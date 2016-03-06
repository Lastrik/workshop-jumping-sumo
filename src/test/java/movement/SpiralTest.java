package de.bischinger.parrot.commands.movement;

import de.bischinger.parrot.commands.Acknowledge;
import de.bischinger.parrot.commands.animation.SpinToPosture;
import de.bischinger.parrot.commands.animation.Spiral;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test of {@link SpinToPosture}.
 *
 * @author  Tobias Schneider
 */
public class SpiralTest {

    private Spiral sut;

    @Before
    public void setUp() throws Exception {

        sut = Spiral.spiral();
    }


    @Test
    public void getBytes() {

        byte[] bytesPackage = sut.getBytes(1);

        assertThat(bytesPackage, is(new byte[] { 4, 11, 1, 15, 0, 0, 0, 3, 2, 4, 0, 8, 0, 0, 0 }));
    }


    @Test
    public void getAcknowledge() {

        Acknowledge acknowledge = sut.getAcknowledge();
        assertThat(acknowledge, is(Acknowledge.AckBefore));
    }
}
