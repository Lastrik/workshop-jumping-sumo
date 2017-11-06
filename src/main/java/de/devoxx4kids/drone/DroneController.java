package de.devoxx4kids.drone;

import de.devoxx4kids.dronecontroller.command.Command;
import de.devoxx4kids.dronecontroller.command.animation.Metronome;
import de.devoxx4kids.dronecontroller.command.animation.Ondulation;
import de.devoxx4kids.dronecontroller.command.animation.Slalom;
import de.devoxx4kids.dronecontroller.command.animation.SlowShake;
import de.devoxx4kids.dronecontroller.command.animation.Spin;
import de.devoxx4kids.dronecontroller.command.animation.SpinJump;
import de.devoxx4kids.dronecontroller.command.animation.SpinToPosture;
import de.devoxx4kids.dronecontroller.command.animation.Spiral;
import de.devoxx4kids.dronecontroller.command.animation.StopAnimation;
import de.devoxx4kids.dronecontroller.command.animation.Tap;
import de.devoxx4kids.dronecontroller.command.movement.Jump;
import de.devoxx4kids.dronecontroller.command.movement.Pcmd;
import de.devoxx4kids.dronecontroller.command.multimedia.AudioTheme;
import de.devoxx4kids.dronecontroller.command.multimedia.VideoStreaming;
import de.devoxx4kids.dronecontroller.command.multimedia.Volume;
import de.devoxx4kids.dronecontroller.listener.common.BatteryListener;
import de.devoxx4kids.dronecontroller.listener.common.PCMDListener;
import de.devoxx4kids.dronecontroller.listener.multimedia.VideoListener;
import de.devoxx4kids.dronecontroller.network.ConnectionException;
import de.devoxx4kids.dronecontroller.network.DroneConnection;
import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import java.util.function.Consumer;


/**
 * Controller to control the drone connected by the {@link DroneConnection}.
 *
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public class DroneController implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DroneConnection droneConnection;

    public DroneController(DroneConnection droneConnection) {

        LOGGER.info("Creating DroneController");
        this.droneConnection = droneConnection;

        connect();
    }

    @Override
    public void close() {

        disconnect();
    }


    public void connect() {

        try {
            droneConnection.connect();
        } catch (ConnectionException e) {
            LOGGER.error("Could not establish connection to drone");
        }
    }


    public void disconnect() {

        droneConnection.disconnect();
    }


    public DroneController send(Command command) {

        this.droneConnection.sendCommand(command);

        return this;
    }


    public DroneController pcmd(int speed, int degree) {

        this.droneConnection.sendCommand(Pcmd.pcmd(speed, degree));

        return this;
    }


    public DroneController forward() {

        pcmd(40, 0);

        return this;
    }


    public DroneController backward() {

        pcmd(-40, 0);

        return this;
    }


    public DroneController left() {

        left(90);

        return this;
    }


    public DroneController left(int degrees) {

        pcmd(0, -degrees);

        return this;
    }


    public DroneController right(int degrees) {

        pcmd(0, degrees);

        return this;
    }


    public DroneController right() {

        right(90);

        return this;
    }


    public DroneController jump(Jump.Type jumpType) {

        this.droneConnection.sendCommand(Jump.jump(jumpType));

        return this;
    }


    public DroneController jumpHigh() {

        return jump(Jump.Type.High);
    }


    public DroneController jumpLong() {

        return jump(Jump.Type.Long);
    }


    public DroneController stopAnimation() {

        this.droneConnection.sendCommand(StopAnimation.stopAnimation());

        return this;
    }


    public DroneController spin() {

        this.droneConnection.sendCommand(Spin.spin());

        return this;
    }


    public DroneController tap() {

        this.droneConnection.sendCommand(Tap.tap());

        return this;
    }


    public DroneController slowShake() {

        this.droneConnection.sendCommand(SlowShake.slowShake());

        return this;
    }


    public DroneController metronome() {

        this.droneConnection.sendCommand(Metronome.metronome());

        return this;
    }


    public DroneController ondulation() {

        this.droneConnection.sendCommand(Ondulation.ondulation());

        return this;
    }


    public DroneController spinJump() {

        this.droneConnection.sendCommand(SpinJump.spinJump());

        return this;
    }


    public DroneController spinToPosture() {

        this.droneConnection.sendCommand(SpinToPosture.spinToPosture());

        return this;
    }


    public DroneController spiral() {

        this.droneConnection.sendCommand(Spiral.spiral());

        return this;
    }


    public DroneController slalom() {

        this.droneConnection.sendCommand(Slalom.slalom());

        return this;
    }


    public DroneController addBatteryListener(Consumer<Byte> consumer) {

        droneConnection.addEventListener(BatteryListener.batteryListener(consumer));

        return this;
    }


    public DroneController addPCMDListener(Consumer<String> consumer) {

        droneConnection.addEventListener(PCMDListener.pcmdlistener(consumer));

        return this;
    }


    public AudioController audio() {

        return new AudioController(this);
    }


    public VideoController video() {

        return new VideoController(this);
    }

    public class AudioController {

        private final DroneController droneController;

        AudioController(DroneController droneController) {

            this.droneController = droneController;
        }

        public AudioController theme(AudioTheme.Theme theme) {

            droneConnection.sendCommand(AudioTheme.audioTheme(theme));

            return this;
        }


        public AudioController volume(int volume) {

            droneConnection.sendCommand(Volume.volume(volume));

            return this;
        }


        public AudioController mute() {

            volume(0);

            return this;
        }


        public AudioController unmute() {

            volume(100);

            return this;
        }


        public DroneController drone() {

            return droneController;
        }
    }

    public class VideoController {

        private final DroneController droneController;

        VideoController(DroneController droneController) {

            this.droneController = droneController;
        }

        public VideoController enableVideo(Consumer<BufferedImage> consumer) {

            droneConnection.addEventListener(VideoListener.videoListener(consumer));
            droneConnection.sendCommand(VideoStreaming.enableVideoStreaming());

            return this;
        }


        public VideoController disableVideo() {

            droneConnection.sendCommand(VideoStreaming.disableVideoStreaming());

            return this;
        }


        public DroneController drone() {

            return droneController;
        }
    }
}
