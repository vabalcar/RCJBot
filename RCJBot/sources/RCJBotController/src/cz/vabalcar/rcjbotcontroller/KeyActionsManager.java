package cz.vabalcar.rcjbotcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import cz.vabalcar.jbot.RemoteJBot;
import cz.vabalcar.jbot.moving.Direction;
import cz.vabalcar.jbot.moving.Locomotion;
import cz.vabalcar.jbot.moving.MotorMovement;
import cz.vabalcar.jbot.moving.Movement;
import cz.vabalcar.jbot.moving.Rotation;
import cz.vabalcar.jbot.moving.Stop;
import cz.vabalcar.util.FloatArray;

/**
 * This class manages KeyListeners used by this program to remotely control JBot.
 * It operates on RobotMovementActionRegister and provides a simple way of controls management.
 */
public class KeyActionsManager {
    
    /** the key of the configuration property containing name of key used to move robot forward.*/
    private static final String KEY_FORWARD = "key.forward";
    
    /** the key of the configuration property containing name of key used to move robot backward.*/
    private static final String KEY_BACKWARD = "key.backward";
    
    /** the key of the configuration property containing name of key used to rotate robot to the left.*/
    private static final String KEY_LEFT = "key.left";
    
    /** the key of the configuration property containing name of key used to rotate robot to the right.*/
    private static final String KEY_RIGHT = "key.right";
    
    /** the key of the configuration property containing name of key used to move robot's non-chassis motor 1 forward.*/
    private static final String KEY_M1_FORWARD = "key.m1.forward";
    
    /** the key of the configuration property containing name of key used to move robot's non-chassis motor 1 backward.*/
    private static final String KEY_M1_BACKWARD = "key.m1.backward";
    
    /** the key of the configuration property containing name of key used to move robot's non-chassis motor 2 forward.*/
    private static final String KEY_M2_FORWARD = "key.m2.forward";
    
    /** the key of the configuration property containing name of key used to move robot's non-chassis motor 2 backward.*/
    private static final String KEY_M2_BACKWARD = "key.m2.backward";
    
    /** the key of the configuration property containing name of key used to move robot's non-chassis motor 3 forward.*/
    private static final String KEY_M3_FORWARD = "key.m3.forward";
    
    /** the key of the configuration property containing name of key used to move robot's non-chassis motor 3 backward.*/
    private static final String KEY_M3_BACKWARD = "key.m3.backward";
    
    /** the key of the configuration property containing name of key used to move robot's non-chassis motor 4 forward.*/
    private static final String KEY_M4_FORWARD = "key.m4.forward";
    
    /** the key of the configuration property containing name of key used to move robot's non-chassis motor 4 backward.*/
    private static final String KEY_M4_BACKWARD = "key.m4.backward";
    
    /** the default value of the configuration property containing name of key used to move robot forward.*/
    private static final String KEY_FORWARD_DEFAULT = "UP";
    
    /** the default value of the configuration property containing name of key used to move robot backward.*/
    private static final String KEY_BACKWARD_DEFAULT = "DOWN";
    
    /** the default value of the configuration property containing name of key used to move robot left.*/
    private static final String KEY_LEFT_DEFAULT = "LEFT";
    
    /** the default value of the configuration property containing name of key used to move robot right.*/
    private static final String KEY_RIGHT_DEFAULT = "RIGHT";
    
    /** the default value of the configuration property containing name of key used to move robot's non-chassis motor 1 forward.*/
    private static final String KEY_M1_FORWARD_DEFAULT = "Q";
    
    /** the default value of the configuration property containing name of key used to move robot's non-chassis motor 1 backward.*/
    private static final String KEY_M1_BACKWARD_DEFAULT = "A";
    
    /** the default value of the configuration property containing name of key used to move robot's non-chassis motor 2 forward.*/
    private static final String KEY_M2_FORWARD_DEFAULT = "W";
    
    /** the default value of the configuration property containing name of key used to move robot's non-chassis motor 2 backward.*/
    private static final String KEY_M2_BACKWARD_DEFAULT = "S";
    
    /** the default value of the configuration property containing name of key used to move robot's non-chassis motor 3 forward.*/
    private static final String KEY_M3_FORWARD_DEFAULT = "E";
    
    /** the default value of the configuration property containing name of key used to move robot's non-chassis motor 3 backward.*/
    private static final String KEY_M3_BACKWARD_DEFAULT = "D";
    
    /** the default value of the configuration property containing name of key used to move robot's non-chassis motor 4 forward.*/
    private static final String KEY_M4_FORWARD_DEFAULT = "R";
    
    /** the default value of the configuration property containing name of key used to move robot's non-chassis motor 4 backward.*/
    private static final String KEY_M4_BACKWARD_DEFAULT = "F";
    
    /** max number of controllable of robot's non-chassis motors */
    private static final int MOTORS_MAX = 4;
    
    /** the RobotMovementActionRegister on which KeyActionsManager operates */
    private final RobotMovementActionRegister register;
    
    /** table of used keys and their types */
    private final Map<String, String> keyTypes = new HashMap<>();
        
    /**
     * Instantiates a new KeyActionsManager.
     *
     * @param eventSource JComponent to register all KeyListeners to it. All registered KeyListeners are enabled
     * iff eventSource has focus. Good choice is a logical root of tree of JComponents.
     * @param movementTarget JBot to send movement commands to
     */
    public KeyActionsManager(JComponent eventSource, RemoteJBot<FloatArray> movementTarget) {
        register = new RobotMovementActionRegister(eventSource, movementTarget);
        register(KEY_FORWARD, KEY_FORWARD_DEFAULT, new Locomotion(Direction.FORWARD));
        register(KEY_BACKWARD, KEY_BACKWARD_DEFAULT, new Locomotion(Direction.BACKWARD));
        register(KEY_LEFT, KEY_LEFT_DEFAULT, new Rotation(Direction.LEFT));
        register(KEY_RIGHT, KEY_RIGHT_DEFAULT, new Rotation(Direction.RIGHT));
        
        register(KEY_M1_FORWARD, KEY_M1_FORWARD_DEFAULT, 1, new Locomotion(Direction.FORWARD));
        register(KEY_M1_BACKWARD, KEY_M1_BACKWARD_DEFAULT, 1, new Locomotion(Direction.BACKWARD));
        
        register(KEY_M2_FORWARD, KEY_M2_FORWARD_DEFAULT, 2, new Locomotion(Direction.FORWARD));
        register(KEY_M2_BACKWARD, KEY_M2_BACKWARD_DEFAULT, 2, new Locomotion(Direction.BACKWARD));
        
        register(KEY_M3_FORWARD, KEY_M3_FORWARD_DEFAULT, 3, new Locomotion(Direction.FORWARD));
        register(KEY_M3_BACKWARD, KEY_M3_BACKWARD_DEFAULT, 3, new Locomotion(Direction.BACKWARD));
        
        register(KEY_M4_FORWARD, KEY_M4_FORWARD_DEFAULT, 4, new Locomotion(Direction.FORWARD));
        register(KEY_M4_BACKWARD, KEY_M4_BACKWARD_DEFAULT, 4, new Locomotion(Direction.BACKWARD));
    }
    
    /**
     * Registers a robot movement sending performed when key of given type is pressed.
     *
     * @param <M> type of movement to register its sending
     * @param keyType the type of action which is performed when key of type <code>keyType</code> is pressed  
     * @param defaultKeyName the default key of type <code>keyType</code>
     * @param movement the movement to register to be sent
     */
    private <M extends Movement> void register(String keyType, String defaultKeyName, M movement) {
        ConfigurationManager.instance().setDefault(keyType, defaultKeyName);
        String keyName = ConfigurationManager.instance().get(keyType);
        keyTypes.put(keyName, keyType);
        register.register(keyName, movement);
    }
    
    /**
     * Registers a non-chassis motor movement sending performed when key of given type is pressed.
     *
     * @param <M> type of movement to register its sending
     * @param keyType the type of action which is performed when key of type <code>keyType</code> is pressed  
     * @param defaultKeyName the default key of type <code>keyType</code>
     * @param target the target non-chassis motor to perform sent movement
     * @param movement the movement to register to be sent
     */
    private <M extends Movement> void register(String keyType, String defaultKeyName, int target, M movement) {
        ConfigurationManager.instance().setDefault(keyType, defaultKeyName);
        String keyName = ConfigurationManager.instance().get(keyType);
        keyTypes.put(keyName, keyType);
        register.register(keyName, new MotorMovement(target, movement), new MotorMovement(target, new Stop()));
    }
    
    /**
     * Enable handlers (all KeyListeners registered by the KeyActionsManager).
     */
    public void enableHandlers() {
        setHandlersEnabled(true);
    }
    
    /**
     * Disable handlers (all KeyListeners registered by the KeyActionsManager).
     */
    public void disableHandlers() {
        setHandlersEnabled(false);
    }
    
    /**
     * Sets if the handlers (all KeyListeners registered by the KeyActionsManager) are enabled.
     *
     * @param enabled the state of handlers to set
     */
    private void setHandlersEnabled(boolean enabled) {
        for(String usedKey : keyTypes.keySet()) {
            register.getListener(usedKey).setEnabled(enabled);
        }
    }
    
    /**
     * Gets the name of a key which pressing causes that robot moves forward.
     *
     * @return the name of a key which pressing causes that robot moves forward
     */
    public String getKeyForward() {
        return ConfigurationManager.instance().get(KEY_FORWARD);
    }
    
    /**
     * Gets the name of a key which pressing causes that robot moves backward.
     *
     * @return the name of a key which pressing causes that robot moves backward
     */
    public String getKeyBackward() {
        return ConfigurationManager.instance().get(KEY_BACKWARD);
    }
    
    /**
     * Gets the name of a key which pressing causes that robot moves left.
     *
     * @return the name of a key which pressing causes that robot moves left
     */
    public String getKeyLeft() {
        return ConfigurationManager.instance().get(KEY_LEFT);
    }

    /**
     * Gets the name of a key which pressing causes that robot moves right.
     *
     * @return the name of a key which pressing causes that robot moves right
     */
    public String getKeyRight() {
        return ConfigurationManager.instance().get(KEY_RIGHT);
    }
    
    /**
     * Gets the name of a key which pressing causes that robot's non-chassis of given number moves in given direction.
     *
     * @param motorNumber the number of motor
     * @param direction the direction of motor's movement
     * @return the name of a key which pressing causes that robot's non-chassis of given number moves in given direction
     */
    public String getMotorKey(int motorNumber, Direction direction) {        
        return ConfigurationManager.instance().get(getKeyType(motorNumber, direction));
    }
    
    /**
     * Sets the key which pressing will cause that robot will move forward.
     *
     * @param keyName the name the key which pressing will cause that robot moves forward
     * @return <code>true</code>, if setting is successful, <code>false</code> otherwise
     */
    public boolean setKeyForward(String keyName) {
        return setKey(KEY_FORWARD, keyName);
    }
    
    /**
     * Sets the key which pressing will cause that robot will move backward.
     *
     * @param keyName the name the key which pressing will cause that robot will move backward
     * @return <code>true</code>, if setting is successful, <code>false</code> otherwise
     */
    public boolean setKeyBackward(String keyName) {
        return setKey(KEY_BACKWARD, keyName);
    }
    
    /**
     * Sets the key which pressing will cause that robot will rotate left.
     *
     * @param keyName the name the key which pressing will cause that robot will rotate to the left
     * @return <code>true</code>, if setting is successful, <code>false</code> otherwise
     */
    public boolean setKeyLeft(String keyName) {
        return setKey(KEY_LEFT, keyName);
    }

    /**
     * Sets the key which pressing will cause that robot will rotate right.
     *
     * @param keyName the name the key which pressing will cause that robot will rotate to the right
     * @return <code>true</code>, if setting is successful, <code>false</code> otherwise
     */
    public boolean setKeyRight(String keyName) {
        return setKey(KEY_RIGHT, keyName);
    }
    
    /**
     * Sets the key which pressing will cause that robot's non-chassis motor with given number will move 
     * in given direction.
     *
     * @param motorNumber the number of motor
     * @param direction the direction
     * @param keyName the name of key which pressing will cause that robot's non-chassis motor with given number will move 
     * in given direction
     * @return <code>true</code>, if setting is successful, <code>false</code> otherwise
     */
    public boolean setMotorKey(int motorNumber, Direction direction, String keyName) {
        return setKey(getKeyType(motorNumber, direction), keyName);
    }
    
    /**
     * Sets the key to the type of key.
     *
     * @param keyType the name of type of key
     * @param keyName the name of key to set to given type of key
     * @return <code>true</code>, if setting is successful, <code>false</code> otherwise
     */
    private boolean setKey(String keyType, String keyName) {
        String prevKeyName = ConfigurationManager.instance().get(keyType);
        if (prevKeyName.equals(keyName)) {
            return true;
        }
        if (verify(keyName)) {
            boolean swaped = register.moveListener(prevKeyName, keyName);
            if (swaped) {
                String prevKeyType = keyTypes.get(keyName);
                ConfigurationManager.instance().set(prevKeyType, prevKeyName);
                keyTypes.put(prevKeyName, prevKeyType);
            } else {
                keyTypes.remove(prevKeyName);
            }
            keyTypes.put(keyName, keyType);
            ConfigurationManager.instance().set(keyType, keyName);
            return true;
        }
        return false;
    }
    
    /**
     * Verifies the the existence of given name of key.
     *
     * @param keyName the name of key to verify its existence
     * @return <code>true</code>, if key with given name exists, <code>false</code> otherwise
     */
    private boolean verify(String keyName) {
        return KeyStroke.getKeyStroke(keyName) != null;
    }
    
    /**
     * Gets the name of type of key which causes robot's non-chassis motor with given number move in given direction.
     *
     * @param motorNumber the number of motor
     * @param direction the direction
     * @return the name of type of key which causes robot's non-chassis motor with given number move in given direction
     */
    private String getKeyType(int motorNumber, Direction direction) {
        if (motorNumber < 1 || motorNumber > MOTORS_MAX 
                || direction == Direction.LEFT || direction == Direction.RIGHT) {
            throw new IllegalArgumentException();
        }
        return new StringBuilder().append("key.m").append(motorNumber).append('.').append(direction).toString();
    }
}
