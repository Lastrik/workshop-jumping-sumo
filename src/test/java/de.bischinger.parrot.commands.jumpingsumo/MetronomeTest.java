package de.bischinger.parrot.commands.jumpingsumo;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link Metronome}.
 *
 * @author  Tobias Schneider
 */
public class MetronomeTest {

    @Test
    public void getBytes() {

        byte[] bytesPackage = Metronome.metronome().getBytes(1);

        assertThat(bytesPackage, is(new byte[] { 4, 11, 1, 15, 0, 0, 0, 3, 2, 4, 0, 4, 0, 0, 0 }));
    }
}
