package javax.speech;

/**
 * Problem encountered connecting audio to/from a speech engine.
 */
public class AudioException extends SpeechException {

    /**
     * Constructs a AudioException with no detail message.
     */
    public AudioException() {
    }

    /**
     * Construct an AudioException with the specified detail message.
     * The string describes this particular exception.
     *
     * @param s the detail message
     */
    public AudioException(String s) {
        super(s);
    }
}
