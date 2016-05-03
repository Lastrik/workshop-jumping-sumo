package de.devoxx4kids.drone.control;

import de.devoxx4kids.drone.DroneController;

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
import de.devoxx4kids.dronecontroller.listener.common.BatteryListener;
import de.devoxx4kids.dronecontroller.listener.common.PCMDListener;
import de.devoxx4kids.dronecontroller.network.DroneConnection;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

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
    public void setUp() {

        sut = new DroneController(droneConnectionMock);
    }


    @Test
    public void close() {

        sut.close();

        verify(droneConnectionMock).disconnect();
    }


    @Test
    public void pcmd() {

        DroneController droneController = sut.pcmd(100, 180);
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void forward() {

        DroneController droneController = sut.forward();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void backward() {

        DroneController droneController = sut.backward();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void left() {

        DroneController droneController = sut.left();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void leftWithArguments() {

        DroneController droneController = sut.left(90);
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void right() {

        DroneController droneController = sut.right();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void rightWithArguments() {

        DroneController droneController = sut.right(100);
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void jump() {

        DroneController droneController = sut.jump(Jump.Type.Long);
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Jump.class));
    }


    @Test
    public void jumpHigh() {

        DroneController droneController = sut.jumpHigh();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Jump.class));
    }


    @Test
    public void jumpLong() {

        DroneController droneController = sut.jumpLong();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Jump.class));
    }


    @Test
    public void stopAnimation() {

        DroneController droneController = sut.stopAnimation();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(StopAnimation.class));
    }


    @Test
    public void spin() {

        DroneController droneController = sut.spin();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Spin.class));
    }


    @Test
    public void tap() {

        DroneController droneController = sut.tap();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Tap.class));
    }


    @Test
    public void slowShake() {

        DroneController droneController = sut.slowShake();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(SlowShake.class));
    }


    @Test
    public void metronome() {

        DroneController droneController = sut.metronome();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Metronome.class));
    }


    @Test
    public void ondulation() {

        DroneController droneController = sut.ondulation();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Ondulation.class));
    }


    @Test
    public void spinJump() {

        DroneController droneController = sut.spinJump();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(SpinJump.class));
    }


    @Test
    public void spinToPosture() {

        DroneController droneController = sut.spinToPosture();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(SpinToPosture.class));
    }


    @Test
    public void spiral() {

        DroneController droneController = sut.spiral();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Spiral.class));
    }


    @Test
    public void slalom() {

        DroneController droneController = sut.slalom();
        assertThat(droneController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Slalom.class));
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
