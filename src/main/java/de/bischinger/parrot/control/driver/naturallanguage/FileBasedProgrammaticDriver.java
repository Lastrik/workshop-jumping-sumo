package de.bischinger.parrot.control.driver.naturallanguage;

import de.bischinger.parrot.control.DroneController;
import de.bischinger.parrot.lib.network.DroneConnection;

import java.io.File;
import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.net.URISyntaxException;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.List;
import java.util.logging.Logger;


/**
 * @author  Alexander Bischof
 */
public class FileBasedProgrammaticDriver {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());
    private static final String FILENAME = "programm.txt";

    private final DroneController drone;

    public FileBasedProgrammaticDriver(DroneConnection droneConnection) throws IOException, URISyntaxException {

        drone = new DroneController(droneConnection);

        // FIXME
        // drone.addBatteryListener(b -> System.out.println("BatteryState: " + b));
        drone.pcmd(0, 0);

        new Thread(this::startFileWatcher).start();

        readCommands();
    }

    private void startFileWatcher() {

        try(final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path path = Paths.get(new File("").toURI());
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            LOGGER.info("Programm gestartet: Schaue auf " + Paths.get(FILENAME));

            while (true) {
                final WatchKey wk = watchService.take();

                for (WatchEvent<?> event : wk.pollEvents()) {
                    final Path changed = (Path) event.context();

                    // FIXME
                    // System.out.println(changed + " " + changed.equals(FILENAME) + " " + FILENAME);
                    if (changed.endsWith(FILENAME)) {
                        LOGGER.info("programm.txt has changed");
                        readCommands();
                    }
                }

                boolean valid = wk.reset();

                if (!valid) {
                    LOGGER.info("Key has been unregisterede");
                }
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void readCommands() throws URISyntaxException, IOException {

        List<String> commands = Files.readAllLines(Paths.get(FILENAME));
        commands.forEach(new CommandInputConsumer(drone));
    }
}
