package cz.vabalcar.rcjbotcontroller;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 * This class allows to use given JTextArea as an OutputStream
 */
public class TextAreaOutputStream extends OutputStream {
    
    /** the inner jTextArea */
    private JTextArea jTextArea;
    
    /**
     * Instantiates a new TextAreaOutputStream.
     *
     * @param jTextArea the JTextArea to write to
     */
    public TextAreaOutputStream(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
        DefaultCaret caret = (DefaultCaret) this.jTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
    
    /* (non-Javadoc)
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(int b) throws IOException {
        jTextArea.append(String.valueOf((char) b));
    }
    
}
