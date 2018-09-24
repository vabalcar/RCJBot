package cz.vabalcar.rcjbotcontroller.windows;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Callable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cz.vabalcar.jbot.events.RewritingDataListener;
import cz.vabalcar.rcjbotcontroller.ConfigurationManager;
import cz.vabalcar.rcjbotcontroller.KeyActionsManager;
import cz.vabalcar.rcjbotcontroller.SwingSensorDataVisualizer;
import cz.vabalcar.rcjbotcontroller.TextAreaOutputStream;
import cz.vabalcar.rcjbotcontroller.visualizer.processing.VisualizingSensorDataListener;
import cz.vabalcar.rcjbotcontroller.visualizer.processing.ev3.EV3SensorDataProcessor;
import cz.vabalcar.jbot.RemoteJBot;
import cz.vabalcar.util.FloatArray;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * This class provides the main window of the program. It contains visualizer of (supported) received data
 * and text area which shows all received data from connected RemoteJBot's sensors. It has a menu which allows
 * user open dialog windows to manage configuration of control and connection to RemoteJBot.
 * When opened for the first time, ConnectorJDialog is opened automatically and connection can be established.
 */
public class RCJBotControllerJFrame extends JFrame {
    
    /** the constant serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** the remote JBot */
    private final RemoteJBot<FloatArray> jBot = new RemoteJBot<>(FloatArray.class);
    
    /** the KeyActionsManager which controls enabling and disabling of registered KeyListeners */
    private final KeyActionsManager keyActionsManager;

    /**
     * Instantiates a new RCJBotControllerJFrame.
     */
    public RCJBotControllerJFrame() {
        setTitle("RCJBotController");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(914, 652);
        setLocationRelativeTo(null);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnJbot = new JMenu("Jbot");
        menuBar.add(mnJbot);
        
        JMenuItem mntmConnect = new JMenuItem("Connect");
        mntmConnect.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent event) {
                connectToJBot();
            }
        });
        mnJbot.add(mntmConnect);
        
        JMenu mnPreferences = new JMenu("Preferences");
        menuBar.add(mnPreferences);
        
        JMenuItem mntmControlsConfiguration = new JMenuItem("Controls configuration");
        mntmControlsConfiguration.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent event) {
                configureControls();
            }
        });
        mnPreferences.add(mntmControlsConfiguration);
        
        final JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.addFocusListener(new FocusListener() {
            
            @Override
            public void focusLost(FocusEvent event) {
                contentPane.requestFocusInWindow();
            }
            
            @Override
            public void focusGained(FocusEvent event) {
                // Nothing to do here.
            }
        });
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent event) {
                connectToJBot();
            }
            
            @Override
            public void windowClosing(WindowEvent event) {
                try {
                    ConfigurationManager.instance().store();
                    jBot.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        keyActionsManager = new KeyActionsManager(contentPane, jBot);
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(0, 0, 440, 583);
        contentPane.add(scrollPane);
        
        SwingSensorDataVisualizer sensorDataVisualizer = new SwingSensorDataVisualizer();
        sensorDataVisualizer.setBounds(441, 0, 416, 583);
        contentPane.add(sensorDataVisualizer);
        
        jBot.addInterruptionListener(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                JOptionPane.showMessageDialog(RCJBotControllerJFrame.this, "Connection terminated", "Connection error", JOptionPane.ERROR_MESSAGE);
                keyActionsManager.disableHandlers();
                jBot.close();
                return null;
            }
        });
        
        jBot.addListener(new RewritingDataListener<>(FloatArray.class, new TextAreaOutputStream(textArea)));
        jBot.addListener(new VisualizingSensorDataListener(new EV3SensorDataProcessor(sensorDataVisualizer.getVisualizer(), true)));
    }

    /**
     * Gets the KeyActionsManager.
     *
     * @return used instance of KeyActionsManager
     */
    public KeyActionsManager getKeyActionsManager() {
        return keyActionsManager;
    }
    
    /**
     * Gets the RemoteJBot.
     *
     * @return the used instance of RemoteJBot
     */
    public RemoteJBot<FloatArray> getJBot() {
        return jBot;
    }
    
    /**
     * Opens a ConnectorJDialog.
     */
    private void connectToJBot() {
        new ConnectorJDialog(this).setVisible(true);
    }
    
    /**
     * Opens a KeyConfigurationJDialog.
     */
    private void configureControls() {
        new KeyConfigurationJDialog(this).setVisible(true);
    }
}
