package de.bischinger.parrot.common;

import de.bischinger.parrot.commands.common.CurrentDate;

import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link CurrentDate}.
 *
 * @author  Tobias Schneider
 */
public class CurrentDateTest {

    @Test
    public void getBytes() {

        byte[] bytesPackage = CurrentDate.currentDate(Clock.fixed(Instant.EPOCH, ZoneId.systemDefault())).getBytes(1);

        assertThat(bytesPackage,
            is(new byte[] { 4, 11, 1, 15, 0, 0, 0, 0, 4, 0, 0, 49, 57, 55, 48, 45, 48, 49, 45, 48, 49, 0 }));
    }
}
