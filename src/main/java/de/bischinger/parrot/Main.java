package de.bischinger.parrot;

import de.bischinger.parrot.driver.keyboard.KeyboardDriver;
import de.bischinger.parrot.driver.naturallanguage.FileBasedProgrammaticDriver;
import de.bischinger.parrot.driver.naturallanguage.JumpingSumoLang;
import de.bischinger.parrot.driver.naturallanguage.SwingBasedProgrammaticDriver;
import de.bischinger.parrot.driver.programmatic.ProgrammaticDriver;

import java.io.File;
import java.io.FileReader;

import java.util.Properties;

import static java.lang.Integer.valueOf;


public class Main {

    private static final int DEFAULT_TURN_DEGREE = 25;
    private static final int DEFAULT_SPEED = 50;

    public static void main(String[] args) throws Exception {

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

        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "keyboard":

                    int speedConfig = args.length > 1 ? valueOf(args[1]) : DEFAULT_SPEED;
                    int turnConfig = args.length > 2 ? valueOf(args[2]) : DEFAULT_TURN_DEGREE;
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
                    System.out.println("Argument unbekannt: keyboard | program | file | swing");
            }
        } else {
            new ProgrammaticDriver(ip, port, wlan).drive();
        }
    }
}
