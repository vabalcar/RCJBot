package cz.vabalcar.rcjbotcontroller;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import cz.vabalcar.rcjbotcontroller.KeyListener.KeyPressAction;
import cz.vabalcar.rcjbotcontroller.KeyListener.KeyReleaseAction;

/**
 * This class provides a simpler-than-default way to manage listeners of keyboard events. 
 */
public class KeyActionsRegister {
    
    /** JComponent to register all KeyListeners to it */
    private final JComponent eventSource;
    
    /** a boolean to store whether KeyActionsRegister is clear */
    private boolean clear = true;
    
    /**
     * Instantiates a new KeyActionsRegister.
     *
     * @param eventSource JComponent to register all KeyListeners to it. All registered KeyListeners are enabled
     * iff eventSource has focus. Good choice is a logical root of tree of JComponents.
     */
    public KeyActionsRegister(JComponent eventSource) {
        this.eventSource = eventSource;
    }
    
    /**
     * Checks if is clear.
     *
     * @return <code>true</code>, if the KeyActionsRegister is clear, <code>false</code> otherwise
     */
    public boolean isClear() {
        return clear;
    }
    
    /**
     * Registers the KeyListener constructed from pair of AbstractActions 
     * which listens events of key with given name.
     *
     * @param keyName the name of a key to register it the given KeyListener
     * @param onPressAction the onPressAction defining the KeyListener
     * @param onReleaseAction the onReleaseAction defining the KeyListener
     */
    public void register(String keyName, AbstractAction onPressAction, AbstractAction onReleaseAction) {
        register(keyName, new KeyListener(onPressAction, onReleaseAction));
    }
    
    /**
     * Registers the KeyListener which listens events of key with given name.
     *
     * @param keyName the name of a key to register it the given KeyListener
     * @param listener KeyListener to register
     */
    public void register(String keyName, KeyListener listener) {
        clear = false;
        String onPressActionName = getOnPressActionName(keyName);
        String onReleaseActionName = getOnReleaseActionName(keyName);
        eventSource.getInputMap().put(getOnPressKeyStroke(keyName), onPressActionName);
        eventSource.getInputMap().put(getOnReleaseKeyStroke(keyName), onReleaseActionName);
        eventSource.getActionMap().put(onPressActionName, listener.getOnPressAction());
        eventSource.getActionMap().put(onReleaseActionName, listener.getOnReleaseAction());
    }
    
    /**
     * Unregisters the KeyListener which listens events of key with given name.
     *
     * @param keyName the name of a key to unregister its listener
     */
    public void unregister(String keyName) {
        eventSource.getInputMap().remove(getOnPressKeyStroke(keyName));
        eventSource.getInputMap().remove(getOnReleaseKeyStroke(keyName));
        eventSource.getActionMap().remove(getOnPressActionName(keyName));
        eventSource.getActionMap().remove(getOnReleaseActionName(keyName));
    }
    
    /**
     * Gets the KeyListener which listens events of key with given name.
     *
     * @param keyName the name of a key to obtain this key's listener  
     * @return KeyListener which listens events of key with given name 
     * or null if such listener doesn't exist.
     */
    public KeyListener getListener(String keyName) {
        Action onPressAction = eventSource.getActionMap().get(getOnPressActionName(keyName));
        Action onReleaseAction = eventSource.getActionMap().get(getOnReleaseActionName(keyName));
        if (onPressAction == null || onReleaseAction == null) {
            return null;
        } else if (onPressAction instanceof KeyPressAction && onReleaseAction instanceof KeyReleaseAction) {
            return ((KeyPressAction)onPressAction).getParent();
        } else {
            return null;
        }
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
        boolean swap = false;
        if (fromKey.equals(toKey)) {
            return swap;
        }
        KeyListener movedListener = getListener(fromKey);
        if (movedListener == null) {
            return swap;
        }
        unregister(fromKey);
        KeyListener replacedListener = getListener(toKey);
        if (replacedListener != null) {
            unregister(toKey);
            register(fromKey, replacedListener);
            swap = true;
        }
        register(toKey, movedListener);
        return swap;
    }
    
    /**
     * Gets the instance of press KeyStroke.
     *
     * @param keyName the name of key which release KeyStroke will be obtained 
     * @return the KeyStroke instance represents the releasing of key with name keyName
     */
    private KeyStroke getOnPressKeyStroke(String keyName) {
        return KeyStroke.getKeyStroke(keyName);
    }
    
    /**
     * Gets the instance of release KeyStroke.
     *
     * @param keyName the name of key which release KeyStroke will be obtained 
     * @return the KeyStroke instance represents the releasing of key with name keyName
     */
    private KeyStroke getOnReleaseKeyStroke(String keyName) {
        return KeyStroke.getKeyStroke("released " + keyName);
    }
    
    /**
     * Gets the onPressAction's name.
     *
     * @param keyName the name of a key to determine the onReleaseAction's name from 
     * @return the onReleaseAction's name determined from given keyName
     */
    private String getOnPressActionName(String keyName) {
        return keyName + " pressed";
    }
    
    /**
     * Gets the onReleaseAction's name.
     *
     * @param keyName the name of a key to determine the onReleaseAction's name from 
     * @return the onReleaseAction's name determined from given keyName
     */
    private String getOnReleaseActionName(String keyName) {
        return keyName + " released";
    }
    
    /**
     * Clears all KeyListners from KeyActionsRegister (and therefore from JComponent on which KeyListener works).
     */
    public void clear() {
        if (!clear) {
            eventSource.getInputMap().clear();
            eventSource.getActionMap().clear();
            clear = true;
        }
    }
}
