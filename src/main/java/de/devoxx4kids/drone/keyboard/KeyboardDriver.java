package de.devoxx4kids.drone.keyboard;

import de.devoxx4kids.drone.DroneController;

import de.devoxx4kids.dronecontroller.command.movement.Jump;
import de.devoxx4kids.dronecontroller.command.movement.Pcmd;
import de.devoxx4kids.dronecontroller.command.multimedia.AudioTheme;
import de.devoxx4kids.dronecontroller.network.DroneConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import java.io.IOException;

import java.lang.invoke.MethodHandles;

import static java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager;
import static java.awt.event.KeyEvent.KEY_PRESSED;
import static java.awt.event.KeyEvent.KEY_RELEASED;
import static java.awt.event.KeyEvent.VK_0;
import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_4;
import static java.awt.event.KeyEvent.VK_5;
import static java.awt.event.KeyEvent.VK_6;
import static java.awt.event.KeyEvent.VK_7;
import static java.awt.event.KeyEvent.VK_8;
import static java.awt.event.KeyEvent.VK_9;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_I;
import static java.awt.event.KeyEvent.VK_J;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_X;
import static java.awt.event.KeyEvent.VK_Y;

import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class KeyboardDriver implements Runnable, KeyEventDispatcher {

    public static final int DEFAULT_TURN_DEGREE = 50;
    public static final int DEFAULT_SPEED = 100;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DroneController droneController;
    private final int straightSpeed;
    private final int turnDegrees;

    private boolean isUpPressed;
    private boolean isDownPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;

    public KeyboardDriver(DroneConnection droneConnection, int straightSpeed, int turnDegrees) {

        this.straightSpeed = straightSpeed;
        this.turnDegrees = turnDegrees;
        this.droneController = new DroneController(droneConnection);

        initComponents();
    }

    private void initComponents() {

        new KeyboardDriverFrame(droneController);
        new Thread(this).start();

        droneController.addBatteryListener(b -> LOGGER.info("BatteryState: {}%", b));
        // droneController.addPCMDListener(b -> LOGGER.info("PCMD: " + b));

        droneController.video().enableVideo();

        getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }


    @Override
    public void run() {

        while (true) {
            try {
                MILLISECONDS.sleep(10);

                // set speed
                int speed = 0;

                if (isUpPressed) {
                    speed = this.straightSpeed;
                } else if (isDownPressed) {
                    speed = -1 * this.straightSpeed;
                }

                // set turn degrees
                int degrees = 0;

                if (isLeftPressed) {
                    degrees = isDownPressed ? turnDegrees : -turnDegrees;
                } else if (isRightPressed) {
                    degrees = isDownPressed ? -turnDegrees : turnDegrees;
                }

                if (speed != 0 || degrees != 0) {
                    droneController.send(Pcmd.pcmd(speed, degrees, 0));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (e.getID() == KEY_PRESSED) {
            try {
                handleKeyPressed(keyCode);
            } catch (IOException | InterruptedException e1) {
                e1.printStackTrace();
            }
        } else if (e.getID() == KEY_RELEASED) {
            handleKeyReleased(keyCode);
        }

        return true;
    }


    private void handleKeyReleased(int keyCode) {

        switch (keyCode) {
            case VK_UP:
                isUpPressed = false;
                break;

            case VK_DOWN:
                isDownPressed = false;
                break;

            case VK_LEFT:
                isLeftPressed = false;
                break;

            case VK_RIGHT:
                isRightPressed = false;
                break;
        }
    }


    private void handleKeyPressed(int keyCode) throws IOException, InterruptedException {

        switch (keyCode) {
            case VK_UP:
                isUpPressed = true;
                break;

            case VK_DOWN:
                isDownPressed = true;
                break;

            case VK_LEFT:
                isLeftPressed = true;
                break;

            case VK_RIGHT:
                isRightPressed = true;
                break;

            case VK_H:
                droneController.jump(Jump.Type.High);
                break;

            case VK_J:
                droneController.jump(Jump.Type.Long);
                break;

            case VK_1:
                droneController.spin();
                break;

            case VK_2:
                droneController.tap();
                break;

            case VK_3:
                droneController.slowShake();
                break;

            case VK_4:
                droneController.metronome();
                break;

            case VK_5:
                droneController.ondulation();
                break;

            case VK_6:
                droneController.spinJump();
                break;

            case VK_7:
                droneController.spinToPosture();
                break;

            case VK_8:
                droneController.spiral();
                break;

            case VK_9:
                droneController.slalom();
                break;

            case VK_0:
                droneController.stopAnimation();
                break;

            case VK_A:
                droneController.pcmd(0, -90);
                break;

            case VK_D:
                droneController.pcmd(0, 90);
                break;

            case VK_S:
                droneController.pcmd(0, 180);
                break;

            case VK_I:
                droneController.audio().theme(AudioTheme.Theme.Monster);
                break;

            case VK_O:
                droneController.audio().theme(AudioTheme.Theme.Insect);
                break;

            case VK_P:
                droneController.audio().theme(AudioTheme.Theme.Robot);
                break;

            case VK_Y:
                droneController.audio().mute();
                break;

            case VK_X:
                droneController.audio().unmute();
                break;

            case VK_V:
                droneController.connect();
                break;

            case VK_B:
                droneController.disconnect();
                break;
        }
    }
}
