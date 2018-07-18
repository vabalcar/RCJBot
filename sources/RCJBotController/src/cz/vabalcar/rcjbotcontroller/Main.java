package cz.vabalcar.rcjbotcontroller;

import javax.swing.SwingUtilities;

import cz.vabalcar.rcjbotcontroller.windows.RCJBotControllerJFrame;

/**
 * The Main class. It's the entry point of program.
 */
public class Main {
    
    static {
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            ConfigurationManager.instance().load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method.
     *
     * @param args the arguments from command line
     */
    public static void main(String[] args) {
        openGUI();
    }
    
    /**
     * Opens GUI.
     */
    private static void openGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RCJBotControllerJFrame frame = new RCJBotControllerJFrame();
                frame.setVisible(true);
            }
        });
    }
}
