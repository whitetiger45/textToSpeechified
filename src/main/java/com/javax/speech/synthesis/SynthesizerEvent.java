package javax.speech.synthesis;

import javax.speech.EngineEvent;


/**
 * Event issued by Synthesizer to indicate a change in
 * state or other activity.  A SynthesizerEvent is
 * issued to each SynthesizerListener attached to a
 * Synthesizer using the addEngineListener
 * method it inherits from the Engine interface.
 * <p>
 * The SynthesizerEvent class extends the
 * EngineEvent class.  Similarly, the
 * SynthesizerListener interface extends the
 * EngineListener interface.
 * <p>
 * SynthesizerEvent extends EngineEvent
 * with several events that are specialized for speech synthesis.
 * It also inherits several event types from EngineEvent:
 * ENGINE_ALLOCATED,
 * ENGINE_DEALLOCATED,
 * ENGINE_ALLOCATING_RESOURCES,
 * ENGINE_DEALLOCATING_RESOURCES,
 * ENGINE_PAUSED,
 * ENGINE_RESUMED.
 */
public class SynthesizerEvent extends EngineEvent {

    /**
     * The speaking queue of the Synthesizer has emptied
     * and the Synthesizer has changed to the
     * QUEUE_EMPTY state.
     * The queue may become empty because speech output of all
     * items in the queue is completed, or because the items
     * have been cancelled.
     * <p>
     * The QUEUE_EMPTIED event follows the SPEAKABLE_ENDED
     * or SPEAKABLE_CANCELLED event that removed the last item
     * from the speaking queue.
     *
     * @see javax.speech.synthesis.SynthesizerListener#queueEmptied(javax.speech.synthesis.SynthesizerEvent)
     * @see javax.speech.synthesis.Synthesizer#cancel()
     * @see javax.speech.synthesis.Synthesizer#cancel(java.lang.Object)
     * @see javax.speech.synthesis.Synthesizer#cancelAll()
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_ENDED
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_CANCELLED
     * @see javax.speech.synthesis.Synthesizer#QUEUE_EMPTY
     */
    public static final int QUEUE_EMPTIED = 700;

    /**
     * The speech output queue has changed.  This event may indicate
     * a change in state of the Synthesizer from
     * QUEUE_EMPTY to QUEUE_NOT_EMPTY.
     * The event may also occur in the QUEUE_NOT_EMPTY
     * state without changing state.
     * the enumerateQueue method of Synthesizer
     * will return a changed list.
     * <p>
     * The speech output queue changes when (a) a new item is placed
     * on the queue with a call to one of the speak methods,
     * (b) when an item is removed from the queue with one of the
     * cancel methods (without emptying the queue), or (c) when output
     * of the top item of the queue is completed (again, without leaving
     * an empty queue).
     * <p>
     * The topOfQueueChanged boolean parameter is set
     * to true if the top item on the queue has changed.
     *
     * @see javax.speech.synthesis.SynthesizerListener#queueUpdated(javax.speech.synthesis.SynthesizerEvent)
     * @see javax.speech.synthesis.SynthesizerEvent#getTopOfQueueChanged()
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_ENDED
     * @see javax.speech.synthesis.Synthesizer#QUEUE_NOT_EMPTY
     */
    public static final int QUEUE_UPDATED = 701;

    /**
     * topOfQueueChanged is true for
     * QUEUE_UPDATED event when the top item in the
     * speech output queue has changed.
     *
     * @see javax.speech.synthesis.SynthesizerEvent#getTopOfQueueChanged()
     */
    protected boolean topOfQueueChanged;

    /**
     * Construct a SynthesizerEvent with a specified event id
     * and topOfQueueChanged flag.
     *
     * @param source            the Synthesizer that issued the event
     * @param id                the identifier for the event type
     * @param topOfQueueChanged true if top item on speech output queue changed
     * @param oldEngineState    engine state prior to this event
     * @param newEngineState    engine state following this event
     */
    public SynthesizerEvent(Synthesizer source, int id, boolean topOfQueueChanged, long oldEngineState, long newEngineState) {
        super(source, id, oldEngineState, newEngineState);
        this.topOfQueueChanged = topOfQueueChanged;
    }

    /**
     * Return the topOfQueueChanged value.  The value
     * is true for a QUEUE_UPDATED event
     * when the top item in the speech output queue has changed.
     *
     * @see javax.speech.synthesis.SynthesizerEvent#topOfQueueChanged
     */
    public boolean getTopOfQueueChanged() {
        return this.topOfQueueChanged;
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
        case 700:
            return "QUEUE_EMPTIED";
        case 701:
            if (this.topOfQueueChanged) {
                return "QUEUE_UPDATED (topOfQueue changed)";
            }

            return "QUEUE_UPDATED";
        default:
            return super.paramString();
        }
    }
}
