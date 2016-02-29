package de.bischinger.parrot.commands.common;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link Pong}.
 *
 * @author  Tobias Schneider
 */
public class PongTest {

    @Test
    public void getBytes() {

        int notNeededCounter = 2;
        byte[] bytesPackage = Pong.pong(1).getBytes(notNeededCounter);

        assertThat(bytesPackage, is(new byte[] { 1, -2, 1, 8, 0, 0, 0, 1 }));
    }
}
