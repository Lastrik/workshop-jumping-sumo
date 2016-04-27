package de.devoxx4kids.drone.control.driver.keyboard;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;


/**
 * Small swing component as interceptor for the key events.
 *
 * @author  Tobias Schneider
 */
public class KeyboardDriverFrame extends JFrame {

    public KeyboardDriverFrame() {

        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setSize(640, 480);
        setVisible(true);

        JLabel jLabel = new JLabel();
        add(jLabel);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                File frame = new File("frame.jpg");

                if (frame.exists()) {
                    try {
                        BufferedImage myPicture = ImageIO.read(frame);

                        jLabel.setIcon(new ImageIcon(myPicture));
                        jLabel.repaint();
                        jLabel.revalidate();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
