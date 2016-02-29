package de.bischinger.parrot.listener;

import java.util.function.Consumer;


/**
 * @author  Tobias Schneider
 */
public class PCMDListener implements EventListener {

    private Consumer<String> consumer;

    protected PCMDListener(Consumer<String> consumer) {

        this.consumer = consumer;
    }

    public static PCMDListener PCMDListener(Consumer<String> consumer) {

        return new PCMDListener(consumer);
    }


    @Override
    public void eventFired(byte[] data) {

        if (filterProject(data, 3, 1, 0)) {
            consumer.accept("" + data[11]);
        }
    }
}
