package de.bischinger.parrot.driver.keyboard;

import de.bischinger.parrot.commands.jumpingsumo.AudioTheme;
import de.bischinger.parrot.commands.jumpingsumo.Jump;
import de.bischinger.parrot.network.DroneController;
import de.bischinger.parrot.network.handshake.HandshakeRequest;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

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
import static java.awt.event.KeyEvent.VK_X;
import static java.awt.event.KeyEvent.VK_Y;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;


public class KeyboardDriver extends JFrame implements Runnable, KeyEventDispatcher {

    private final int speedConfig;
    private final int turnspeedConfig;

    private DroneController droneController;
    private boolean isUpPressed;
    private boolean isDownPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;

    public KeyboardDriver(String ip, int port, String sumoWlan, int speedConfig, int turnspeedConfig) throws Exception {

        this.speedConfig = speedConfig;
        this.turnspeedConfig = turnspeedConfig;
        droneController = new DroneController(ip, port, new HandshakeRequest(sumoWlan, "_arsdk-0902._udp"), false);

        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setOpacity(0.8f);
        setSize(200, 200);
        setVisible(true);
        new Thread(this).start();

        droneController.addBatteryListener(b -> System.out.println("BatteryState: " + b));
        droneController.addCriticalBatteryListener(b -> System.out.println("Critical-BatteryState: " + b));
        droneController.addPCMDListener(b -> System.out.println("PCMD: " + b));
        droneController.addOutdoorSpeedListener(b -> System.out.println("Speed: " + b));

        getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }


    @Override
    public void run() {

        while (true) {
            try {
                MILLISECONDS.sleep(100);

                // set speed
                int speed = 0;

                if (isUpPressed)
                    speed = speedConfig;
                else if (isDownPressed)
                    speed = -1 * speedConfig;

                // set direction
                int direction = 0;

                if (isLeftPressed)
                    direction = -turnspeedConfig;
                else if (isRightPressed)
                    direction = turnspeedConfig;

                if (speed != 0 || direction != 0)
                    droneController.pcmd(speed, direction);
            } catch (Exception e1) {
                e1.printStackTrace();
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
                SECONDS.sleep(2);
                break;

            case VK_J:
                droneController.jump(Jump.Type.Long);
                break;

            case VK_1:
                droneController.spin();
                SECONDS.sleep(1);
                break;

            case VK_2:
                droneController.tap();
                SECONDS.sleep(1);
                break;

            case VK_3:
                droneController.slowShake();
                SECONDS.sleep(1);
                break;

            case VK_4:
                droneController.metronome();
                SECONDS.sleep(1);
                break;

            case VK_5:
                droneController.ondulation();
                SECONDS.sleep(1);
                break;

            case VK_6:
                droneController.spinJump();
                SECONDS.sleep(1);
                break;

            case VK_7:
                droneController.spinToPosture();
                SECONDS.sleep(1);
                break;

            case VK_8:
                droneController.spiral();
                SECONDS.sleep(1);
                break;

            case VK_9:
                droneController.slalom();
                SECONDS.sleep(1);
                break;

            case VK_0:
                droneController.stopAnnimation();
                break;

            case VK_A:
                turn90Left();
                SECONDS.sleep(2);
                break;

            case VK_D:
                turn90Right();
                SECONDS.sleep(2);
                break;

            case VK_S:
                turn180();
                SECONDS.sleep(2);
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
        }
    }


    private void turn90Right() throws IOException {

        droneController.pcmd(0, 25);
    }


    private void turn90Left() throws IOException {

        droneController.pcmd(0, -25);
    }


    private void turn180() throws IOException {

        droneController.pcmd(0, 50);
    }
}
