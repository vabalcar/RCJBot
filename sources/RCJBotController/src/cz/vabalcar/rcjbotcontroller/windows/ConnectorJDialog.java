package cz.vabalcar.rcjbotcontroller.windows;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cz.vabalcar.rcjbotcontroller.JBotConfiguration;

import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;

import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;

/**
 * This class provides dialog which can be used by user to connect to a JBot. If connection is successful, 
 * connection details are stored in configuration and the dialog will be filled with them when dialog will
 * be opened in the future.
 */
public class ConnectorJDialog extends JDialog {
    
    /** the constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** the array of interactive components */
    private final JComponent[] interactiveComponents;
    
    /** the JBot configuration manager */
    private final JBotConfiguration jBotConfiguration = new JBotConfiguration();
    
    /**
     * Instantiates a new ConnectorJDialog.
     *
     * @param parent the parent RCJBotControllerJFrame
     */
    public ConnectorJDialog(final RCJBotControllerJFrame parent) {
        super(parent);
        setModal(true);
        setTitle("Connect to JBot");
        setResizable(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(298, 140);
        setLocationRelativeTo(parent);
        final JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblEvName = new JLabel("Name");
        lblEvName.setBounds(10, 11, 35, 14);
        contentPane.add(lblEvName);
        
        final JTextField tfJBotName = new JTextField();
        tfJBotName.setBounds(55, 8, 219, 20);
        contentPane.add(tfJBotName);
        tfJBotName.setColumns(10);
        tfJBotName.setText(jBotConfiguration.getName());
        
        JLabel lblPort = new JLabel("Port");
        lblPort.setBounds(10, 36, 35, 14);
        contentPane.add(lblPort);
        
        final JTextField tfJBotPort = new JTextField();
        tfJBotPort.setBounds(55, 33, 219, 20);
        contentPane.add(tfJBotPort);
        tfJBotPort.setColumns(10);
        tfJBotPort.setText(jBotConfiguration.getPort());
        
        final JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                final SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        disableUserInteraction();
                        try {
                            String jBotName = tfJBotName.getText();
                            String jBotPort = tfJBotPort.getText();
                            
                            if (!jBotConfiguration.verify(jBotName, jBotPort)) {
                                return false;
                            }
                            
                            if (parent.getJBot().isConnected()) {
                                parent.getJBot().close();
                            }
                            
                            BrickInfo[] bricks = BrickFinder.find(jBotName);
                            
                            if (bricks.length == 0) {
                                return false;
                            }
                            
                            jBotConfiguration.setName(jBotName);
                            jBotConfiguration.setPort(jBotPort);
                            
                            System.out.println(bricks[0].getIPAddress() + " found");
                            
                            return parent.getJBot().connectTo(bricks[0].getIPAddress(), jBotConfiguration.getPortNumber());
                        } catch (Exception e) {
                            return false;
                        }
                    }
                };
                worker.addPropertyChangeListener(new PropertyChangeListener() {
                    
                    @Override
                    public void propertyChange(PropertyChangeEvent event) {
                        Object newValue = event.getNewValue();
                        if (newValue.equals(SwingWorker.StateValue.DONE)) {
                            boolean connectionSucessful;
                            try {
                                connectionSucessful = worker.get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                                enableUserInteraction();
                                return;
                            } 
                            if (connectionSucessful) {
                                dispose();
                                parent.getKeyActionsManager().enableHandlers();
                            } else {
                                enableUserInteraction();
                                JOptionPane.showMessageDialog(ConnectorJDialog.this, "Unable to connect to selected JBot", "Connection error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });
                worker.execute();
            }
        });
        btnConnect.setBounds(185, 64, 89, 23);
        
        interactiveComponents = new JComponent[]{ tfJBotName, tfJBotPort, btnConnect };
        
        contentPane.add(btnConnect);
    }
    
    /**
     * Disables JComponents from array interactiveComponent
     */
    private void disableUserInteraction() {
        setUserInteractionEnabled(false);
    }
    
    /**
     * Enables JComponents from array interactiveComponent.
     */
    private void enableUserInteraction() {
        setUserInteractionEnabled(true);
    }
    
    /**
     * Sets whether the user interaction is enabled.
     *
     * @param enabled the new user interaction enabled
     */
    private void setUserInteractionEnabled(boolean enabled) {
        for(JComponent interactiveComponent : interactiveComponents) {
            interactiveComponent.setEnabled(enabled);
        }
    }
}
