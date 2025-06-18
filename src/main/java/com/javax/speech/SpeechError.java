package javax.speech;

/**
 * Signals that an error has occurred in the javax.speech package.
 * SpeechError is the super class of all errors in
 * the Java Speech API.
 */
public class SpeechError extends Error {

    /**
     * Empty constructor for SpeechException with no detail message.
     */
    public SpeechError() {
    }

    /**
     * Constructs a SpeechException with a detail message.
     *
     * @param s the detail message
     */
    public SpeechError(String s) {
        super(s);
    }
}
