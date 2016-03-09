package de.bischinger.parrot.lib.network;

import de.bischinger.parrot.commands.CommandException;
import de.bischinger.parrot.lib.command.Command;
import de.bischinger.parrot.lib.command.common.CommonCommand;
import de.bischinger.parrot.lib.command.common.CurrentDate;
import de.bischinger.parrot.lib.command.common.CurrentTime;
import de.bischinger.parrot.lib.command.common.Pong;
import de.bischinger.parrot.lib.network.handshake.HandshakeRequest;
import de.bischinger.parrot.lib.network.handshake.HandshakeResponse;
import de.bischinger.parrot.lib.network.handshake.TcpHandshake;
import de.bischinger.parrot.listener.EventListener;

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
 * Represents the queue wireless lan connection to the drone.
 *
 * @author  Tobias Schneider
 */
public class QueueWirelessLanDroneConnection implements DroneConnection {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());

    private static final String CONTROLLER_TYPE = "_arsdk-0902._udp";

    private final BlockingQueue<Command> commonCommandQueue = new ArrayBlockingQueue<>(25);
    private final BlockingQueue<Command> commandQueue = new ArrayBlockingQueue<>(25);
    private final List<EventListener> eventListeners = new ArrayList<>();

    private final String deviceIp;
    private final int tcpPort;
    private final String wirelessLanName;
    private final Clock clock;

    private int devicePort;
    private byte noAckCounter = 0;
    private byte ackCounter = 0;

    public QueueWirelessLanDroneConnection(String deviceIp, int tcpPort, String wirelessLanName) {

        LOGGER.info(format("Creating " + this.getClass().getSimpleName() + " for %s:%s...", deviceIp, tcpPort));

        this.deviceIp = deviceIp;
        this.tcpPort = tcpPort;
        this.wirelessLanName = wirelessLanName;
        this.clock = Clock.systemDefaultZone();
    }

    @Override
    public void connect() throws IOException {

        LOGGER.fine("Connecting to drone...");

        HandshakeRequest handshakeRequest = new HandshakeRequest(wirelessLanName, CONTROLLER_TYPE);
        HandshakeResponse handshakeResponse = new TcpHandshake(deviceIp, tcpPort).shake(handshakeRequest);
        devicePort = handshakeResponse.getC2d_port();
        LOGGER.info(format("Connected to drone - Handshake completed with %s", handshakeResponse));

        sendCommand(CurrentDate.currentDate(clock));
        sendCommand(CurrentTime.currentTime(clock));

        runResponseHandler();
        runConsumer(commandQueue);
        runConsumer(commonCommandQueue);
    }


    @Override
    public void sendCommand(Command command) {

        try {
            if (command instanceof CommonCommand) {
                commonCommandQueue.put(command);
            } else {
                commandQueue.put(command);
            }
        } catch (InterruptedException e) {
            LOGGER.info("Could not add " + command + " to a queue.");
        }
    }


    @Override
    public void addEventListener(EventListener eventListener) {

        this.eventListeners.add(eventListener);
    }


    /**
     * Drone response handler.
     *
     * <p>Will listen to the udp packages send from the drone to the receiver and looks up what command it is and react
     * to it</p>
     */
    private void runResponseHandler() {

        new Thread(() -> {
            try(DatagramSocket sumoSocket = new DatagramSocket(devicePort)) {
                LOGGER.info(format("Listing for response on port %s", devicePort));

                int pingCounter = 0;

                while (true) {
                    byte[] buffer = new byte[65000];

                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    sumoSocket.receive(packet);

                    byte[] data = packet.getData();

                    // Answer with a Pong
                    if (data[1] == 126) {
                        LOGGER.config("Ping");
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
                LOGGER.warning("Error occurred while receiving packets from the drone.");
            }
        }).start();
    }


    private void runConsumer(BlockingQueue<Command> queue) {

        LOGGER.info("Creating a specific command queue consumer...");

        new Thread(() -> {
            try(DatagramSocket sumoSocket = new DatagramSocket()) {
                while (true) {
                    try {
                        Command command = queue.take();

                        byte[] packet = command.getBytes(changeAndGetCounter(command));
                        sumoSocket.send(new DatagramPacket(packet, packet.length, getByName(deviceIp), devicePort));
                        LOGGER.info(format("Sending command: %s", command));

                        MILLISECONDS.sleep(command.waitingTime());
                    } catch (InterruptedException e) {
                        throw new CommandException("Got interrupted while taking a command", e);
                    }
                }
            } catch (IOException e) {
                LOGGER.warning("Error occurred while sending packets to the drone.");
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
}
