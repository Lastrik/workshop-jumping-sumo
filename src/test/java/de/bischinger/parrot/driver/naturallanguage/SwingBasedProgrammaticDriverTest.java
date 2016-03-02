package de.bischinger.parrot.driver.naturallanguage;

import de.bischinger.parrot.controller.DroneController;
import de.bischinger.parrot.network.DroneConnection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static de.bischinger.parrot.Main.SINGLETON;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by bischofa on 02/03/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SwingBasedProgrammaticDriverTest {
    @Mock
    private DroneController droneController;

    @Mock
    private DroneConnection droneConnection;

    @Test
    public void test() throws IOException, InterruptedException {
        SINGLETON = droneController;

        SwingBasedProgrammaticDriver swingBasedProgrammaticDriver = new SwingBasedProgrammaticDriver(SINGLETON).withDynamicCompilation();
        swingBasedProgrammaticDriver.setText("JumpingSumo.spinJump()");
        swingBasedProgrammaticDriver.fire();
        verify(droneController, times(1)).spinJump();
    }
}