package de.bischinger.parrot.driver.programmatic;

import de.bischinger.parrot.network.DroneConnection;
import de.bischinger.parrot.commands.jumpingsumo.AudioTheme;
import de.bischinger.parrot.network.DroneController;

import java.io.IOException;


/**
 * @author  Alexander Bischof
 */
public class ProgrammaticDriver {

    private final DroneController drone;

    public ProgrammaticDriver(DroneConnection droneConnection) throws IOException {

        drone = new DroneController(droneConnection);
        drone.pcmd(0, 0);
    }

    public void drive() throws IOException, InterruptedException {

        drone.audio().theme(AudioTheme.Theme.Monster).volume(100);
        System.exit(0);
    }
}
