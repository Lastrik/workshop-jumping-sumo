package de.bischinger.parrot;

import de.bischinger.parrot.commands.common.Disconnect;
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

        DroneController pcmd = sut.pcmd(100, 180);
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void forward() throws IOException {

        DroneController pcmd = sut.forward();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void backward() throws IOException {

        DroneController pcmd = sut.backward();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void left() throws IOException {

        DroneController pcmd = sut.left();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void leftWithArguments() throws IOException {

        DroneController pcmd = sut.left(90);
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void right() throws IOException {

        DroneController pcmd = sut.right();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void rightWithArguments() throws IOException {

        DroneController pcmd = sut.right(100);
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Pcmd.class));
    }


    @Test
    public void jump() throws IOException {

        DroneController pcmd = sut.jump(Jump.Type.Long);
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Jump.class));
    }


    @Test
    public void stopAnimation() throws IOException {

        DroneController pcmd = sut.stopAnimation();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(StopAnimation.class));
    }


    @Test
    public void spin() throws IOException {

        DroneController pcmd = sut.spin();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Spin.class));
    }


    @Test
    public void tap() throws IOException {

        DroneController pcmd = sut.tap();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Tap.class));
    }


    @Test
    public void slowShake() throws IOException {

        DroneController pcmd = sut.slowShake();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(SlowShake.class));
    }


    @Test
    public void metronome() throws IOException {

        DroneController pcmd = sut.metronome();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Metronome.class));
    }


    @Test
    public void ondulation() throws IOException {

        DroneController pcmd = sut.ondulation();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Ondulation.class));
    }


    @Test
    public void spinJump() throws IOException {

        DroneController pcmd = sut.spinJump();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(SpinJump.class));
    }


    @Test
    public void spinToPosture() throws IOException {

        DroneController pcmd = sut.spinToPosture();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(SpinToPosture.class));
    }


    @Test
    public void spiral() throws IOException {

        DroneController pcmd = sut.spiral();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Spiral.class));
    }


    @Test
    public void slalom() throws IOException {

        DroneController pcmd = sut.slalom();
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).sendCommand(any(Slalom.class));
    }


    @Test
    public void addCriticalBatteryListener() {

        DroneController pcmd = sut.addCriticalBatteryListener(b -> { });
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).addEventListener(any(CriticalBatteryListener.class));
    }


    @Test
    public void addBatteryListener() {

        DroneController pcmd = sut.addBatteryListener(b -> { });
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).addEventListener(any(BatteryListener.class));
    }


    @Test
    public void addPCMDListener() {

        DroneController pcmd = sut.addPCMDListener(b -> { });
        assertThat(pcmd, is(sut));

        verify(droneConnectionMock).addEventListener(any(PCMDListener.class));
    }


    @Test
    public void addOutdoorSpeedListener() {

        DroneController pcmd = sut.addOutdoorSpeedListener(b -> { });
        assertThat(pcmd, is(sut));

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
