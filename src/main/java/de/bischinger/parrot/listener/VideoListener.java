package de.bischinger.parrot.listener;

import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author  Tobias Schneider
 */
public class VideoListener implements EventListener {

    private int pictureCounter = 0;

    public static VideoListener videoListener() {

        return new VideoListener();
    }


    @Override
    public void eventFired(byte[] data) {

        if (data[1] == 125) {
            try {
                try(FileOutputStream fos = new FileOutputStream("picture" + pictureCounter + ".mp4")) {
                    fos.write(getJpegDate(data));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            pictureCounter++;
        }
    }


    private byte[] getJpegDate(byte[] data) {

        byte[] jpegData = new byte[data.length];
        System.arraycopy(data, 12, jpegData, 0, data.length - 12);

        return jpegData;
    }
}
