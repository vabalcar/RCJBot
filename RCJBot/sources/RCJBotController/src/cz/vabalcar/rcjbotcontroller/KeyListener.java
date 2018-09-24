package cz.vabalcar.rcjbotcontroller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * This class is a pair of AbstractActions which provide a complex handling of keyboard events.
 */
public class KeyListener {
    
    /**
     * This class is an implementation of the AbstractAction and it's a handler for events when a key is pressed.
     * It's a wrapper around an AbstractAction that filters out repeating user input events raised repeatedly 
     * when a key is pressed and hold, so the wrapped AbstractAction is performed only once.  
     */
    public class KeyPressAction extends AbstractAction {
        
        /** the constant serialVersionUID */
        private static final long serialVersionUID = 1L;
        
        /** the parent KeyListener's reference */
        private final KeyListener parent;
        
        /** the inner action */
        private final AbstractAction innerAction;
        
        /**
         * Instantiates a new key press action.
         *
         * @param parent the parent
         * @param action the action
         */
        public KeyPressAction(KeyListener parent, AbstractAction action) {
            this.parent = parent;
            innerAction = action;
        }
        
        /**
         * Gets the reference to the parent KeyListener.
         *
         * @return the reference to the parent KeyListener
         */
        public KeyListener getParent() {
            return parent;
        }
        
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            if (!keyPressed)  {
                keyPressed = true;
                if (handlersEnabled) {
                    innerAction.actionPerformed(event);
                }
            }
        }
    }
    
    /**
     * This class is an implementation of the AbstractAction and it's a handler for events when a key is released.
     */
    public class KeyReleaseAction extends AbstractAction {
        
        /** the constant serialVersionUID */
        private static final long serialVersionUID = 1L;
        
        /** the parent KeyListener's reference */
        private final KeyListener parent;
        
        /** the inner action */
        private final AbstractAction innerAction;
        
        /**
         * Instantiates a new KeyReleaseAction.
         *
         * @param parent the parent KeyListener of newly instantiated KeyReleaseAction.
         * @param action the action which has to be performed when the key is released
         */
        public KeyReleaseAction(KeyListener parent, AbstractAction action) {
            this.parent = parent;
            innerAction = action;
        }
        
        /**
         * Gets the reference to the parent KeyListener.
         *
         * @return the reference to the parent KeyListener
         */
        public KeyListener getParent() {
            return parent;
        }
        
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            if (keyPressed)  {
                keyPressed = false;
                if (handlersEnabled) {
                    innerAction.actionPerformed(event);
                }
            }
        }
    }
    
    /** the onKeyPress event handler */
    private final AbstractAction onPressAction;
    
    /** the onKeyRelease event handler */
    private final AbstractAction onReleaseAction;
    
    /** a boolean to store whether the key is pressed. */
    private boolean keyPressed = false;
    
    /** a boolean to store whether handlers are enabled. */
    private boolean handlersEnabled = false;
    
    /**
     * Instantiates a new key listener.
     *
     * @param onPressAction a onKeyPress event handler
     * @param onReleaseAction a onKeyRelease event handler
     */
    public KeyListener(AbstractAction onPressAction, AbstractAction onReleaseAction) {
        this.onPressAction = new KeyPressAction(this, onPressAction);
        this.onReleaseAction = new KeyReleaseAction(this, onReleaseAction);
    }
    
    /**
     * Gets the onKeyPress event handler.
     *
     * @return the onKeyPress event handler
     */
    public AbstractAction getOnPressAction() {
        return onPressAction;
    }
    
    /**
     * Gets the onKeyRelease event handler.
     *
     * @return the onKeyRelease event handler
     */
    public AbstractAction getOnReleaseAction() {
        return onReleaseAction;
    }
    
    /**
     * Checks if the KeyListener is enabled.
     *
     * @return <code>true</code>, if the KeyListener is enabled, <code>false</code> otherwise
     */
    public boolean isEnabled() {
        return handlersEnabled;
    }
    
    /**
     * Sets whether the KeyListener is enabled or not.
     *
     * @param enabled <code>true</code>, if the KeyListener is about to be be enabled, <code>false</code> otherwise
     */
    public void setEnabled(boolean enabled) {
        handlersEnabled = enabled;
    }
}
