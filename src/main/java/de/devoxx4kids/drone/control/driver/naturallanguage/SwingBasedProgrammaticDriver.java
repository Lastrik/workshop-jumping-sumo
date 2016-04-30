package de.devoxx4kids.drone.control.driver.naturallanguage;

import de.devoxx4kids.drone.control.DroneController;

import de.devoxx4kids.dronecontroller.network.DroneConnection;

import net.openhft.compiler.CachedCompiler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import java.lang.invoke.MethodHandles;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;

import static java.lang.String.format;

import static java.util.stream.Stream.of;


public class SwingBasedProgrammaticDriver extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected static DroneController DRONE_CONTROLLER;

    private final DroneController drone;
    private Consumer<String> startOperation;
    private JTextArea textArea;

    public SwingBasedProgrammaticDriver(DroneConnection droneConnection) {

        drone = new DroneController(droneConnection);
        DRONE_CONTROLLER = drone;

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

        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("Jumping Sumo");
        JMenuItem jMenuItem = new JMenuItem("Neu Verbinden");
        jMenuItem.addActionListener(e -> drone.connect());
        jMenu.add(jMenuItem);
        jMenu.add(new JPopupMenu.Separator());

        JMenuItem exitItem = new JMenuItem("Beenden");
        exitItem.addActionListener(e -> System.exit(0));
        jMenu.add(exitItem);
        jMenuBar.add(jMenu);
        this.setJMenuBar(jMenuBar);

        this.pack();

        drone.addBatteryListener(b -> LOGGER.info("BatteryState: " + b));
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
            code -> {
            long currentTimeMillis = System.currentTimeMillis();

            code = code.replaceAll("(?i)jumpingsumo\\.", "");

            String javaCode = format("package de.devoxx4kids.drone.control.driver.naturallanguage;\n"
                    + "public class SwingRunner%s implements Runnable {\n"
                    + "  public void run() {\n"
                    + "    SwingBasedProgrammaticDriver.DRONE_CONTROLLER.%s;\n"
                    + "  }\n"
                    + "}\n", currentTimeMillis, code);

            String className = format("de.devoxx4kids.drone.control.driver.naturallanguage.SwingRunner%s",
                    currentTimeMillis);

            try {
                Class aClass = new CachedCompiler(null, null).loadFromJava(className, javaCode);
                Runnable runner = (Runnable) aClass.newInstance();
                runner.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        return this;
    }
}
