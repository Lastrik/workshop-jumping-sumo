package de.bischinger.parrot.network;

import de.bischinger.parrot.commands.common.CurrentDate;
import de.bischinger.parrot.commands.common.CurrentTime;
import de.bischinger.parrot.commands.common.Disconnect;
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
import de.bischinger.parrot.listener.BatteryListener;
import de.bischinger.parrot.listener.BatteryState;
import de.bischinger.parrot.listener.CriticalBatteryListener;
import de.bischinger.parrot.listener.OutdoorSpeedListener;
import de.bischinger.parrot.listener.PCMDListener;

import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.time.Clock;

import java.util.function.Consumer;
import java.util.logging.Logger;


/**
 * Controller to control the drone connected by the {@link DroneConnection}.
 *
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public class DroneController implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());

    private final DroneConnection droneConnection;

    private byte noAckCounter = 0;
    private byte ackCounter = 0;

    public DroneController(DroneConnection droneConnection) throws IOException {

        LOGGER.info("Creating DroneController");
        this.droneConnection = droneConnection;

        droneConnection.connect();

        Clock clock = Clock.systemDefaultZone();
        droneConnection.sendCommand(CurrentDate.currentDate(clock).getBytes(ackCounter++));
        droneConnection.sendCommand(CurrentTime.currentTime(clock).getBytes(ackCounter++));
        droneConnection.sendCommand(VideoStreaming.enableVideoStreaming().getBytes(ackCounter++));
    }

    @Override
    public void close() throws IOException {

        droneConnection.sendCommand(Disconnect.disconnect().getBytes(ackCounter++));
        droneConnection.close();
    }


    public DroneController pcmd(int speed, int degree) throws IOException {

        this.droneConnection.sendCommand(Pcmd.pcmd(speed, degree).getBytes(++noAckCounter));

        return this;
    }


    public DroneController forward() throws IOException {

        pcmd(40, 0);

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

        pcmd(0, -degrees);

        return this;
    }


    public DroneController right(int degrees) throws IOException {

        pcmd(0, degrees);

        return this;
    }


    public DroneController right() throws IOException {

        right(90);

        return this;
    }


    public DroneController jump(Jump.Type jumpType) throws IOException {

        this.droneConnection.sendCommand(Jump.jump(jumpType).getBytes(++ackCounter));

        return this;
    }


    public DroneController stopAnimation() throws IOException {

        this.droneConnection.sendCommand(StopAnimation.stopAnimation().getBytes(++ackCounter));

        return this;
    }


    public DroneController spin() throws IOException {

        this.droneConnection.sendCommand(Spin.spin().getBytes(++ackCounter));

        return this;
    }


    public DroneController tap() throws IOException {

        this.droneConnection.sendCommand(Tap.tap().getBytes(++ackCounter));

        return this;
    }


    public DroneController slowShake() throws IOException {

        this.droneConnection.sendCommand(SlowShake.slowShake().getBytes(++ackCounter));

        return this;
    }


    public DroneController metronome() throws IOException {

        this.droneConnection.sendCommand(Metronome.metronome().getBytes(++ackCounter));

        return this;
    }


    public DroneController ondulation() throws IOException {

        this.droneConnection.sendCommand(Ondulation.ondulation().getBytes(++ackCounter));

        return this;
    }


    public DroneController spinJump() throws IOException {

        this.droneConnection.sendCommand(SpinJump.spinJump().getBytes(++ackCounter));

        return this;
    }


    public DroneController spinToPosture() throws IOException {

        this.droneConnection.sendCommand(SpinToPosture.spinToPosture().getBytes(++ackCounter));

        return this;
    }


    public DroneController spiral() throws IOException {

        this.droneConnection.sendCommand(Spiral.spiral().getBytes(++ackCounter));

        return this;
    }


    public DroneController slalom() throws IOException {

        this.droneConnection.sendCommand(Slalom.slalom().getBytes(++ackCounter));

        return this;
    }


    public DroneController addCriticalBatteryListener(Consumer<BatteryState> consumer) {

        droneConnection.addEventListener(CriticalBatteryListener.criticalBatteryListener(consumer));

        return this;
    }


    public DroneController addBatteryListener(Consumer<Byte> consumer) {

        droneConnection.addEventListener(BatteryListener.batteryListener(consumer));

        return this;
    }


    public DroneController addPCMDListener(Consumer<String> consumer) {

        droneConnection.addEventListener(PCMDListener.PCMDListener(consumer));

        return this;
    }


    public DroneController addOutdoorSpeedListener(Consumer<String> consumer) {

        droneConnection.addEventListener(OutdoorSpeedListener.outdoorSpeedListener(consumer));

        return this;
    }


    public AudioController audio() {

        return new AudioController(this);
    }

    public class AudioController {

        private final DroneController droneController;

        public AudioController(DroneController droneController) {

            this.droneController = droneController;
        }

        public AudioController theme(AudioTheme.Theme theme) throws IOException {

            droneConnection.sendCommand(AudioTheme.audioTheme(theme).getBytes(++ackCounter));

            return this;
        }


        public AudioController volume(int volume) throws IOException {

            droneConnection.sendCommand(Volume.volume(volume).getBytes(++ackCounter));

            return this;
        }


        public AudioController mute() throws IOException {

            volume(0);

            return this;
        }


        public AudioController unmute() throws IOException {

            volume(100);

            return this;
        }


        public DroneController drone() {

            return droneController;
        }
    }
}
