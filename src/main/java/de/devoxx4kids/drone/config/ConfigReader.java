package de.devoxx4kids.drone.config;

import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.util.Properties;

import static java.lang.Integer.valueOf;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class ConfigReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String DEFAULT_IP = "192.168.2.1";
    private static final int DEFAULT_PORT = 4444;
    private static final String DEFAULT_WLAN = "JumpingSumo";

    public Config get(String[] args) {

        Driver driver = getDriver(args);

        Config configuration = null;
        File configFile = new File("config.properties");

        if (configFile.exists()) {
            try {
                Properties properties = new Properties();
                properties.load(new FileReader(configFile));

                String ip = properties.getProperty("ip");
                int port = valueOf(properties.getProperty("port"));
                String wlanName = properties.getProperty("wlan");

                configuration = new Config(ip, port, wlanName, driver);
            } catch (IOException e) {
                configuration = new Config(DEFAULT_IP, DEFAULT_PORT, DEFAULT_WLAN, driver);
                LOGGER.error("Could not create configuration, use default values - " + driver, e);
            }
        }

        return configuration;
    }


    @NotNull
    private Driver getDriver(String[] args) {

        Driver driver = Driver.KEYBOARD;

        if (args.length >= 1) {
            driver = Driver.valueOf(args[0].toLowerCase());
        }

        return driver;
    }
}
