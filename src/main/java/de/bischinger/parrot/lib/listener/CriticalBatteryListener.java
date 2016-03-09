package de.bischinger.parrot.lib.listener;

import java.util.function.Consumer;


/**
 * @author  Tobias Schneider
 */
public final class CriticalBatteryListener implements EventListener {

    private final Consumer<BatteryState> consumer;

    protected CriticalBatteryListener(Consumer<BatteryState> consumer) {

        this.consumer = consumer;
    }

    public static CriticalBatteryListener criticalBatteryListener(Consumer<BatteryState> consumer) {

        return new CriticalBatteryListener(consumer);
    }


    @Override
    public void eventFired(byte[] data) {

        if (filterProject(data, 3, 1, 1)) {
            consumer.accept(BatteryState.values()[data[11]]);
        }
    }
}
