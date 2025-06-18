package javax.speech.synthesis;

import javax.speech.SpeechException;


/**
 * Thrown if a syntax problem is found with text in the marked up with the
 * <p>
 * .
 * <p>
 * The exception message is a printable string.
 */
public class JSMLException extends SpeechException {

    /**
     * Constructs a JSMLException with no detail message.
     */
    public JSMLException() {
    }

    /**
     * Constructs a JSMLException with the specified detail message.
     *
     * @param s a printable detail message
     */
    public JSMLException(String s) {
        super(s);
    }
}
