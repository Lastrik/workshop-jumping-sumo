package de.devoxx4kids.drone.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.util.Properties;

import static de.devoxx4kids.drone.config.Driver.KEYBOARD;

import static java.lang.Integer.valueOf;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class ConfigReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String DEFAULT_IP = "192.168.2.1";
    private static final int DEFAULT_PORT = 4444;
    private static final String DEFAULT_WLAN = "JumpingSumo";
    private static final Driver DEFAULT_DRIVER = KEYBOARD;
    private static final int DEFAULT_TURN_DEGREE = 50;
    private static final int DEFAULT_SPEED = 100;

    public Config get(String[] args) {

        Driver driver = getDriver(args);
        int speed = getSpeed(args);
        int turn = getTurn(args);

        Config configuration = null;
        File configFile = new File("config.properties");

        if (configFile.exists()) {
            try {
                Properties properties = new Properties();
                properties.load(new FileReader(configFile));

                String ip = properties.getProperty("ip");
                int port = valueOf(properties.getProperty("port"));
                String wlanName = properties.getProperty("wlan");

                configuration = new Config(ip, port, wlanName, driver, speed, turn);
            } catch (IOException e) {
                configuration = new Config(DEFAULT_IP, DEFAULT_PORT, DEFAULT_WLAN, driver, speed, turn);
                LOGGER.error("Could not create configuration, use default values - " + driver, e);
            }
        }

        return configuration;
    }


    private int getTurn(String[] args) {

        return args.length > 2 ? valueOf(args[2]) : DEFAULT_TURN_DEGREE;
    }


    private int getSpeed(String[] args) {

        return args.length > 1 ? valueOf(args[1]) : DEFAULT_SPEED;
    }


    private Driver getDriver(String[] args) {

        Driver driver = DEFAULT_DRIVER;

        if (args.length >= 1) {
            driver = Driver.valueOf(args[0].toLowerCase());
        }

        return driver;
    }
}
