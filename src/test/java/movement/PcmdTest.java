package de.bischinger.parrot.commands.movement;

import de.bischinger.parrot.lib.command.movement.Pcmd;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link Pcmd}.
 *
 * @author  Tobias Schneider
 */
public class PcmdTest {

    @Test(expected = IllegalArgumentException.class)
    public void getBytesOverflowInSpeedLow() {

        Pcmd.pcmd(-129, 0);
    }


    @Test(expected = IllegalArgumentException.class)
    public void getBytesOverflowInSpeedHigh() {

        Pcmd.pcmd(128, 0);
    }


    @Test
    public void getBytesWIthCorrect180DegreeTo50Percent() {

        byte[] bytesPackage = Pcmd.pcmd(40, 180).getBytes(1);

        assertThat(bytesPackage, is(new byte[] { 2, 10, 1, 14, 0, 0, 0, 3, 0, 0, 0, 1, 40, 50 }));
    }
}
