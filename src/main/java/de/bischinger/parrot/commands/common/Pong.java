package de.bischinger.parrot.commands.common;

import de.bischinger.parrot.commands.Acknowledge;
import de.bischinger.parrot.commands.Command;


/**
 * @author  Alexander Bischof
 */
public final class Pong implements Command {

    private final int counter;

    public Pong(int counter) {

        this.counter = counter;
    }

    public static Pong pong(int counter) {

        return new Pong(counter);
    }


    @Override
    public byte[] getBytes(int counter) {

        return new byte[] { 1, (byte) 0xfe, (byte) this.counter, 8, 0, 0, 0, (byte) this.counter };
    }


    @Override
    public Acknowledge getAcknowledge() {

        return Acknowledge.None;
    }


    @Override
    public String toString() {

        return "Pong";
    }
}
