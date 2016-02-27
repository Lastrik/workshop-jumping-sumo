package de.bischinger.parrot.driver.naturallanguage;

import de.bischinger.parrot.commands.jumpingsumo.Jump;
import de.bischinger.parrot.network.DroneController;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static java.lang.Integer.parseInt;


/**
 * @author  Alexander Bischof
 */
public class CommandInputConsumer implements Consumer<String> {

    private final DroneController drone;

    public CommandInputConsumer(DroneController drone) {

        this.drone = drone;
    }

    @Override
    public void accept(String command) {

        try {
            String lowercaseCommand = command.toLowerCase().trim();

            switch (lowercaseCommand) {
                case "vor":
                    drone.forward();
                    break;

                case "rechts":
                    drone.right();
                    break;

                case "links":
                    drone.left();
                    break;

                case "zurueck":
                    drone.backward();
                    break;

                case "springe hoch":
                    drone.jump(Jump.Type.High);
                    break;

                case "springe weit":
                    drone.jump(Jump.Type.Long);
                    break;

                default:
                    if (lowercaseCommand.startsWith("warte")) {
                        int seconds = parseInt(lowercaseCommand.split(" ")[1]);
                        TimeUnit.SECONDS.sleep(seconds);
                    } else
                    // Handling for links/rechts mit winkel
                    if (lowercaseCommand.startsWith("links")) {
                        int degrees = parseInt(lowercaseCommand.split(" ")[1]);
                        drone.left(degrees);
                    } else if (lowercaseCommand.startsWith("rechts")) {
                        int degrees = parseInt(lowercaseCommand.split(" ")[1]);
                        drone.right(degrees);
                    } else {
                        System.out.println("Kommando nicht implementiert: " + command);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
