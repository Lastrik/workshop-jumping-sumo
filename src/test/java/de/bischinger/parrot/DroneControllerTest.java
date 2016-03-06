package de.bischinger.parrot;

import de.bischinger.parrot.commands.animation.Metronome;
import de.bischinger.parrot.commands.animation.Ondulation;
import de.bischinger.parrot.commands.animation.Slalom;
import de.bischinger.parrot.commands.animation.SlowShake;
import de.bischinger.parrot.commands.animation.Spin;
import de.bischinger.parrot.commands.animation.SpinJump;
import de.bischinger.parrot.commands.animation.SpinToPosture;
import de.bischinger.parrot.commands.animation.Spiral;
import de.bischinger.parrot.commands.animation.StopAnimation;
import de.bischinger.parrot.commands.animation.Tap;
import de.bischinger.parrot.commands.common.Disconnect;
import de.bischinger.parrot.commands.movement.Jump;
import de.bischinger.parrot.commands.movement.Pcmd;
import de.bischinger.parrot.controller.DroneController;
import de.bischinger.parrot.listener.BatteryListener;
import de.bischinger.parrot.listener.CriticalBatteryListener;
import de.bischinger.parrot.listener.OutdoorSpeedListener;
import de.bischinger.parrot.listener.PCMDListener;
import de.bischinger.parrot.network.DroneConnection;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.verify;


/**
 * Unit test of {@link DroneController}.
 *
 * @author  Tobias Schneider
 */
@RunWith(MockitoJUnitRunner.class)
public class DroneControllerTest {

    private DroneController sut;

    @Mock
    private DroneConnection droneConnectionMock;

    @Before
    public void setUp() throws Exception {

        sut = new DroneController(droneConnectionMock);
    }


    @Test
    public void close() throws Exception {

        sut.close();

        verify(droneConnectionMock).sendCommand(any(Disconnect.class));
        verify(droneConnectionMock).close();
    }


    @Test
    public void pcmd() throws IOException {

        DroneController droneController = sut.pcmd(100, 180);
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void forward() throws IOException {

        DroneController droneController = sut.forward();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void backward() throws IOException {

        DroneController droneController = sut.backward();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void left() throws IOException {

        DroneController droneController = sut.left();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void leftWithArguments() throws IOException {

        DroneController droneController = sut.left(90);
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void right() throws IOException {

        DroneController droneController = sut.right();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void rightWithArguments() throws IOException {

        DroneController droneController = sut.right(100);
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void jump() throws IOException {

        DroneController droneController = sut.jump(Jump.Type.Long);
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Jump.class));
    }


    @Test
    public void stopAnimation() throws IOException {

        DroneController droneController = sut.stopAnimation();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(StopAnimation.class));
    }


    @Test
    public void spin() throws IOException {

        DroneController droneController = sut.spin();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Spin.class));
    }


    @Test
    public void tap() throws IOException {

        DroneController droneController = sut.tap();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Tap.class));
    }


    @Test
    public void slowShake() throws IOException {

        DroneController droneController = sut.slowShake();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(SlowShake.class));
    }


    @Test
    public void metronome() throws IOException {

        DroneController droneController = sut.metronome();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Metronome.class));
    }


    @Test
    public void ondulation() throws IOException {

        DroneController droneController = sut.ondulation();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Ondulation.class));
    }


    @Test
    public void spinJump() throws IOException {

        DroneController droneController = sut.spinJump();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(SpinJump.class));
    }


    @Test
    public void spinToPosture() throws IOException {

        DroneController droneController = sut.spinToPosture();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(SpinToPosture.class));
    }


    @Test
    public void spiral() throws IOException {

        DroneController droneController = sut.spiral();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Spiral.class));
    }


    @Test
    public void slalom() throws IOException {

        DroneController droneController = sut.slalom();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Slalom.class));
    }


    @Test
    public void addCriticalBatteryListener() {

        DroneController droneController = sut.addCriticalBatteryListener(b -> { });
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).addEventListener(any(CriticalBatteryListener.class));
    }


    @Test
    public void addBatteryListener() {

        DroneController droneController = sut.addBatteryListener(b -> { });
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).addEventListener(any(BatteryListener.class));
    }


    @Test
    public void addPCMDListener() {

        DroneController droneController = sut.addPCMDListener(b -> { });
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).addEventListener(any(PCMDListener.class));
    }


    @Test
    public void addOutdoorSpeedListener() {

        DroneController droneController = sut.addOutdoorSpeedListener(b -> { });
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).addEventListener(any(OutdoorSpeedListener.class));
    }


    @Test
    public void audio() {

        DroneController.AudioController audio = sut.audio();
        assertThat(audio, is(instanceOf(DroneController.AudioController.class)));
    }


    @Test
    public void video() {

        DroneController.VideoController audio = sut.video();
        assertThat(audio, is(instanceOf(DroneController.VideoController.class)));
    }
}
