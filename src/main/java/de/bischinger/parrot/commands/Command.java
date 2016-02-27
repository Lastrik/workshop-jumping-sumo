package de.bischinger.parrot.commands;

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
}
