package de.bischinger.parrot.driver.naturallanguage;

import de.bischinger.parrot.network.DroneController;
import de.bischinger.parrot.network.handshake.HandshakeRequest;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.lang.invoke.MethodHandles;

import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;

import static java.util.stream.Stream.of;


public class SwingBasedProgrammaticDriver extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());

    private final DroneController drone;

    public SwingBasedProgrammaticDriver(String ip, int port, String sumoWlan) throws Exception {

        drone = new DroneController(ip, port, new HandshakeRequest(sumoWlan, "_arsdk-0902._udp"), true);

        initComponents();
    }

    private void initComponents() {

        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setMinimumSize(new Dimension(700, 800));

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTextArea textArea = new JTextArea("", 5, 10);
        textArea.setPreferredSize(new Dimension(600, 700));

        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(scrollPane, CENTER);

        JButton jbStart = new JButton("Start");
        jbStart.addActionListener(e -> {
            String text = textArea.getText();
            of(text.split("\\r?\\n")).map(c -> c.toLowerCase().trim()).forEach(new CommandInputConsumer(drone));
        });
        this.add(jbStart, SOUTH);
        this.pack();
        setVisible(true);

        drone.addBatteryListener(b -> LOGGER.info("BatteryState: " + b));
        drone.addCriticalBatteryListener(b -> LOGGER.info("Critical-BatteryState: " + b));
        drone.addPCMDListener(b -> LOGGER.info("PCMD: " + b));
    }
}
