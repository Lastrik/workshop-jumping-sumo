package de.bischinger.parrot.driver.programmatic;

import de.bischinger.parrot.controller.DroneController;
import de.bischinger.parrot.network.DroneConnection;

import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.util.logging.Logger;


/**
 * @author  Alexander Bischof
 */
public class ProgrammaticDriver {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());

    private final DroneController drone;

    public ProgrammaticDriver(DroneConnection droneConnection) throws IOException {

        drone = new DroneController(droneConnection);
        drone.pcmd(0, 0);
    }

    public void drive() throws IOException, InterruptedException {

        drone.video().enableVideo().drone().right(360);

//        drone.addOutdoorSpeedListener(b -> System.out.println("Speed: " + b)).addPCMDListener(b ->
//                    System.out.println("PCMD: " + b)).addBatteryListener(aByte ->
//                System.out.println("Batterylevel: " + aByte.toString() + "%")).video().enableVideo();
    }
}
