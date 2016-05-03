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

                LOGGER.info("Starting Keyboard driver");
                new KeyboardDriver(droneConnection, config.getSpeed(), config.getTurn());
                break;

            case PROGRAM:
                LOGGER.info("Starting Program driver");
                new ProgrammaticDriver(droneConnection).drive();
                break;

            case SWING:
                LOGGER.info("Starting Swing driver");
                new SwingBasedProgrammaticDriver(droneConnection);
                break;

            default:
                LOGGER.info("Argument unbekannt: keyboard | program | swing");
        }
    }
}
