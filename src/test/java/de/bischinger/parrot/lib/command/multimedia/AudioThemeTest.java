package de.bischinger.parrot.lib.command.multimedia;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link AudioTheme}.
 *
 * @author  Tobias Schneider
 */
public class AudioThemeTest {

    @Test
    public void themes() {

        assertThat(AudioTheme.Theme.Default.ordinal(), is(0));
        assertThat(AudioTheme.Theme.Robot.ordinal(), is(1));
        assertThat(AudioTheme.Theme.Insect.ordinal(), is(2));
        assertThat(AudioTheme.Theme.Monster.ordinal(), is(3));
    }


    @Test
    public void getBytes() {

        byte[] bytesPackage = AudioTheme.audioTheme(AudioTheme.Theme.Default).getBytes(1);

        assertThat(bytesPackage, is(new byte[] { 4, 11, 1, 15, 0, 0, 0, 3, 12, 1, 0, 0, 0, 0, 0 }));
    }
}
