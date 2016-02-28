package de.bischinger.parrot.driver.keyboard;

import javax.swing.JFrame;
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
        setOpacity(0.8f);
        setSize(200, 200);
        setVisible(true);
    }
}
