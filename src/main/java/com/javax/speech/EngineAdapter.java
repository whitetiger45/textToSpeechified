package javax.speech;

/**
 * Trivial implementation of the EngineListener interface
 * that receives a EngineEvents.
 * The methods in this class are empty;  this class is provided as a
 * convenience for easily creating listeners by extending this class
 * and overriding only the methods of interest.
 * <p>
 * Extended by RecognizerAdapter and SynthesizerAdapter.
 *
 * @see javax.speech.recognition.RecognizerAdapter
 * @see javax.speech.synthesis.SynthesizerAdapter
 */
public class EngineAdapter implements EngineListener {

    /**
     * The Engine has been allocated.
     *
     * @see javax.speech.EngineEvent#ENGINE_ALLOCATED
     */
    @Override
    public void engineAllocated(EngineEvent e) {
    }

    /**
     * The Engine is being allocated.
     *
     * @see javax.speech.EngineEvent#ENGINE_ALLOCATING_RESOURCES
     */
    @Override
    public void engineAllocatingResources(EngineEvent e) {
    }

    /**
     * The Engine has been deallocated.
     *
     * @see javax.speech.EngineEvent#ENGINE_DEALLOCATED
     */
    @Override
    public void engineDeallocated(EngineEvent e) {
    }

    /**
     * The Engine is being deallocated.
     *
     * @see javax.speech.EngineEvent#ENGINE_DEALLOCATING_RESOURCES
     */
    @Override
    public void engineDeallocatingResources(EngineEvent e) {
    }

    /**
     * An EngineErrorEvent has occurred and the
     * Engine is unable to continue normal operation.
     */
    @Override
    public void engineError(EngineErrorEvent e) {
    }

    /**
     * The Engine has been paused.
     *
     * @see javax.speech.EngineEvent#ENGINE_PAUSED
     */
    @Override
    public void enginePaused(EngineEvent e) {
    }

    /**
     * The Engine has been resumed.
     *
     * @see javax.speech.EngineEvent#ENGINE_RESUMED
     */
    @Override
    public void engineResumed(EngineEvent e) {
    }
}
