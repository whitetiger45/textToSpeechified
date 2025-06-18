package javax.speech;

/**
 * EngineErrorEvent is an asynchronous notification
 * of an internal error in the engine which prevents normal
 * behavior of that engine.  The event encapsulates a Throwable
 * object that provides details about the error.
 */
public class EngineErrorEvent extends EngineEvent {

    /**
     * Identifier for event issued when engine error occurs.
     *
     * @see javax.speech.EngineListener#engineError(javax.speech.EngineErrorEvent)
     */
    public static final int ENGINE_ERROR = 550;

    /**
     * Throwable object (Exception or
     * Error) that describes the detected engine problem.
     *
     * @see javax.speech.EngineErrorEvent#getEngineError()
     */
    protected Throwable problem;

    /**
     * Constructs an EngineErrorEvent with an event identifier,
     * throwable, old engine state and new engine state.  The old and
     * new states are zero if the engine states are unknown or undefined.
     *
     * @param source         the object that issued the event
     * @param id             the identifier for the event type
     * @param throwable      description of the detected error
     * @param oldEngineState engine state prior to this event
     * @param newEngineState engine state following this event
     * @see javax.speech.Engine#getEngineState()
     */
    public EngineErrorEvent(Engine source, int id, Throwable throwable, long oldEngineState, long newEngineState) {
        super(source, id, oldEngineState, newEngineState);
    }

    /**
     * Return the Throwable object
     * (Exception or Error)
     * that describes the engine problem.
     */
    public Throwable getEngineError() {
        return this.problem;
    }

    /**
     * Returns a parameter string identifying this  event.
     * This method is useful for event-logging and for debugging.
     *
     * @return a string identifying the event
     */
    @Override
    public String paramString() {
        switch (super.id) {
        case 550:
            return "ENGINE_ERROR: " + this.problem.getMessage();
        default:
            return super.paramString();
        }
    }
}
