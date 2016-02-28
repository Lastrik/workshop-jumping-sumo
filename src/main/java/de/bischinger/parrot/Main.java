package de.bischinger.parrot;

import de.bischinger.parrot.driver.keyboard.KeyboardDriver;
import de.bischinger.parrot.driver.naturallanguage.FileBasedProgrammaticDriver;
import de.bischinger.parrot.driver.naturallanguage.JumpingSumoLang;
import de.bischinger.parrot.driver.naturallanguage.SwingBasedProgrammaticDriver;
import de.bischinger.parrot.driver.programmatic.ProgrammaticDriver;

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

    private Main() {
    }

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, AWTException {

        System.setProperty("java.util.logging.SimpleFormatter.format",
            "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");

        String ip = "192.168.2.1";
        int port = 44444;
        String wlan = "JS-Alex";

        File configFile = new File("config.properties");

        if (configFile.exists()) {
            Properties properties = new Properties();
            properties.load(new FileReader(configFile));

            ip = properties.getProperty("ip");
            port = valueOf(properties.getProperty("port"));
            wlan = properties.getProperty("wlan");
        }

        String driver = "keyboard";

        if (args.length > 1) {
            driver = args[0].toLowerCase();
        }

        switch (driver) {
            case "keyboard":

                int speedConfig = args.length > 1 ? valueOf(args[1]) : KeyboardDriver.DEFAULT_SPEED;
                int turnConfig = args.length > 2 ? valueOf(args[2]) : KeyboardDriver.DEFAULT_TURN_DEGREE;
                new KeyboardDriver(ip, port, wlan, speedConfig, turnConfig);
                break;

            case "program":
                new ProgrammaticDriver(ip, port, wlan).drive();
                break;

            case "file":
                new FileBasedProgrammaticDriver(ip, port, wlan);
                break;

            case "swing":
                new SwingBasedProgrammaticDriver(ip, port, wlan);
                break;

            case "ant4lr":
                new JumpingSumoLang().execute();
                break;

            default:
                LOGGER.info("Argument unbekannt: keyboard | program | file | swing");
        }
    }
}
