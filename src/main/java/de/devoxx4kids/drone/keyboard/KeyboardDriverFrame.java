package de.devoxx4kids.drone.keyboard;

import de.devoxx4kids.drone.DroneController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Component;
import java.awt.event.ActionListener;
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
        setSize(640, 480);
        setVisible(true);

        // Menu
        JMenu jsMenu = new JMenu("Menu");
        add(jsMenu, e -> droneController.video().enableVideo(), "Enable Video");
        add(jsMenu, e -> droneController.video().disableVideo(), "Disable Video");
        add(jsMenu, e -> droneController.audio().mute(), "Mute Sound");
        add(jsMenu, e -> droneController.audio().unmute(), "Unmute Sound");
        addSeparator(jsMenu);

        add(jsMenu, e -> droneController.connect(), "Reconnect");
        addSeparator(jsMenu);

        add(jsMenu, e -> System.exit(0), "Exit");

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(jsMenu);
        this.setJMenuBar(menuBar);

        addLivePicture();
    }

    private void addLivePicture() {

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


    private Component addSeparator(JMenu jsMenu) {

        return jsMenu.add(new JPopupMenu.Separator());
    }


    private void add(JMenu jsMenu, ActionListener actionListener, String text) {

        JMenuItem enableVideo = new JMenuItem(text);
        enableVideo.addActionListener(actionListener);
        jsMenu.add(enableVideo);
    }
}
