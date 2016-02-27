package de.bischinger.parrot.driver.programmatic;

import de.bischinger.parrot.network.DroneController;
import de.bischinger.parrot.network.handshake.HandshakeRequest;

import java.io.IOException;


/**
 * @author  Alexander Bischof
 */
public class ProgrammaticDriver {

    private DroneController drone;

    public ProgrammaticDriver(String ip, int port, String sumoWlan) throws Exception {

        drone = new DroneController(ip, port, new HandshakeRequest(sumoWlan, "_arsdk-0902._udp"), true);
        drone.pcmd(0, 0);
    }

    public void drive() throws IOException, InterruptedException {

        drone.forward();
        System.exit(0);
    }
}
