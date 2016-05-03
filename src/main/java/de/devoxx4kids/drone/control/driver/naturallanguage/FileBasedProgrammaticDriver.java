package de.devoxx4kids.drone.control.driver.naturallanguage;

import de.devoxx4kids.drone.control.DroneController;

import de.devoxx4kids.dronecontroller.network.DroneConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.List;


/**
 * FileBased Driver.
 *
 * @author  Alexander Bischof
 */
public class FileBasedProgrammaticDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String FILENAME = "programm.txt";

    private final DroneController drone;

    public FileBasedProgrammaticDriver(DroneConnection droneConnection) {

        drone = new DroneController(droneConnection);
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

                    if (changed.endsWith(FILENAME)) {
                        LOGGER.info("programm.txt has changed");
                        readCommands();
                    }
                }

                boolean valid = wk.reset();

                if (!valid) {
                    LOGGER.info("Key has been unregistered!");
                }
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.warn("Something went wrong", e);
        }
    }


    private void readCommands() {

        try {
            List<String> commands = Files.readAllLines(Paths.get(FILENAME));
            commands.forEach(new CommandInputConsumer(drone));
        } catch (IOException e) {
            LOGGER.warn("Could not read from file", e);
        }
    }
}
