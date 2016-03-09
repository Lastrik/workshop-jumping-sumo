package de.bischinger.parrot.lib.command;

/**
 * This interface represents a command sent to the jumping sumo.
 *
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public interface Command {

    /**
     * Returns the byte package of the specific command.
     *
     * <p>TODO: describe the Package format and counter</p>
     *
     * @param  counter
     *
     * @return  byte package of command
     */
    byte[] getBytes(int counter);


    /**
     * TODO Describe why this is needed.
     *
     * @return
     */
    Acknowledge getAcknowledge();


    /**
     * Define the time to wait after a command was send to the drone to wait until the next command should be fired.
     *
     * @return  time to wait until send next command to the drone
     */
    default int waitingTime() {

        return 500;
    }
}
