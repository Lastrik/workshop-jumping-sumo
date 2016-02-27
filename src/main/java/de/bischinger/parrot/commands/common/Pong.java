package de.bischinger.parrot.commands.common;

import de.bischinger.parrot.commands.Command;


/**
 * @author  Alexander Bischof
 */
public class Pong implements Command {

    public static Pong pong() {

        return new Pong();
    }


    @Override
    public byte[] getBytes(int counter) {

        return new byte[] { 1, (byte) 0xfe, (byte) counter, 8, 0, 0, 0, (byte) counter };
    }
}
