package de.bischinger.parrot;

import de.bischinger.parrot.controller.DroneController;
import de.bischinger.parrot.driver.keyboard.KeyboardDriver;
import de.bischinger.parrot.driver.naturallanguage.FileBasedProgrammaticDriver;
import de.bischinger.parrot.driver.naturallanguage.JumpingSumoLang;
import de.bischinger.parrot.driver.naturallanguage.SwingBasedProgrammaticDriver;
import de.bischinger.parrot.driver.programmatic.ProgrammaticDriver;
import de.bischinger.parrot.lib.network.DroneConnection;
import de.bischinger.parrot.lib.network.WirelessLanDroneConnection;

import java.awt.AWTException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.net.URISyntaxException;

import java.util.Properties;
import java.util.logging.Logger;

import static java.lang.Integer.valueOf;


public final class Main {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());

    public static DroneController SINGLETON;

    private Main() {
    }

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, AWTException {

        System.setProperty("java.util.logging.SimpleFormatter.format",
            "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");

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

        String driver = "program";

        if (args.length >= 1) {
            driver = args[0].toLowerCase();
        }

        DroneConnection droneConnection = new WirelessLanDroneConnection(ip, port, wlanName);

        switch (driver) {
            case "keyboard":

                int speedConfig = args.length > 1 ? valueOf(args[1]) : KeyboardDriver.DEFAULT_SPEED;
                int turnConfig = args.length > 2 ? valueOf(args[2]) : KeyboardDriver.DEFAULT_TURN_DEGREE;
                new KeyboardDriver(droneConnection, speedConfig, turnConfig);
                break;

            case "program":
                new ProgrammaticDriver(droneConnection).drive();
                break;

            case "file":
                new FileBasedProgrammaticDriver(droneConnection);
                break;

            case "swing":
                SINGLETON = new DroneController(droneConnection);
                new SwingBasedProgrammaticDriver(SINGLETON).withDynamicCompilation().setVisible(true);
                break;

            case "ant4lr":
                new JumpingSumoLang().execute();
                break;

            default:
                LOGGER.info("Argument unbekannt: keyboard | program | file | swing");
        }
    }
}
