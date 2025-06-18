package javax.speech.synthesis;

/**
 * Adapter that receives events associated with spoken output of a
 * Speakable object.  The methods in this class are empty;
 * this class is provided as a convenience for easily creating listeners
 * by extending this class and overriding only the methods of interest.
 */
public class SpeakableAdapter implements SpeakableListener {

    /**
     * A MARKER_REACHED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#MARKER_REACHED
     */
    @Override
    public void markerReached(SpeakableEvent e) {
    }

    /**
     * A SPEAKABLE_CANCELLED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_CANCELLED
     */
    @Override
    public void speakableCancelled(SpeakableEvent e) {
    }

    /**
     * A SPEAKABLE_ENDED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_ENDED
     */
    @Override
    public void speakableEnded(SpeakableEvent e) {
    }

    /**
     * A SPEAKABLE_PAUSED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_PAUSED
     */
    @Override
    public void speakablePaused(SpeakableEvent e) {
    }

    /**
     * A SPEAKABLE_RESUMED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_RESUMED
     */
    @Override
    public void speakableResumed(SpeakableEvent e) {
    }

    /**
     * A SPEAKABLE_STARTED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_STARTED
     */
    @Override
    public void speakableStarted(SpeakableEvent e) {
    }

    /**
     * A TOP_OF_QUEUE event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#TOP_OF_QUEUE
     */
    @Override
    public void topOfQueue(SpeakableEvent e) {
    }

    /**
     * A WORD_STARTED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#WORD_STARTED
     */
    @Override
    public void wordStarted(SpeakableEvent e) {
    }
}
