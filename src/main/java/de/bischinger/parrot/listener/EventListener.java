package de.bischinger.parrot.listener;

/**
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
@FunctionalInterface
public interface EventListener {

    void eventFired(byte[] data);


    default boolean filterChannel(byte[] data, int frameType, int channel) {

        return data[0] == frameType && data[1] == channel;
    }


    default boolean filterProject(byte[] data, int project, int clazz, int cmd) {

        return data[7] == project && data[8] == clazz && data[9] == cmd;
    }
}
