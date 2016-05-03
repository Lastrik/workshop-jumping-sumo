package de.devoxx4kids.drone.control.driver.programmatic;

import de.devoxx4kids.dronecontroller.command.animation.SpinJump;
import de.devoxx4kids.dronecontroller.network.DroneConnection;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/**
 * Unit test of {@link SwingBasedProgrammaticDriver}.
 *
 * @author  Tobias Schneider
 */
@RunWith(MockitoJUnitRunner.class)
public class SwingBasedProgrammaticDriverTest {

    @Mock
    private DroneConnection droneConnectionMock;

    @Test
    public void test() throws InterruptedException {

        SwingBasedProgrammaticDriver sut = new SwingBasedProgrammaticDriver(droneConnectionMock);

        sut.setText("JumpingSumo.spinJump()");
        sut.fire();

        verify(droneConnectionMock, times(3)).sendCommand(any(SpinJump.class));
    }
}
