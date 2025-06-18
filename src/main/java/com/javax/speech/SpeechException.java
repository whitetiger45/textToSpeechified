package javax.speech;

/**
 * Signals that a Java Speech API exception has occurred.
 * SpeechException is the super class of all exceptions
 * in the javax.speech packages.
 * <p>
 * In addition to exceptions that inherit from SpeechException
 * some calls throw other Java Exceptions and Errors
 * such as IllegalArgumentException and SecurityException.
 */
public class SpeechException extends Exception {

    /**
     * Constructs a SpeechException with no detail message.
     */
    public SpeechException() {
    }

    /**
     * Constructs a SpeechException with the specified detail message.
     * A detail message is a String that describes this particular exception.
     *
     * @param s the detail message
     */
    public SpeechException(String s) {
        super(s);
    }
}
