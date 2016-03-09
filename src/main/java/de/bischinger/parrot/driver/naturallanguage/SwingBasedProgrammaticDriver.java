package de.bischinger.parrot.driver.naturallanguage;

import de.bischinger.parrot.controller.DroneController;

import net.openhft.compiler.CachedCompiler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.util.function.Consumer;
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
    private Consumer<String> startOperation;
    private JTextArea textArea;

    public SwingBasedProgrammaticDriver(DroneController drone) throws IOException {

        this.drone = drone;

        // Default Operation uses old text base consumer
        startOperation = text ->
                of(text.split("\\r?\\n")).map(c -> c.toLowerCase().trim())
                .forEach(new CommandInputConsumer(drone));

        initComponents();
    }

    private void initComponents() {

        setResizable(true);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setPreferredSize(new Dimension(1200, 800));

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textArea = new JTextArea("", 5, 10);
        textArea.setPreferredSize(new Dimension(600, 700));
        textArea.setFont(new Font("serif", Font.PLAIN, 20));

        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(scrollPane, CENTER);

        JButton jbStart = new JButton("Start");
        jbStart.addActionListener(e -> fire());
        this.add(jbStart, SOUTH);
        this.pack();

        drone.addBatteryListener(b -> LOGGER.info("BatteryState: " + b));
        drone.addCriticalBatteryListener(b -> LOGGER.info("Critical-BatteryState: " + b));
        drone.addPCMDListener(b -> LOGGER.info("PCMD: " + b));
    }


    void fire() {

        startOperation.accept(textArea.getText());
    }


    void setText(String text) {

        this.textArea.setText(text);
    }


    public SwingBasedProgrammaticDriver withDynamicCompilation() {

        startOperation =
            text -> {
            long currentTimeMillis = System.currentTimeMillis();

            text = text.replaceAll("(?i)jumpingsumo\\.", "");

            String className = "mypackage.MyClass" + currentTimeMillis;
            String javaCode = String.format("package mypackage;\n"
                    + "import de.bischinger.parrot.Main;\n"
                    + "import java.io.IOException;\n"
                    + "public class MyClass" + currentTimeMillis + " implements Runnable {\n"
                    + "    public void run() {\n"
                    + "        try {\n"
                    +

                    // COOL STUFF coming

                    "           Main.SINGLETON.%s;\n"
                    + "        } catch (IOException e) {\n"
                    + "                e.printStackTrace();\n"
                    + "            }\n"
                    + "    }\n"
                    + "}\n", text);

            try {
                Class aClass = new CachedCompiler(null, null).loadFromJava(className, javaCode);
                Runnable runner = (Runnable) aClass.newInstance();
                runner.run();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        };

        return this;
    }
}
