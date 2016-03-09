package de.bischinger.parrot;

import de.bischinger.parrot.control.DroneController;
import de.bischinger.parrot.lib.command.multimedia.AudioTheme;
import de.bischinger.parrot.lib.command.multimedia.Volume;
import de.bischinger.parrot.lib.network.DroneConnection;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.verify;


/**
 * Unit test of {@link DroneController.AudioController}.
 *
 * @author  Tobias Schneider
 */
@RunWith(MockitoJUnitRunner.class)
public class AudioControllerTest {

    private DroneController.AudioController sut;

    @Mock
    private DroneConnection droneConnectionMock;

    private DroneController droneController;

    @Before
    public void setUp() throws Exception {

        droneController = new DroneController(droneConnectionMock);
        sut = droneController.audio();
    }


    @Test
    public void theme() throws IOException {

        DroneController.AudioController audioController = sut.theme(AudioTheme.Theme.Monster);
        assertThat(audioController, is(sut));

        verify(droneConnectionMock).sendCommand(any(AudioTheme.class));
    }


    @Test
    public void volume() throws IOException {

        DroneController.AudioController audioController = sut.volume(50);
        assertThat(audioController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Volume.class));
    }


    @Test
    public void mute() throws IOException {

        DroneController.AudioController audioController = sut.mute();
        assertThat(audioController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Volume.class));
    }


    @Test
    public void unmute() throws IOException {

        DroneController.AudioController audioController = sut.unmute();
        assertThat(audioController, is(sut));

        verify(droneConnectionMock).sendCommand(any(Volume.class));
    }


    @Test
    public void drone() {

        DroneController droneController = sut.drone();
        assertThat(droneController, is(this.droneController));
    }
}
