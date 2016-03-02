package de.bischinger.parrot.commands.jumpingsumo;

import de.bischinger.parrot.commands.Acknowledge;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;


/**
 * Unit test of {@link VideoStreaming}.
 *
 * @author  Tobias Schneider
 */
public class VideoStreamingTest {

    @Test
    public void enableVideoStreamingGetBytes() {

        VideoStreaming sut = VideoStreaming.enableVideoStreaming();
        byte[] bytesPacket = sut.getBytes(1);

        assertThat(bytesPacket, is(new byte[] { 4, 11, 1, 12, 0, 0, 0, 3, 18, 0, 0, 1, 0 }));
    }


    @Test
    public void disableVideoStreamingGetBytes() {

        VideoStreaming sut = VideoStreaming.disableVideoStreaming();
        byte[] bytesPacket = sut.getBytes(1);

        assertThat(bytesPacket, is(new byte[] { 4, 11, 1, 12, 0, 0, 0, 3, 18, 0, 0, 0, 0 }));
    }


    @Test
    public void getAcknowledge() {

        VideoStreaming sut = VideoStreaming.enableVideoStreaming();
        Acknowledge acknowledge = sut.getAcknowledge();
        assertThat(acknowledge, is(Acknowledge.AckAfter));
    }
}
