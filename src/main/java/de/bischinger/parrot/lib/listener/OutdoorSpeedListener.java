package de.bischinger.parrot.lib.listener;

import java.util.function.Consumer;


/**
 * @author  Tobias Schneider
 */
public final class OutdoorSpeedListener implements EventListener {

    private final Consumer<String> consumer;

    protected OutdoorSpeedListener(Consumer<String> consumer) {

        this.consumer = consumer;
    }

    public static OutdoorSpeedListener outdoorSpeedListener(Consumer<String> consumer) {

        return new OutdoorSpeedListener(consumer);
    }


    @Override
    public void eventFired(byte[] data) {

        if (filterProject(data, 3, 17, 0)) {
            consumer.accept(Byte.toString(data[11]));
        }
    }
}
