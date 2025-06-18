package javax.speech;

/**
 * EngineEvent notifies changes in state of a
 * speech synthesis or recognition engine.  EngineEvents
 * are issued to each EngineListener attached to an engine.
 * The RecognizerEvent and SynthesizerEvent
 * classes both extend EngineEvent to provide specific
 * events for recognizers and synthesizers.
 */
public class EngineEvent extends SpeechEvent {

    /**
     * Identifier for event issued when engine allocation is complete.
     * The ALLOCATED flag of the newEngineState is set.
     *
     * @see javax.speech.EngineEvent#getNewEngineState()
     * @see javax.speech.SpeechEvent#getId()
     * @see javax.speech.Engine#allocate()
     * @see javax.speech.EngineListener#engineAllocated(javax.speech.EngineEvent)
     */
    public static final int ENGINE_ALLOCATED = 501;

    /**
     * Identifier for event issued when engine deallocation is complete.
     * The DEALLOCATED flag of the newEngineState is set.
     *
     * @see javax.speech.EngineEvent#getNewEngineState()
     * @see javax.speech.SpeechEvent#getId()
     * @see javax.speech.Engine#allocate()
     * @see javax.speech.EngineListener#engineDeallocated(javax.speech.EngineEvent)
     */
    public static final int ENGINE_DEALLOCATED = 502;

    /**
     * Identifier for event issued when engine allocation has commenced.
     * The ALLOCATING_RESOURCES flag of the newEngineState is set.
     *
     * @see javax.speech.EngineEvent#getNewEngineState()
     * @see javax.speech.SpeechEvent#getId()
     * @see javax.speech.Engine#allocate()
     * @see javax.speech.EngineListener#engineAllocatingResources(javax.speech.EngineEvent)
     */
    public static final int ENGINE_ALLOCATING_RESOURCES = 503;

    /**
     * Identifier for event issued when engine deallocation has commenced.
     * The DEALLOCATING_RESOURCES flag of the newEngineState is set.
     *
     * @see javax.speech.EngineEvent#getNewEngineState()
     * @see javax.speech.SpeechEvent#getId()
     * @see javax.speech.Engine#allocate()
     * @see javax.speech.EngineListener#engineDeallocatingResources(javax.speech.EngineEvent)
     */
    public static final int ENGINE_DEALLOCATING_RESOURCES = 504;

    /**
     * Identifier for event issued when engine is paused.
     * The PAUSED flag of the newEngineState is set.
     *
     * @see javax.speech.EngineEvent#getNewEngineState()
     * @see javax.speech.SpeechEvent#getId()
     * @see javax.speech.Engine#pause()
     * @see javax.speech.EngineListener#enginePaused(javax.speech.EngineEvent)
     */
    public static final int ENGINE_PAUSED = 505;

    /**
     * Identifier for event issued when engine is resumed.
     * The RESUMED flag of the newEngineState is set.
     *
     * @see javax.speech.EngineEvent#getNewEngineState()
     * @see javax.speech.SpeechEvent#getId()
     * @see javax.speech.Engine#resume()
     * @see javax.speech.EngineListener#engineResumed(javax.speech.EngineEvent)
     */
    public static final int ENGINE_RESUMED = 506;

    /**
     * Engine state following this event.
     *
     * @see javax.speech.EngineEvent#getNewEngineState()
     */
    protected long newEngineState;

    /**
     * Engine state following prior to this event.
     *
     * @see javax.speech.EngineEvent#getOldEngineState()
     */
    protected long oldEngineState;

    /**
     * Constructs an EngineEvent with an event identifier, old
     * engine state and new engine state.
     *
     * @param source         the object that issued the event
     * @param id             the identifier for the event type
     * @param oldEngineState engine state prior to this event
     * @param newEngineState engine state following this event
     * @see javax.speech.Engine#getEngineState()
     */
    public EngineEvent(Engine source, int id, long oldEngineState, long newEngineState) {
        super(source, id);
        this.oldEngineState = oldEngineState;
        this.newEngineState = newEngineState;
    }

    /**
     * Return the state following this EngineEvent.
     * The value matches the getEngineState method.
     *
     * @see javax.speech.Engine#getEngineState()
     */
    public long getNewEngineState() {
        return this.newEngineState;
    }

    /**
     * Return the state prior to this EngineEvent.
     * The value matches the getEngineState method.
     *
     * @see javax.speech.Engine#getEngineState()
     */
    public long getOldEngineState() {
        return this.oldEngineState;
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
        case 501:
            return "ENGINE_ALLOCATED";
        case 502:
            return "ENGINE_DEALLOCATED";
        case 503:
            return "ENGINE_ALLOCATING_RESOURCES";
        case 504:
            return "ENGINE_DEALLOCATING_RESOURCES";
        case 505:
            return "ENGINE_PAUSED";
        case 506:
            return "ENGINE_RESUMED";
        default:
            return super.paramString();
        }
    }
}
