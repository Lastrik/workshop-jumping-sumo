package de.bischinger.parrot.lib.command.animation;

import de.bischinger.parrot.lib.command.Acknowledge;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link Metronome}.
 *
 * @author  Tobias Schneider
 */
public class MetronomeTest {

    private Metronome sut;

    @Before
    public void setUp() throws Exception {

        sut = Metronome.metronome();
    }


    @Test
    public void getBytes() {

        byte[] bytesPackage = sut.getBytes(1);

        assertThat(bytesPackage, is(new byte[] { 4, 11, 1, 15, 0, 0, 0, 3, 2, 4, 0, 4, 0, 0, 0 }));
    }


    @Test
    public void getAcknowledge() {

        Acknowledge acknowledge = sut.getAcknowledge();
        assertThat(acknowledge, is(Acknowledge.AckBefore));
    }
}
