package de.bischinger.parrot.listener;

import java.util.function.Consumer;


/**
 * @author  Tobias Schneider
 */
public final class PCMDListener implements EventListener {

    private final Consumer<String> consumer;

    protected PCMDListener(Consumer<String> consumer) {

        this.consumer = consumer;
    }

    public static PCMDListener pcmdlistener(Consumer<String> consumer) {

        return new PCMDListener(consumer);
    }


    @Override
    public void eventFired(byte[] data) {

        if (filterProject(data, 3, 1, 0)) {
            consumer.accept(Byte.toString(data[11]));
        }
    }
}
