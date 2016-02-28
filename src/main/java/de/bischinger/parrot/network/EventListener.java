package de.bischinger.parrot.network;

/**
 * @author  Alexander Bischof
 */
@FunctionalInterface
public interface EventListener {

    void eventFired(byte[] data);
}
