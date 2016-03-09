package de.bischinger.parrot.commands.common;

import de.bischinger.parrot.lib.command.common.CurrentTime;

import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link CurrentTime}.
 *
 * @author  Tobias Schneider
 */
public class CurrentTimeTest {

    @Test
    public void getBytes() {

        byte[] bytesPackage = CurrentTime.currentTime(Clock.fixed(Instant.EPOCH, ZoneId.of("Europe/Berlin")))
            .getBytes(1);

        assertThat(bytesPackage,
            is(new byte[] { 4, 11, 1, 15, 0, 0, 0, 0, 4, 1, 0, 84, 48, 49, 48, 48, 48, 48, 43, 48, 49, 48, 48, 0 }));
    }
}
