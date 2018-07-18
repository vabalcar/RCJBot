package cz.vabalcar.rcjbotcontroller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import cz.vabalcar.jbot.RemoteJBot;
import cz.vabalcar.jbot.moving.Movement;
import cz.vabalcar.jbot.moving.Stop;
import cz.vabalcar.jbot.moving.UnsupportedMovementProcessorActionException;
import cz.vabalcar.util.FloatArray;

/**
 * This class provides simplified KeyActionsRegister (it works over it actually) to provide simple way to register
 * KeyListeners related to RemoteJBot's control.
 */
public class RobotMovementActionRegister {
    
    /**
     * This class is implementation of AbstractAction which sends given movements to the target RemoteJBot
     *
     * @param <M> the type of movement stored in the RobotMovementAction
     */
    private class RobotMovementAction<M extends Movement> extends AbstractAction {
        
        /** the constant serialVersionUID */
        private static final long serialVersionUID = 1L;
        
        /** the target RemoteJBot */
        private final RemoteJBot<FloatArray> remoteJBot;
        
        /** the movement to send to the target RemoteJBot when actionPerformed is called */
        private final M movement;
        
        /**
         * Instantiates a new robot movement action.
         *
         * @param remotejBot the target RemoteJBot
         * @param movement the movement to send to the target RemoteJBot when actionPerformed is called
         */
        public RobotMovementAction(RemoteJBot<FloatArray> remotejBot, M movement) {
            this.remoteJBot = remotejBot;
            this.movement = movement;
        }
        
        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                if (remoteJBot.isConnected()) {
                    remoteJBot.getMovementProcessor().process(movement);
                }
            } catch (UnsupportedMovementProcessorActionException e) {
                e.printStackTrace();
            }
            System.out.println(movement + " sent");
        }
    }
    
    /** the KeyActionsRegister */
    private final KeyActionsRegister keyActionsRegister;
    
    /** the target RemoteJBot */
    private final RemoteJBot<FloatArray> movementTarget;
    
    /** the simple robot stop action */
    private final AbstractAction stopAction;

    /**
     * Instantiates a new RobotMovementActionRegister.
     *
     * @param eventSource JComponent to register all KeyListeners to it. All registered KeyListeners are enabled
     * iff eventSource has focus. Good choice is a logical root of tree of JComponents.
     * @param movementTarget JBot to send movement commands to
     */
    public RobotMovementActionRegister(JComponent eventSource, RemoteJBot<FloatArray> movementTarget) {
        keyActionsRegister = new KeyActionsRegister(eventSource);
        this.movementTarget = movementTarget;
        stopAction = new RobotMovementAction<>(this.movementTarget, new Stop());
    }
    
    /**
     * Registers a robot movement sending performed when key with given name is pressed.
     *
     * @param <M> type of movement to register its sending
     * @param keyName the name of the key to register sending of the given movement to 
     * @param movement the movement to register to be sent
     */
    public <M extends Movement> void register(String keyName, M movement) {
        keyActionsRegister.register(keyName, new RobotMovementAction<>(movementTarget, movement), stopAction);
    }
    
    /**
     * Registers a robot non-stopping movement sending performed when a key of given name is pressed
     * and robot stopping movement sending performed when the key is later released.
     *
     * @param <M> type of non-stopping movement to register its sending
     * @param <S> type of stopping movement to register its sending
     * @param keyName the name of the key to register sending of the given movement to
     * @param movement the non-stopping movement to register to be sent
     * @param stop the stopping movement to register to be sent
     */
    public <M extends Movement, S extends Movement> void register(String keyName, M movement, S stop) {
        keyActionsRegister.register(keyName, 
                new RobotMovementAction<>(movementTarget, movement),
                new RobotMovementAction<>(movementTarget, stop));
    }
    
    /**
     * Unregisters the KeyListener which listens events of key with given name.
     *
     * @param keyName the name of a key to unregister its listener
     */
    public void unregister(String keyName) {
        keyActionsRegister.unregister(keyName);
    }
    
    /**
     * Gets the KeyListener which listens events of key with given name.
     *
     * @param keyName the name of a key to obtain this key's listener  
     * @return KeyListener which listens events of key with given name 
     * or null if such listener doesn't exist.
     */
    public KeyListener getListener(String keyName) {
        return keyActionsRegister.getListener(keyName);
    }
    
    /**
     * If exists, move KeyListener from a key to another. If target key already has a KeyListener, these two
     * KeyListeners are swapped.
     *
     * @param fromKey the name of a key to move KeyListener from
     * @param toKey the name of a key to move moving KeyListener to
     * @return true, if swap happened
     */
    public boolean moveListener(String fromKey, String toKey) {
        return keyActionsRegister.moveListener(fromKey, toKey);
    }
}
