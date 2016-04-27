package de.devoxx4kids.drone.control.driver.naturallanguage;

import de.devoxx4kids.drone.Main;
import de.devoxx4kids.drone.control.DroneController;

import de.devoxx4kids.dronecontroller.network.DroneConnection;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

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
    public void test() throws InterruptedException {

        Main.SINGLETON = droneController;

        SwingBasedProgrammaticDriver swingBasedProgrammaticDriver =
            new SwingBasedProgrammaticDriver(Main.SINGLETON).withDynamicCompilation();
        swingBasedProgrammaticDriver.setText("JumpingSumo.spinJump()");
        swingBasedProgrammaticDriver.fire();
        verify(droneController, times(1)).spinJump();
    }
}
