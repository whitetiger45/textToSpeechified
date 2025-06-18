package javax.speech.synthesis;

import javax.speech.EngineAdapter;


/**
 * Adapter that receives events associated with a Synthesizer.
 * The methods in this class are empty;  this class is provided as a
 * convenience for easily creating listeners by extending this class
 * and overriding only the methods of interest.
 */
public class SynthesizerAdapter extends EngineAdapter implements SynthesizerListener {

    /**
     * The synthesizer text output queue has emptied.
     *
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_EMPTIED
     */
    @Override
    public void queueEmptied(SynthesizerEvent e) {
    }

    /**
     * The speaking queue has changed.
     *
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_UPDATED
     */
    @Override
    public void queueUpdated(SynthesizerEvent e) {
    }
}
