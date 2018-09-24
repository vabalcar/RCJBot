/*
 * 
 */
package cz.vabalcar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * The Class Util.
 */
public final class Util {
    
    /** The Constant DEFAULT_MARK. */
    public static final char DEFAULT_MARK = '"';
    
    /**
     * Split given string.
     *
     * @param s the string to split
     * @param delimiter the delimiter of tokens
     * @return the string[] tokens get by splitting string by delimiter
     */
    public static String[] split(String s, char delimiter) {
        return split(s, delimiter, DEFAULT_MARK);
    }
    
    /**
     * Split given string.
     *
     * @param s the string to split
     * @param delimiter the delimiter of tokens
     * @param mark the mark value
     * @return the string[] tokens get by splitting string by delimiter
     */
    public static String[] split(String s, char delimiter, char mark) {
        return split(s, (int)delimiter, (int)mark);
    }
    
    /**
     * Split given string.
     *
     * @param s the string to split
     * @param delimiter the value of delimiter of tokens
     * @param mark the mark value
     * @return the string[] of tokens get by splitting string by delimiter
     */
    public static String[] split(String s, int delimiter, int mark) {
        return split(new StringReader(s), delimiter, mark).toArray(new String[0]);
    }
    
    /**
     * Split given string.
     *
     * @param reader to read input from
     * @param delimiter the value of delimiter of tokens
     * @param mark the mark value
     * @return the list of tokens get by splitting string by delimiter
     */
    public static List<String> split(StringReader reader, int delimiter, int mark) {      
        try(TokenReader tokenReader = new TokenReader(reader, delimiter, mark)) {
            return tokenReader.readTokens();
        } catch (Exception e) {
            return null;
        }
    }
	
	/**
	 * Peek from BufferedReader.
	 *
	 * @param reader the reader to peek from
	 * @return the code of peeked char
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int peek(BufferedReader reader) throws IOException {
        reader.mark(1);
        int readValue = reader.read();
        reader.reset();
        return readValue;
    }
}
