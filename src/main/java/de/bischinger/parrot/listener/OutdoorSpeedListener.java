package de.bischinger.parrot.listener;

import java.util.function.Consumer;


/**
 * @author  Tobias Schneider
 */
public class OutdoorSpeedListener implements EventListener {

    private Consumer<String> consumer;

    protected OutdoorSpeedListener(Consumer<String> consumer) {

        this.consumer = consumer;
    }

    public static OutdoorSpeedListener outdoorSpeedListener(Consumer<String> consumer) {

        return new OutdoorSpeedListener(consumer);
    }


    @Override
    public void eventFired(byte[] data) {

        if (filterProject(data, 3, 17, 0)) {
            consumer.accept("" + data[11]);
        }
    }
}
