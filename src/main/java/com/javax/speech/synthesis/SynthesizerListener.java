package javax.speech.synthesis;

import javax.speech.EngineListener;


/**
 * An extension to the EngineListener interface for receiving
 * notification of events associated with a Synthesizer.
 * SynthesizerListener objects are attached to and removed
 * from a Synthesizer by calling the addEngineListener
 * and removeEngineListener methods (which Synthesizer
 * inherits from the Engine interface).
 * <p>
 * The source for all SynthesizerEvents provided to a
 * SynthesizerListener is the Synthesizer.
 * <p>
 * The SynthesizerAdapter class provides a trivial implementation
 * of this interface.
 *
 * @see javax.speech.synthesis.SynthesizerListener
 * @see javax.speech.synthesis.Synthesizer
 * @see javax.speech.Engine
 * @see javax.speech.Engine#addEngineListener(javax.speech.EngineListener)
 * @see javax.speech.Engine#removeEngineListener(javax.speech.EngineListener)
 */
public interface SynthesizerListener extends EngineListener {

    /**
     * An QUEUE_EMPTIED event has occurred indicating that
     * the text output queue of the Synthesizer has emptied.
     * The Synthesizer is in the QUEUE_EMPTY state.
     *
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_EMPTIED
     * @see javax.speech.synthesis.Synthesizer#QUEUE_EMPTY
     */
    void queueEmptied(SynthesizerEvent e);

    /**
     * An QUEUE_UPDATED event has occurred indicating that
     * the speaking queue has changed.
     * The Synthesizer is in the QUEUE_NOT_EMPTY state.
     *
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_UPDATED
     * @see javax.speech.synthesis.Synthesizer#QUEUE_NOT_EMPTY
     */
    void queueUpdated(SynthesizerEvent e);
}
