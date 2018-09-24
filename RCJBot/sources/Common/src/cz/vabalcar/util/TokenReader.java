/*
 * 
 */
package cz.vabalcar.util;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class TokenReader.
 */
public class TokenReader implements AutoCloseable {
    
    /** The token builder. */
    private final StringBuilder tokenBuilder = new StringBuilder();
    
    /** The reader. */
    private final Reader reader;
    
    /** The delimiter code. */
    private final int delimiter;
    
    /** The mark code. */
    private final int mark;
    
    /** The skipping state. */
    private boolean skippingState = false;
    
    /**
     * Instantiates a new token reader.
     *
     * @param reader to read tokens from
     * @param delimiter the tokens delimiter
     * @param mark code of mark symbol - any delimiter between pair of marks is ignored. Marks aren't part of any read token. 
     */
    public TokenReader(Reader reader, int delimiter, int mark) {
        this.reader = reader;
        this.delimiter = delimiter;
        this.mark = mark;
    }
    
    /**
     * Read tokens.
     *
     * @return the list of read tokens
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public List<String> readTokens() throws IOException {
        List<String> tokens = new ArrayList<>();
        String token;
        while((token = readToken()) != null) {
            tokens.add(token);
        }
        return tokens;
    }
    
    /**
     * Read token.
     *
     * @return the read token
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String readToken() throws IOException {
        int readValue = Integer.MIN_VALUE;
        while((readValue = read()) == delimiter);
        while(readValue != -1 && (skippingState || readValue != delimiter)) {
            tokenBuilder.append((char)readValue);
            readValue = read();
        }
        String token = null;
        if (tokenBuilder.length() != 0) {
            token = tokenBuilder.toString();
            tokenBuilder.setLength(0);
        }
        return token;
    }
    
    /**
     * Read the code of next char in reader of -1 if end of reader has reached end of underlying stream.
     *
     * @return the code of read char
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public int read() throws IOException {
        int readValue = reader.read();
        if (readValue == mark) {
            skippingState = !skippingState;
            return read();
        } else {
            return readValue;
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.AutoCloseable#close()
     */
    @Override
    public void close() throws IOException {
        reader.close();
    }
}
