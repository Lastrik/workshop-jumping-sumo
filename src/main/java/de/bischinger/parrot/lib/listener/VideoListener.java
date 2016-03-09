package de.bischinger.parrot.lib.listener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author  Tobias Schneider
 */
public class VideoListener implements EventListener {

    private final FileOutputStream fileOutputStream;
    private int pictureCounter = 0;
    private byte[] temp = new byte[] {};

    public VideoListener() throws FileNotFoundException {

        fileOutputStream = new FileOutputStream("video.mp4");
    }

    public static VideoListener videoListener() throws FileNotFoundException {

        return new VideoListener();
    }


    @Override
    public void eventFired(byte[] data) {

        if (data[1] == 125) {
            concatenateByteArrays(temp, getJpegDate(data));

            if (pictureCounter == 15) {
                try {
                    fileOutputStream.write(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            pictureCounter++;
        }
    }


    private byte[] getJpegDate(byte[] data) {

        byte[] jpegData = new byte[data.length];
        System.arraycopy(data, 12, jpegData, 0, data.length - 12);

        return jpegData;
    }


    private byte[] concatenateByteArrays(byte[] a, byte[] b) {

        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);

        System.out.println("a " + a.toString());
        System.out.println("b " + b.toString());
        System.out.println("result " + result.toString());

        return result;
    }
}
