package de.bischinger.parrot.commands.movement;

import de.bischinger.parrot.lib.command.multimedia.Volume;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link Volume}.
 *
 * @author  Tobias Schneider
 */
public class VolumeTest {

    @Test
    public void volume() {

        byte[] bytesPackage = Volume.volume(50).getBytes(1);

        assertThat(bytesPackage, is(new byte[] { 4, 11, 1, 12, 0, 0, 0, 3, 12, 0, 0, 50, 0 }));
    }


    @Test(expected = IllegalArgumentException.class)
    public void volumeToLow() {

        Volume.volume(-1);
    }


    @Test(expected = IllegalArgumentException.class)
    public void volumeToHigh() {

        Volume.volume(101);
    }
}
