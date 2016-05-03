package de.devoxx4kids.drone;

import de.devoxx4kids.drone.config.Config;
import de.devoxx4kids.drone.config.ConfigReader;
import de.devoxx4kids.drone.keyboard.KeyboardDriver;
import de.devoxx4kids.drone.programmatic.ProgrammaticDriver;
import de.devoxx4kids.drone.programmatic.SwingBasedProgrammaticDriver;

import de.devoxx4kids.dronecontroller.network.DroneConnection;
import de.devoxx4kids.dronecontroller.network.WirelessLanDroneConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import java.lang.invoke.MethodHandles;

import static java.lang.Integer.valueOf;


public final class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Main() {
    }

    public static void main(String[] args) throws IOException {

        Config config = new ConfigReader().get(args);

        DroneConnection droneConnection = new WirelessLanDroneConnection(config.getIp(), config.getPort(),
                config.getWlanName());

        switch (config.getDriver()) {
            case KEYBOARD:

                int speedConfig = args.length > 1 ? valueOf(args[1]) : KeyboardDriver.DEFAULT_SPEED;
                int turnConfig = args.length > 2 ? valueOf(args[2]) : KeyboardDriver.DEFAULT_TURN_DEGREE;
                new KeyboardDriver(droneConnection, speedConfig, turnConfig);
                break;

            case PROGRAM:
                new ProgrammaticDriver(droneConnection).drive();
                break;

            case SWING:
                new SwingBasedProgrammaticDriver(droneConnection);
                break;

            default:
                LOGGER.info("Argument unbekannt: keyboard | program | swing");
        }
    }
}
