package de.devoxx4kids.drone;

import de.devoxx4kids.drone.keyboard.KeyboardDriver;
import de.devoxx4kids.drone.programmatic.ProgrammaticDriver;
import de.devoxx4kids.drone.programmatic.SwingBasedProgrammaticDriver;

import de.devoxx4kids.dronecontroller.network.DroneConnection;
import de.devoxx4kids.dronecontroller.network.WirelessLanDroneConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.util.Properties;

import static java.lang.Integer.valueOf;


public final class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Main() {
    }

    public static void main(String[] args) throws IOException {

        String ip = "192.168.2.1";
        int port = 44444;
        String wlanName = "JS-Alex";

        File configFile = new File("config.properties");

        if (configFile.exists()) {
            Properties properties = new Properties();
            properties.load(new FileReader(configFile));

            ip = properties.getProperty("ip");
            port = valueOf(properties.getProperty("port"));
            wlanName = properties.getProperty("wlan");
        }

        Driver driver = Driver.KEYBOARD;

        if (args.length >= 1) {
            driver = Driver.valueOf(args[0].toLowerCase());
        }

        DroneConnection droneConnection = new WirelessLanDroneConnection(ip, port, wlanName);

        switch (driver) {
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
