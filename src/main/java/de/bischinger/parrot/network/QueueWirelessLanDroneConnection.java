package de.bischinger.parrot.network;

import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandException;
import de.bischinger.parrot.commands.common.CurrentDate;
import de.bischinger.parrot.commands.common.CurrentTime;
import de.bischinger.parrot.commands.common.Pong;
import de.bischinger.parrot.listener.EventListener;
import de.bischinger.parrot.network.handshake.HandshakeRequest;
import de.bischinger.parrot.network.handshake.HandshakeResponse;
import de.bischinger.parrot.network.handshake.TcpHandshake;

import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.time.Clock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import static java.lang.String.format;

import static java.net.InetAddress.getByName;

import static java.util.concurrent.TimeUnit.MILLISECONDS;


/**
 * Represents the wireless lan connection to the drone.
 *
 * @author  Tobias Schneider
 */
public class QueueWirelessLanDroneConnection implements DroneConnection {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());

    private final BlockingQueue<Command> queue = new ArrayBlockingQueue<>(25);
    private final List<EventListener> eventListeners = new ArrayList<>();

    private final String deviceIp;
    private final int tcpPort;
    private final String wlanName;
    private final Clock clock;

    private int devicePort;
    private byte noAckCounter = 0;
    private byte ackCounter = 0;

    public QueueWirelessLanDroneConnection(String deviceIp, int tcpPort, String wlanName) {

        LOGGER.info(format("Creating DroneConnector for %s:%s...", deviceIp, tcpPort));
        this.deviceIp = deviceIp;
        this.tcpPort = tcpPort;
        this.wlanName = wlanName;
        this.clock = Clock.systemDefaultZone();
    }

    @Override
    public void connect() throws IOException {

        LOGGER.info("Connecting to drone...");

        HandshakeRequest handshakeRequest = new HandshakeRequest(wlanName, "_arsdk-0902._udp");
        HandshakeResponse handshakeResponse = new TcpHandshake(deviceIp, tcpPort).shake(handshakeRequest);
        devicePort = handshakeResponse.getC2d_port();
        LOGGER.info(format("Connected: Handshake completed with %s", handshakeResponse));

        sendCommand(CurrentDate.currentDate(clock));
        sendCommand(CurrentTime.currentTime(clock));

        addAnswerSocket();
        runCommandConsumer();
    }


    @Override
    public void sendCommand(Command command) {

        LOGGER.info("ADDED " + command);

        try {
            queue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addEventListener(EventListener eventListener) {

        this.eventListeners.add(eventListener);
    }


    private void addAnswerSocket() {

        new Thread(() -> {
            try(DatagramSocket sumoSocket = new DatagramSocket(devicePort)) {
                LOGGER.finest(format("Listing for answers on port %s", devicePort));

                int pingCounter = 0;

                while (true) {
                    byte[] buf = new byte[65000];

                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    sumoSocket.receive(packet);
                    LOGGER.finest(format("Listing for answers on port %s", devicePort));

                    byte[] data = packet.getData();

                    // Answer with a Pong
                    if (data[1] == 126) {
                        LOGGER.finest("Ping");
                        sendCommand(Pong.pong(data[3]));

                        if (pingCounter > 10 && pingCounter % 10 == 1) {
                            sendCommand(CurrentDate.currentDate(clock));
                            sendCommand(CurrentTime.currentTime(clock));
                        }

                        pingCounter++;

                        continue;
                    }

                    eventListeners.stream().forEach(eventListener -> eventListener.eventFired(data));
                }
            } catch (IOException e) {
                // TODO own exception with handling?
                e.printStackTrace();
            }
        }).start();
    }


    private void runCommandConsumer() {

        LOGGER.info("Create Consumer Thread...");

        new Thread(() -> {
            try(DatagramSocket sumoSocket = new DatagramSocket()) {
                LOGGER.info("CommandConsumer started...");

                while (true) {
                    try {
                        Command command = queue.take();

                        byte[] packet = command.getBytes(changeAndGetCounter(command));
                        sumoSocket.send(new DatagramPacket(packet, packet.length, getByName(deviceIp), devicePort));
                        LOGGER.info(format("Sending command: %s", command));

                        MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        throw new CommandException("Got interrupted while getting command", e);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private int changeAndGetCounter(Command command) {

        int counter = 0;

        switch (command.getAcknowledge()) {
            case AckBefore:
                counter = ++ackCounter;
                break;

            case AckAfter:
                counter = ackCounter++;
                break;

            case NoAckBefore:
                counter = ++noAckCounter;
                break;

            case None:
            default:
                break;
        }

        return counter;
    }


    @Override
    public void close() throws Exception {
    }
}
