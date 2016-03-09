package de.bischinger.parrot.commands.movement;

import de.bischinger.parrot.lib.command.Acknowledge;
import de.bischinger.parrot.lib.command.animation.Spin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link Spin}.
 *
 * @author  Tobias Schneider
 */
public class SpinTest {

    private Spin sut;

    @Before
    public void setUp() throws Exception {

        sut = Spin.spin();
    }


    @Test
    public void getBytes() {

        byte[] bytesPackage = sut.getBytes(1);

        assertThat(bytesPackage, is(new byte[] { 4, 11, 1, 15, 0, 0, 0, 3, 2, 4, 0, 1, 0, 0, 0 }));
    }


    @Test
    public void getAcknowledge() {

        Acknowledge acknowledge = sut.getAcknowledge();
        assertThat(acknowledge, is(Acknowledge.AckBefore));
    }
}
