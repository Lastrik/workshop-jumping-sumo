package de.bischinger.parrot.lib.command.common;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link Disconnect}.
 *
 * @author  Tobias Schneider
 */
public class DisconnectTest {

    @Test
    public void getBytes() {

        byte[] bytesPackage = Disconnect.disconnect().getBytes(1);

        assertThat(bytesPackage, is(new byte[] { 4, 11, 1, 15, 0, 0, 0, 0, 0, 0, 0 }));
    }
}
