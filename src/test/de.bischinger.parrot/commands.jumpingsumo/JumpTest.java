package de.bischinger.parrot.commands.jumpingsumo;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author  Tobias Schneider
 */
public class JumpTest {

    @Test
    public void themes() {

        assertThat(Jump.Type.Long.ordinal(), is(0));
        assertThat(Jump.Type.High.ordinal(), is(1));
    }


    @Test
    public void getBytes() {

        byte[] bytesPackage = Jump.jump(Jump.Type.High).getBytes(1);
        assertThat(bytesPackage, is(new byte[] { 4, 11, 1, 15, 0, 0, 0, 3, 2, 3, 0, 1, 0, 0, 0 }));
    }
}
