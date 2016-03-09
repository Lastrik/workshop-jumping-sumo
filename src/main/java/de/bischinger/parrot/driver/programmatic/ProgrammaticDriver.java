package de.bischinger.parrot.driver.programmatic;

import de.bischinger.parrot.controller.DroneController;
import de.bischinger.parrot.lib.network.DroneConnection;

import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.util.logging.Logger;


/**
 * ProgrammaticDriver.
 *
 * <p>Predefine the moves of the drone</p>
 *
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public class ProgrammaticDriver {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());

    private final DroneController drone;

    public ProgrammaticDriver(DroneConnection droneConnection) throws IOException {

        drone = new DroneController(droneConnection);

        drone.addOutdoorSpeedListener(b -> LOGGER.info("Speed: " + b));
        drone.addPCMDListener(b -> LOGGER.info("PCMD: " + b));
        drone.addBatteryListener(aByte -> LOGGER.info("Batterylevel: " + aByte.toString() + "%"));
    }

    public void drive() throws IOException, InterruptedException {

        drone.metronome().tap().right().left();
    }
}
