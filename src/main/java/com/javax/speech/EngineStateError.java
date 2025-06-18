package javax.speech;

/**
 * Signals an error caused by an illegal call to a method
 * of a speech engine.  For example, it is illegal to request
 * a deallocated Synthesizer to speak,
 * or to request a deallocated Recognizer to
 * create or new grammar, or to request any deallocated
 * engine to pause or resume.
 */
public class EngineStateError extends SpeechError {

    /**
     * Construct an EngineStateError with no detail message.
     */
    public EngineStateError() {
    }

    /**
     * Construct an EngineStateError with a detail message.
     * A detail message is a String that describes this particular exception.
     */
    public EngineStateError(String s) {
        super(s);
    }
}
