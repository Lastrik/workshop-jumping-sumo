package de.bischinger.parrot.commands.jumpingsumo;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link Pcmd}.
 *
 * @author  Tobias Schneider
 */
public class PcmdTest {

    @Test
    public void getBytesWIthCorrect180DegreeTo50Percent() {

        byte[] bytesPackage = Pcmd.pcmd(40, 180).getBytes(1);

        assertThat(bytesPackage, is(new byte[] { 2, 10, 1, 14, 0, 0, 0, 3, 0, 0, 0, 1, 40, 50 }));
    }
}
