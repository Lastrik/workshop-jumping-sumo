package de.bischinger.parrot.control.driver.programmatic;

import de.bischinger.parrot.control.DroneController;

import de.devoxx4kids.dronecontroller.command.flip.DownsideDown;
import de.devoxx4kids.dronecontroller.network.DroneConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import java.lang.invoke.MethodHandles;


/**
 * ProgrammaticDriver.
 *
 * <p>Predefine the moves of the drone</p>
 *
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public class ProgrammaticDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DroneController drone;

    public ProgrammaticDriver(DroneConnection droneConnection) throws IOException {

        drone = new DroneController(droneConnection);

        drone.addPCMDListener(b -> LOGGER.info("PCMD: " + b));
        drone.addBatteryListener(aByte -> LOGGER.info("Batterylevel: " + aByte.toString() + "%"));
    }

    public void drive() throws IOException, InterruptedException {

        drone.send(DownsideDown.downsideDown()).right().left();
    }
}
