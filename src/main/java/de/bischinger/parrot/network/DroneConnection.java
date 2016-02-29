package de.bischinger.parrot.network;

import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.listener.EventListener;

import java.io.IOException;


/**
 * @author  Tobias Schneider
 */
public interface DroneConnection extends AutoCloseable {

    /**
     * Connect with the drone with the constructor injected credentials.
     *
     * @throws  IOException
     */
    void connect() throws IOException;


    /**
     * Sends the given {@link Command} to the drone.
     *
     * @param  command  to send to drone
     *
     * @throws  IOException
     */
    void sendCommand(Command command) throws IOException;


    /**
     * Register the given {@link EventListener} to the {@link DroneConnection}.
     *
     * @param  eventListener  with the capsuled functionality
     */
    void addEventListener(EventListener eventListener);
}
