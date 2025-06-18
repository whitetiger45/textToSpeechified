package javax.speech;

/**
 * The EngineCreate interface is implemented by
 * EngineModeDesc objects obtained through calls to
 * the EngineCentral objects of each
 * speech engine registered with the Central class.
 * <p>
 * Note: most applications do not need to use this interface.
 * <p>
 * Each engine implementation must sub-class either
 * RecognizerModeDesc or SynthesizerModeDesc
 * to implement the EngineCreate interface.
 * For example:
 * <pre>
 * public MyRecognizerModeDesc extends RecognizerModeDesc implements EngineCreate
 * ...
 *   public Engine createEngine() {
 *   // Use mode desc properties to create an appropriate engine
 *   }
 * </pre>
 * This implementation mechanism allows the engine to embed additional
 * mode information (engine-specific mode identifiers, GUIDs etc.)
 * that simplify creation of the engine if requested by the Central class.
 * The engine-specific mode descriptor may need to override equals and
 * other methods if engine-specific features are defined.
 * <p>
 * The engine must perform the same security checks on access to
 * speech engines as the Central class.
 *
 * @see javax.speech.Central
 * @see javax.speech.EngineCentral
 * @see javax.speech.EngineModeDesc
 * @see javax.speech.recognition.RecognizerModeDesc
 * @see javax.speech.synthesis.SynthesizerModeDesc
 */
public interface EngineCreate {

    /**
     * Create an engine with the properties specified by this object.
     * A new engine should be created in the DEALLOCATED state.
     *
     * @throws java.lang.IllegalArgumentException The properties of the EngineModeDesc do not refer to a known
     *                                            engine or engine mode.
     * @throws javax.speech.EngineException       The engine defined by this EngineModeDesc could not be properly created.
     * @throws java.lang.SecurityException        if the caller does not have createRecognizer permission but
     *                                            is attempting to create a Recognizer
     * @see javax.speech.Engine#DEALLOCATED
     */
    Engine createEngine() throws IllegalArgumentException, EngineException, SecurityException;
}
