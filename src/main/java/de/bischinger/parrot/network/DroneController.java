package de.bischinger.parrot.network;

import de.bischinger.parrot.commands.CommandReader;
import de.bischinger.parrot.commands.common.CurrentDate;
import de.bischinger.parrot.commands.common.CurrentTime;
import de.bischinger.parrot.commands.common.Disconnect;
import de.bischinger.parrot.commands.common.Pong;
import de.bischinger.parrot.commands.jumpingsumo.AudioTheme;
import de.bischinger.parrot.commands.jumpingsumo.Jump;
import de.bischinger.parrot.commands.jumpingsumo.Metronome;
import de.bischinger.parrot.commands.jumpingsumo.Ondulation;
import de.bischinger.parrot.commands.jumpingsumo.Pcmd;
import de.bischinger.parrot.commands.jumpingsumo.Slalom;
import de.bischinger.parrot.commands.jumpingsumo.SlowShake;
import de.bischinger.parrot.commands.jumpingsumo.Spin;
import de.bischinger.parrot.commands.jumpingsumo.SpinJump;
import de.bischinger.parrot.commands.jumpingsumo.SpinToPosture;
import de.bischinger.parrot.commands.jumpingsumo.Spiral;
import de.bischinger.parrot.commands.jumpingsumo.StopAnimation;
import de.bischinger.parrot.commands.jumpingsumo.Tap;
import de.bischinger.parrot.commands.jumpingsumo.VideoStreaming;
import de.bischinger.parrot.commands.jumpingsumo.Volume;
import de.bischinger.parrot.network.handshake.HandshakeAnswer;
import de.bischinger.parrot.network.handshake.HandshakeRequest;
import de.bischinger.parrot.network.handshake.TcpHandshake;

import java.io.FileOutputStream;
import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static java.lang.String.format;

import static java.net.InetAddress.getByName;

import static java.util.concurrent.TimeUnit.MILLISECONDS;


/**
 * @author  Alexander Bischof
 */
public class DroneController implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());

    private final DatagramSocket controller2DeviceSocket;
    private final String deviceIp;
    private final int controller2DevicePort;
    private final boolean postTimeout;

    private final List<EventListener> eventListeners = new ArrayList<>();
    private byte nonackCounter = 0;
    private byte ackCounter = 0;

    public DroneController(String deviceIp, int tcpPort, HandshakeRequest handshakeRequest, boolean postSendTimeout)
        throws IOException {

        LOGGER.info(format("Creating DroneController for %s:%s...", deviceIp, tcpPort));

        this.deviceIp = deviceIp;
        this.postTimeout = postSendTimeout;

        HandshakeAnswer handshakeAnswer = new TcpHandshake(deviceIp, tcpPort).shake(handshakeRequest);
        controller2DevicePort = handshakeAnswer.getC2d_port();
        LOGGER.info(format("Handshake completed with %s", handshakeAnswer));

        controller2DeviceSocket = new DatagramSocket();

        sendCommand(CurrentDate.currentDate().getBytes(ackCounter++));
        sendCommand(CurrentTime.currentTime().getBytes(ackCounter++));
        sendCommand(VideoStreaming.enableVideoStreaming().getBytes(ackCounter++));

        addAnswerSocket();
    }

    private void addAnswerSocket() {

        new Thread(() -> {
            try(DatagramSocket sumoSocket = new DatagramSocket(controller2DevicePort)) {
                LOGGER.info(format("Listing for answers on port %s", controller2DevicePort));

                while (true) {
                    byte[] buf = new byte[65000];

                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    sumoSocket.receive(packet);

                    byte[] data = packet.getData();

                    if (data[1] == 126) {
                        sendCommand(Pong.pong().getBytes(data[3]));

                        continue;
                    }

                    if (data[1] == 125) {
                        // videoStream.write();
                        try(FileOutputStream fos = new FileOutputStream("video.jpeg")) {
                            fos.write(getJpegDate(data));
                        }

                        continue;
                    }

                    // FIXME
                    CommandReader commandReader = CommandReader.commandReader(data);

                    if (commandReader.isPing() || commandReader.isLinkQualityChanged()
                            || commandReader.isWifiSignalChanged()) {
                        continue;
                    }

                    // System.out.println("---" + Arrays.toString(data));
                    // logIncoming(data);

                    eventListeners.stream().forEach(eventListener -> eventListener.eventFired(data));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    public void close() throws IOException {

        sendCommand(Disconnect.disconnect().getBytes(ackCounter++));
        controller2DeviceSocket.close();
    }


    public void sendCommand(byte[] packetAsBytes) throws IOException {

        LOGGER.fine(format("Sending command: %s", Arrays.toString(packetAsBytes)));

        DatagramPacket packet = new DatagramPacket(packetAsBytes, packetAsBytes.length, getByName(deviceIp),
                controller2DevicePort);

        controller2DeviceSocket.send(packet);

        if (postTimeout) {
            try {
                MILLISECONDS.sleep(580);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public DroneController pcmd(int speed, int turn) throws IOException {

        this.sendCommand(Pcmd.pcmd(speed, turn).getBytes(++nonackCounter));

        return this;
    }


    public DroneController forward() throws IOException {

        pcmd(60, 0);

        return this;
    }


    public DroneController backward() throws IOException {

        pcmd(-40, 0);

        return this;
    }


    public DroneController left() throws IOException {

        left(90);

        return this;
    }


    public DroneController left(int degrees) throws IOException {

        pcmd(0, -25 * (degrees % 180) / 90);

        return this;
    }


    public DroneController right(int degrees) throws IOException {

        pcmd(0, 25 * (degrees % 180) / 90);

        return this;
    }


    public DroneController right() throws IOException {

        right(90);

        return this;
    }


    public DroneController jump(Jump.Type jumpType) throws IOException {

        this.sendCommand(Jump.jump(jumpType).getBytes(++ackCounter));

        return this;
    }


    public DroneController stopAnnimation() throws IOException {

        this.sendCommand(StopAnimation.stopAnimation().getBytes(++ackCounter));

        return this;
    }


    public DroneController spin() throws IOException {

        this.sendCommand(Spin.spin().getBytes(++ackCounter));

        return this;
    }


    public DroneController tap() throws IOException {

        this.sendCommand(Tap.tap().getBytes(++ackCounter));

        return this;
    }


    public DroneController slowShake() throws IOException {

        this.sendCommand(SlowShake.slowShake().getBytes(++ackCounter));

        return this;
    }


    public DroneController metronome() throws IOException {

        this.sendCommand(Metronome.metronome().getBytes(++ackCounter));

        return this;
    }


    public DroneController ondulation() throws IOException {

        this.sendCommand(Ondulation.ondulation().getBytes(++ackCounter));

        return this;
    }


    public DroneController spinJump() throws IOException {

        this.sendCommand(SpinJump.spinJump().getBytes(++ackCounter));

        return this;
    }


    public DroneController spinToPosture() throws IOException {

        this.sendCommand(SpinToPosture.spinToPosture().getBytes(++ackCounter));

        return this;
    }


    public DroneController spiral() throws IOException {

        this.sendCommand(Spiral.spiral().getBytes(++ackCounter));

        return this;
    }


    public DroneController slalom() throws IOException {

        this.sendCommand(Slalom.slalom().getBytes(++ackCounter));

        return this;
    }


    public DroneController addCriticalBatteryListener(Consumer<BatteryState> consumer) {

        this.eventListeners.add(data -> {
            if (filterProject(data, 3, 1, 1)) {
                consumer.accept(BatteryState.values()[data[11]]);
            }
        });

        return this;
    }


    public DroneController addBatteryListener(Consumer<Byte> consumer) {

        this.eventListeners.add(data -> {
            if (filterProject(data, 0, 5, 1)) {
                consumer.accept(data[11]);
            }
        });

        return this;
    }


    public DroneController addPCMDListener(Consumer<String> consumer) {

        this.eventListeners.add(data -> {
            if (filterProject(data, 3, 1, 0)) {
                consumer.accept("" + data[11]);
            }
        });

        return this;
    }


    public DroneController addOutdoorSpeedListener(Consumer<String> consumer) {

        this.eventListeners.add(data -> {
            if (filterProject(data, 3, 17, 0)) {
                consumer.accept("" + data[11]);
            }
        });

        return this;
    }


    public AudioController audio() {

        return new AudioController();
    }


    private boolean filterChannel(byte[] data, int frametype, int channel) {

        return data[0] == frametype && data[1] == channel;
    }


    private boolean filterProject(byte[] data, int project, int clazz, int cmd) {

        return data[7] == project && data[8] == clazz && data[9] == cmd;
    }


    private byte[] getJpegDate(byte[] data) {

        byte[] jpegData = new byte[data.length];
        System.arraycopy(data, 12, jpegData, 0, data.length - 12);

        return jpegData;
    }

    public class AudioController {

        public AudioController theme(AudioTheme.Theme theme) throws IOException {

            sendCommand(AudioTheme.audioTheme(theme).getBytes(++ackCounter));

            return this;
        }


        public AudioController mute() throws IOException {

            sendCommand(Volume.volume(0).getBytes(++ackCounter));

            return this;
        }


        public AudioController unmute() throws IOException {

            sendCommand(Volume.volume(100).getBytes(++ackCounter));

            return this;
        }
    }
}
