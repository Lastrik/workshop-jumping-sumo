package de.bischinger.parrot.lib.command.common;

import de.bischinger.parrot.lib.command.Command;


/**
 * Interface for basic/common commands to keep the connection to the drone or something else.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public interface CommonCommand extends Command {

    /**
     * Time to wait to send the next command after this on.
     *
     * <p>Define the time to wait after a command was send to the drone to wait until the next command should be fired.
     * This will be send at 1/10 Hz</p>
     *
     * @return  time to wait until send next command to the drone
     */
    @Override
    default int waitingTime() {

        return 100;
    }
}
