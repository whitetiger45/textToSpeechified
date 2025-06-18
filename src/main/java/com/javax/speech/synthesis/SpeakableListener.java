package javax.speech.synthesis;

import java.util.EventListener;


/**
 * The listener interface for receiving notification of events
 * during spoken output of a Speakable.
 * Events are requested by either:
 * <p>
 * Providing a SpeakableListener object when calling
 * one of the speak or speakPlainText
 * methods of a Synthesizer.
 * Attaching a SpeakableListener to a Synthesizer
 * with its addSpeakableListener method.
 * <p>
 * The speakable events and the sequencing of events is defined
 * in the documentation for SpeakableEvent.  The
 * source of each SpeakableEvent is the object from
 * which JSML text was derived: a Speakable object,
 * a URL, or a String.
 * <p>
 * The SpeakableAdapter class provides a trivial implementation
 * of this interface.
 *
 * @see javax.speech.synthesis.Speakable
 * @see javax.speech.synthesis.SpeakableEvent
 * @see javax.speech.synthesis.Synthesizer
 * @see javax.speech.synthesis.Synthesizer#addSpeakableListener(javax.speech.synthesis.SpeakableListener)
 * @see javax.speech.synthesis.Synthesizer#speak(javax.speech.synthesis.Speakable, SpeakableListener)
 * @see javax.speech.synthesis.Synthesizer#speak(java.net.URL, SpeakableListener)
 * @see javax.speech.synthesis.Synthesizer#speak(java.lang.String, SpeakableListener)
 * @see javax.speech.synthesis.Synthesizer#speakPlainText(java.lang.String, SpeakableListener)
 */
public interface SpeakableListener extends EventListener {

    /**
     * A MARKER_REACHED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#MARKER_REACHED
     */
    void markerReached(SpeakableEvent e);

    /**
     * A SPEAKABLE_CANCELLED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_CANCELLED
     */
    void speakableCancelled(SpeakableEvent e);

    /**
     * A SPEAKABLE_ENDED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_ENDED
     */
    void speakableEnded(SpeakableEvent e);

    /**
     * A SPEAKABLE_PAUSED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_PAUSED
     */
    void speakablePaused(SpeakableEvent e);

    /**
     * A SPEAKABLE_RESUMED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_RESUMED
     */
    void speakableResumed(SpeakableEvent e);

    /**
     * A SPEAKABLE_STARTED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_STARTED
     */
    void speakableStarted(SpeakableEvent e);

    /**
     * A TOP_OF_QUEUE event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#TOP_OF_QUEUE
     */
    void topOfQueue(SpeakableEvent e);

    /**
     * A WORD_STARTED event has occurred.
     *
     * @see javax.speech.synthesis.SpeakableEvent#WORD_STARTED
     */
    void wordStarted(SpeakableEvent e);
}
