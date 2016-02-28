package de.bischinger.parrot.commands.common;

import org.junit.Test;

import java.time.Clock;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import static java.time.LocalDate.now;
import static java.time.format.DateTimeFormatter.ISO_DATE;


/**
 * Unit test of {@link NullTerminatedString}.
 *
 * @author  Tobias Schneider
 */
public class NullTerminatedStringTest {

    @Test
    public void getBytes() {

        byte[] bytesPackage = new NullTerminatedString(now(Clock.systemDefaultZone()).format(ISO_DATE))
            .getNullTerminatedString();

        assertThat(bytesPackage, is(new byte[] { 50, 48, 49, 54, 45, 48, 50, 45, 50, 56, 0 }));
    }
}
