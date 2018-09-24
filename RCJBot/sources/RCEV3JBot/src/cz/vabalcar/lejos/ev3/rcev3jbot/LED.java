package cz.vabalcar.lejos.ev3.rcev3jbot;

import lejos.hardware.Button;

/**
 * The Class LED.
 */
public class LED {
    
    /** The Constant OFF. */
    private static final int OFF = 0;
    
    /** The Constant STATIC_GREEN. */
    private static final int STATIC_GREEN = 1;
    
    /** The Constant STATIC_RED. */
    private static final int STATIC_RED = 2;
    
    /** The Constant STATIC_YELLOW. */
    private static final int STATIC_YELLOW = 3;
    
    /** The Constant BLINKING_GREEN. */
    private static final int BLINKING_GREEN = 4;
    
    /** The Constant BLINKING_RED. */
    private static final int BLINKING_RED = 5;
    
    /** The Constant BLINKING_YELLOW. */
    private static final int BLINKING_YELLOW = 6;
    
    /** The Constant FAST_BLINKING_GREEN. */
    private static final int FAST_BLINKING_GREEN = 7;
    
    /** The Constant FAST_BLINKING_RED. */
    private static final int FAST_BLINKING_RED = 8;
    
    /** The Constant FAST_BLINKING_YELLOW. */
    private static final int FAST_BLINKING_YELLOW = 9;

    /**
     * Turn off.
     */
    public static void turnOff() {
        Button.LEDPattern(OFF);
    }

    /**
     * Static green.
     */
    public static void staticGreen() {
        Button.LEDPattern(STATIC_GREEN);
    }

    /**
     * Static red.
     */
    public static void staticRed() {
        Button.LEDPattern(STATIC_RED);
    }

    /**
     * Static yellow.
     */
    public static void staticYellow() {
        Button.LEDPattern(STATIC_YELLOW);
    }

    /**
     * Blinking green.
     */
    public static void blinkingGreen() {
        Button.LEDPattern(BLINKING_GREEN);
    }

    /**
     * Blinking red.
     */
    public static void blinkingRed() {
        Button.LEDPattern(BLINKING_RED);
    }

    /**
     * Blinking yellow.
     */
    public static void blinkingYellow() {
        Button.LEDPattern(BLINKING_YELLOW);
    }

    /**
     * Fast blinking green.
     */
    public static void fastBlinkingGreen() {
        Button.LEDPattern(FAST_BLINKING_GREEN);
    }

    /**
     * Fast blinking red.
     */
    public static void fastBlinkingRed() {
        Button.LEDPattern(FAST_BLINKING_RED);
    }

    /**
     * Fast blinking yellow.
     */
    public static void fastBlinkingYellow() {
        Button.LEDPattern(FAST_BLINKING_YELLOW);
    }
}
