package de.devoxx4kids.drone.keyboard;

import de.devoxx4kids.drone.DroneController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.lang.invoke.MethodHandles;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


/**
 * Small swing component as interceptor for the key events.
 *
 * @author  Tobias Schneider
 */
public class KeyboardDriverFrame extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public KeyboardDriverFrame(DroneController droneController) {

        setResizable(false);
        setUndecorated(true);
        setSize(640, 480);
        setVisible(true);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu jsMenu = new JMenu("Menu");

        JMenuItem reconnectItem = new JMenuItem("Reconnect");
        reconnectItem.addActionListener(e -> droneController.connect());
        jsMenu.add(reconnectItem);
        jsMenu.add(new JPopupMenu.Separator());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        jsMenu.add(exitItem);

        menuBar.add(jsMenu);
        this.setJMenuBar(menuBar);

        JLabel livePicture = new JLabel();
        add(livePicture);

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

                        if (myPicture != null) {
                            livePicture.setIcon(new ImageIcon(myPicture));
                            livePicture.repaint();
                            livePicture.revalidate();
                        }
                    } catch (IOException e) {
                        LOGGER.warn("Could not process picture", e);
                    }
                }
            }
        }).start();
    }
}
