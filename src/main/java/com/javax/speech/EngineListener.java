package javax.speech;

import java.util.EventListener;


/**
 * Interface defining methods to be called when state-change events
 * for a speech engine occur.  To receive engine events an
 * application attaches a listener by calling the
 * addEngineListener method of an Engine.
 * A listener is removed by a call to the
 * removeEngineListener method.
 * <p>
 * The event dispatch policy is defined in the documentation
 * for the SpeechEvent class.
 * <p>
 * This interface is extended by the RecognizerListener
 * and SynthesizerListener interfaces to handle the
 * specialized events of speech recognizers and synthesizers.
 * <p>
 * A trivial implementation of EngineListener is
 * provided by the EngineAdapter class.
 * RecognizerAdapter and SynthesizerAdapter
 * classes are also available.
 *
 * @see javax.speech.SpeechEvent
 * @see javax.speech.EngineAdapter
 * @see javax.speech.recognition.RecognizerListener
 * @see javax.speech.recognition.RecognizerAdapter
 * @see javax.speech.synthesis.SynthesizerListener
 * @see javax.speech.synthesis.SynthesizerAdapter
 */
public interface EngineListener extends EventListener {

    /**
     * The Engine has been allocated.
     *
     * @see javax.speech.EngineEvent#ENGINE_ALLOCATED
     */
    void engineAllocated(EngineEvent e);

    /**
     * The Engine is being allocated.
     *
     * @see javax.speech.EngineEvent#ENGINE_ALLOCATING_RESOURCES
     */
    void engineAllocatingResources(EngineEvent e);

    /**
     * The Engine has been deallocated.
     *
     * @see javax.speech.EngineEvent#ENGINE_DEALLOCATED
     */
    void engineDeallocated(EngineEvent e);

    /**
     * The Engine is being deallocated.
     *
     * @see javax.speech.EngineEvent#ENGINE_DEALLOCATING_RESOURCES
     */
    void engineDeallocatingResources(EngineEvent e);

    /**
     * An EngineErrorEvent has occurred and the
     * Engine is unable to continue normal operation.
     *
     * @see javax.speech.EngineErrorEvent
     */
    void engineError(EngineErrorEvent e);

    /**
     * The Engine has been paused.
     *
     * @see javax.speech.EngineEvent#ENGINE_PAUSED
     */
    void enginePaused(EngineEvent e);

    /**
     * The Engine has been resumed.
     *
     * @see javax.speech.EngineEvent#ENGINE_RESUMED
     */
    void engineResumed(EngineEvent e);
}
