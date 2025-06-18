package javax.speech;

/**
 * Signals that an error occurred while trying to create or access
 * a speech synthesis engine, speech recognition engine or
 * EngineCentral object.
 */
public class EngineException extends SpeechException {

    /**
     * Construct an EngineException with no detail message.
     */
    public EngineException() {
    }

    /**
     * Construct an EngineException with a detail message.
     * A detail message is a String that describes this particular exception.
     */
    public EngineException(String s) {
        super(s);
    }
}
