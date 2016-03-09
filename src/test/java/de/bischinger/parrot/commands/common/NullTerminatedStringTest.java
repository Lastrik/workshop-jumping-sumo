package de.bischinger.parrot.commands.common;

import de.bischinger.parrot.lib.command.common.NullTerminatedString;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import static java.time.format.DateTimeFormatter.ISO_DATE;


/**
 * Unit test of {@link NullTerminatedString}.
 *
 * @author  Tobias Schneider
 */
public class NullTerminatedStringTest {

    @Test
    public void getBytes() {

        byte[] bytesPackage = new NullTerminatedString(LocalDate.of(2015, 12, 1).format(ISO_DATE))
            .getNullTerminatedString();

        assertThat(bytesPackage, is(new byte[] { 50, 48, 49, 53, 45, 49, 50, 45, 48, 49, 0 }));
    }
}
