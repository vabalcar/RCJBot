package cz.vabalcar.rcjbotcontroller.windows;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import cz.vabalcar.jbot.moving.Direction;
import cz.vabalcar.rcjbotcontroller.KeyActionsManager;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * This class provides dialog which can be used by user to configure control of JBot. 
 * All changes can be applied immediately (without need of restart of program... etc.) 
 * and are stored in program's configuration file when RCJBotControllerJFrame is closed. 
 */
public class KeyConfigurationJDialog extends JDialog {

    /** the constant serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    /** the empty Border */
    private static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder();
    
    /** the Border of selected JTextFiled */
    private static final Border SELECTED_BORDER = BorderFactory.createLineBorder(Color.BLACK, 2);
    
    /** the Border of JTextFiled which contains an error */
    private static final Border ERROR_BORDER = BorderFactory.createLineBorder(Color.RED, 2);
    
    /** the List of input detectors */
    private final List<JTextField> inputDetectors = new ArrayList<>();
    
    /** the KeyActionsManager which provides configuration of controls */
    private final KeyActionsManager keyActionsManager;
    
    /** a boolean which stores information whether an error among input detectors occurred */
    private boolean errorDetected = false;

    /**
     * Instantiates a new KeyConfigurationJDialog.
     *
     * @param parent the parent RCJBotControllerJFrame
     */
    public KeyConfigurationJDialog(RCJBotControllerJFrame parent) {
        super(parent);
        
        keyActionsManager = parent.getKeyActionsManager();
        
        setTitle("Configuration");
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(426, 313);
        setLocationRelativeTo(parent);
        
        final JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        final JLabel lblChassis = new JLabel("Chassis");
        lblChassis.setHorizontalAlignment(SwingConstants.CENTER);
        lblChassis.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblChassis.setBounds(10, 11, 156, 25);
        contentPane.add(lblChassis);
        
        final JLabel lblKey = new JLabel("Key");
        lblKey.setHorizontalAlignment(SwingConstants.CENTER);
        lblKey.setBounds(84, 44, 82, 14);
        contentPane.add(lblKey);
        
        final JLabel lblForward = new JLabel("Forward");
        lblForward.setHorizontalAlignment(SwingConstants.RIGHT);
        lblForward.setBounds(10, 75, 62, 16);
        contentPane.add(lblForward);
        
        final JTextField tfForward = new JTextField();
        tfForward.setBounds(84, 70, 82, 26);
        tfForward.setBorder(EMPTY_BORDER);
        tfForward.setHorizontalAlignment(SwingConstants.CENTER);
        tfForward.setText(keyActionsManager.getKeyForward());
        toInputDetector(tfForward);
        contentPane.add(tfForward);        
        
        final JLabel lblBackward = new JLabel("Backward");
        lblBackward.setHorizontalAlignment(SwingConstants.RIGHT);
        lblBackward.setBounds(10, 115, 62, 16);
        contentPane.add(lblBackward);
        
        final JTextField tfBackward = new JTextField();
        tfBackward.setBounds(84, 110, 82, 26);
        tfBackward.setBorder(EMPTY_BORDER);
        tfBackward.setHorizontalAlignment(SwingConstants.CENTER);
        tfBackward.setText(keyActionsManager.getKeyBackward());
        toInputDetector(tfBackward);
        contentPane.add(tfBackward);
        
        final JLabel lblLeft = new JLabel("Left");
        lblLeft.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLeft.setBounds(10, 154, 62, 16);
        contentPane.add(lblLeft);
        
        final JTextField tfLeft = new JTextField();
        tfLeft.setBounds(84, 149, 82, 26);
        tfLeft.setBorder(EMPTY_BORDER);
        tfLeft.setHorizontalAlignment(SwingConstants.CENTER);
        tfLeft.setText(keyActionsManager.getKeyLeft());
        toInputDetector(tfLeft);
        contentPane.add(tfLeft);
        
        final JLabel lblRight = new JLabel("Right");
        lblRight.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRight.setBounds(10, 192, 62, 16);
        contentPane.add(lblRight);
        
        final JTextField tfRight = new JTextField();
        tfRight.setBounds(84, 187, 82, 26);
        tfRight.setBorder(EMPTY_BORDER);
        tfRight.setHorizontalAlignment(SwingConstants.CENTER);
        tfRight.setText(keyActionsManager.getKeyRight());
        toInputDetector(tfRight);
        contentPane.add(tfRight);
        
        final JLabel lblOtherMotors = new JLabel("Other motors");
        lblOtherMotors.setHorizontalAlignment(SwingConstants.CENTER);
        lblOtherMotors.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblOtherMotors.setBounds(226, 11, 174, 25);
        contentPane.add(lblOtherMotors);
        
        final JLabel lblForwardUp = new JLabel("Forward");
        lblForwardUp.setHorizontalAlignment(SwingConstants.CENTER);
        lblForwardUp.setBounds(226, 43, 82, 16);
        contentPane.add(lblForwardUp);
        
        final JLabel lblBackwardUp = new JLabel("Backward");
        lblBackwardUp.setHorizontalAlignment(SwingConstants.CENTER);
        lblBackwardUp.setBounds(318, 43, 82, 16);
        contentPane.add(lblBackwardUp);
        
        final JLabel lblM1 = new JLabel("M1");
        lblM1.setHorizontalAlignment(SwingConstants.CENTER);
        lblM1.setBounds(196, 75, 25, 16);
        contentPane.add(lblM1);
        
        final JTextField tfM1Forward = new JTextField();
        tfM1Forward.setBounds(226, 70, 82, 26);
        tfM1Forward.setText((String) null);
        tfM1Forward.setHorizontalAlignment(SwingConstants.CENTER);
        tfM1Forward.setBorder(EMPTY_BORDER);
        tfM1Forward.setText(keyActionsManager.getMotorKey(1, Direction.FORWARD));
        toInputDetector(tfM1Forward);
        contentPane.add(tfM1Forward);
        
        final JTextField tfM1Backward = new JTextField();
        tfM1Backward.setBounds(318, 70, 82, 26);
        tfM1Backward.setText((String) null);
        tfM1Backward.setHorizontalAlignment(SwingConstants.CENTER);
        tfM1Backward.setBorder(EMPTY_BORDER);
        tfM1Backward.setText(keyActionsManager.getMotorKey(1, Direction.BACKWARD));
        toInputDetector(tfM1Backward);
        contentPane.add(tfM1Backward);
        
        final JLabel lblM2 = new JLabel("M2");
        lblM2.setHorizontalAlignment(SwingConstants.CENTER);
        lblM2.setBounds(196, 115, 25, 16);
        contentPane.add(lblM2);
        
        final JTextField tfM2Forward = new JTextField();
        tfM2Forward.setBounds(226, 109, 82, 26);
        tfM2Forward.setText((String) null);
        tfM2Forward.setHorizontalAlignment(SwingConstants.CENTER);
        tfM2Forward.setBorder(EMPTY_BORDER);
        tfM2Forward.setText(keyActionsManager.getMotorKey(2, Direction.FORWARD));
        toInputDetector(tfM2Forward);
        contentPane.add(tfM2Forward);
        
        final JTextField tfM2Backward = new JTextField();
        tfM2Backward.setBounds(318, 109, 82, 26);
        tfM2Backward.setText((String) null);
        tfM2Backward.setHorizontalAlignment(SwingConstants.CENTER);
        tfM2Backward.setBorder(EMPTY_BORDER);
        tfM2Backward.setText(keyActionsManager.getMotorKey(2, Direction.BACKWARD));
        toInputDetector(tfM2Backward);
        contentPane.add(tfM2Backward);
        
        final JLabel lblM3 = new JLabel("M3");
        lblM3.setHorizontalAlignment(SwingConstants.CENTER);
        lblM3.setBounds(196, 154, 25, 16);
        contentPane.add(lblM3);
        
        final JTextField tfM3Forward = new JTextField();
        tfM3Forward.setBounds(226, 148, 82, 26);
        tfM3Forward.setText((String) null);
        tfM3Forward.setHorizontalAlignment(SwingConstants.CENTER);
        tfM3Forward.setBorder(EMPTY_BORDER);
        tfM3Forward.setText(keyActionsManager.getMotorKey(3, Direction.FORWARD));
        toInputDetector(tfM3Forward);
        contentPane.add(tfM3Forward);
        
        final JTextField tfM3Backward = new JTextField();
        tfM3Backward.setBounds(318, 148, 82, 26);
        tfM3Backward.setText((String) null);
        tfM3Backward.setHorizontalAlignment(SwingConstants.CENTER);
        tfM3Backward.setBorder(EMPTY_BORDER);
        tfM3Backward.setText(keyActionsManager.getMotorKey(3, Direction.BACKWARD));
        toInputDetector(tfM3Backward);
        contentPane.add(tfM3Backward);
        
        final JLabel lblM4 = new JLabel("M4");
        lblM4.setHorizontalAlignment(SwingConstants.CENTER);
        lblM4.setBounds(196, 192, 25, 16);
        contentPane.add(lblM4);
        
        final JTextField tfM4Forward = new JTextField();
        tfM4Forward.setBounds(226, 187, 82, 26);
        tfM4Forward.setText((String) null);
        tfM4Forward.setHorizontalAlignment(SwingConstants.CENTER);
        tfM4Forward.setBorder(EMPTY_BORDER);
        tfM4Forward.setText(keyActionsManager.getMotorKey(4, Direction.FORWARD));
        toInputDetector(tfM4Forward);
        contentPane.add(tfM4Forward);
        
        final JTextField tfM4Backward = new JTextField();
        tfM4Backward.setBounds(318, 187, 82, 26);
        tfM4Backward.setText((String) null);
        tfM4Backward.setHorizontalAlignment(SwingConstants.CENTER);
        tfM4Backward.setBorder(EMPTY_BORDER);
        tfM4Backward.setText(keyActionsManager.getMotorKey(4, Direction.BACKWARD));
        toInputDetector(tfM4Backward);
        contentPane.add(tfM4Backward);
        
        final JButton btnApply = new JButton("Apply");
        btnApply.setBounds(252, 236, 82, 25);
        btnApply.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!errorDetected) {
                    applyConfiguration();
                }
            }
        });
        contentPane.add(btnApply);
        
        final JButton btnOk = new JButton("OK");
        btnOk.setBounds(346, 236, 54, 25);
        btnOk.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent event) {
                if (!errorDetected) {
                    applyConfiguration();
                    dispose();
                }
            }
        });
        contentPane.add(btnOk);
        
        checkInputDetectors();
    }
    
    /**
     * Sets a JTextField to behave as input detector, a component which writes a name of pressed key when has
     * the focus.
     *
     * @param textField the JTextField to convert into the input detector
     */
    private void toInputDetector(final JTextField textField) {
        textField.addKeyListener(new KeyListener() {
            
            @Override
            public void keyTyped(KeyEvent e) {
                e.consume();
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                if (textField.isFocusOwner()) {
                    textField.setText(KeyEvent.getKeyText(e.getKeyCode()).toUpperCase());
                    checkInputDetectors();
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                e.consume();
            }
        });
        textField.addFocusListener(new FocusListener() {
            
            @Override
            public void focusLost(FocusEvent e) {
                checkInputDetectors();
                if (!textField.getBorder().equals(ERROR_BORDER)) {
                    textField.setBorder(EMPTY_BORDER);
                }
            }
            
            @Override
            public void focusGained(FocusEvent e) {
                textField.getCaret().setVisible(false);
                textField.setBorder(SELECTED_BORDER);
            }
        });
        
        inputDetectors.add(textField);
    }
    
    /**
     * Check input detectors for errors. An error occurs when two detectors contains same value.
     */
    private void checkInputDetectors() {
        String[] detectedKeyNames = new String[inputDetectors.size()];
        for(int i = 0; i < inputDetectors.size(); i++) {
            detectedKeyNames[i] = inputDetectors.get(i).getText();
        }
        
        boolean[] errors = getEquivalences(detectedKeyNames);
        
        errorDetected = false;
        for(int i = 0; i < inputDetectors.size(); i++) {
            if (errors[i]) {
                inputDetectors.get(i).setBorder(ERROR_BORDER);
                if (!errorDetected) {
                    errorDetected = true;
                }
            } else if (inputDetectors.get(i).getBorder().equals(ERROR_BORDER)) {
                if (inputDetectors.get(i).hasFocus()) {
                    inputDetectors.get(i).setBorder(SELECTED_BORDER);
                } else {
                    inputDetectors.get(i).setBorder(EMPTY_BORDER);
                }
            }
        }
    }
    
    /**
     * Gets the equivalences among the string in the given array. This function is O((k*n)^2) 
     * where n is length of input array and k is length of the longest String in it. So its very slow,
     * but used only for super-small input.
     *
     * @param strings the array of strings to check for equivalences
     * @return the boolean array b of equivalences where holds that ith element is true,
     * iff in input array s exists j such that s[i].equals(s[j]) return <code>true</code>.
     */
    private boolean[] getEquivalences(String[] strings) {
        if (strings == null || strings.length == 0) {
            return new boolean[0];
        }
        boolean[] equivalences = new boolean[strings.length];
        for (int i = 0; i < strings.length; i++) {
            for(int j = i + 1; j < strings.length; j++) {
                if (strings[i].equals(strings[j])) {
                    equivalences[i] = true;
                    equivalences[j] = true;
                }
            }
        }
        return equivalences;
    }
    
    /**
     * Applies control configuration.
     */
    private void applyConfiguration() {
        keyActionsManager.setKeyForward(inputDetectors.get(0).getText());
        keyActionsManager.setKeyBackward(inputDetectors.get(1).getText());
        keyActionsManager.setKeyLeft(inputDetectors.get(2).getText());
        keyActionsManager.setKeyRight(inputDetectors.get(3).getText());
        
        for(int i = 4; i < inputDetectors.size(); i++) {
            keyActionsManager.setMotorKey((i - 2) / 2, i % 2 == 0 ? Direction.FORWARD : Direction.BACKWARD, inputDetectors.get(i).getText());
        }
    }
}
