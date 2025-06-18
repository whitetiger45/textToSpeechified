package javax.speech;

/**
 * Provides a list of EngineModeDesc objects that define the
 * available operating modes of a speech engine.
 * <p>
 * Each speech engine registers an EngineCentral object with the
 * javax.speech.Central class.  When requested by the Central
 * class, each registered EngineCentral object provides a list
 * with an EngineModeDesc object that describes each available
 * operating mode of the engine.
 * <p>
 * The EngineModeDesc objects returned by EngineCentral
 * in its list must implement the EngineCreate interface and
 * be a sub-class of either RecognizerModeDesc or
 * SynthesizerModeDesc.  The Central
 * class calls the createEngine method of the EngineCentral
 * interface when it is requested to create an engine.  (See EngineCreate
 * for more detail.)
 * <p>
 * The engine must perform the same security checks on access to speech engines
 * as the Central class.
 * <p>
 * Note: Application developers do not need to use this interface.
 * EngineCentral is used internally by
 * Central and speech engines.
 *
 * @see javax.speech.Central
 * @see javax.speech.EngineCreate
 * @see javax.speech.EngineModeDesc
 */
public interface EngineCentral {

    /**
     * Create an EngineList containing an EngineModeDesc
     * for each mode of operation of a speech engine that matches a set
     * of required features.  Each object in the list must be a sub-class
     * of either RecognizerModeDesc or SynthesizerModeDesc
     * and must implement the EngineCreate interface.
     * <p>
     * The Central class ensures that the require
     * parameter is an instance of either RecognizerModeDesc
     * or SynthesizerModeDesc.  This enables the EngineCentral
     * to optimize its search for either recognizers or synthesizers.
     * <p>
     * Returns null if no engines are available or if none
     * meet the specified requirements.
     * <p>
     * The returned list should indicate the list of modes available at the time
     * of the call (the list may change over time).  The engine can create the
     * list at the time of the call or it may be pre-stored.
     *
     * @throws java.lang.SecurityException if the caller does not have accessEngineModeDesc permission
     * @see javax.speech.EngineCreate
     * @see javax.speech.EngineModeDesc
     * @see javax.speech.recognition.RecognizerModeDesc
     * @see javax.speech.synthesis.SynthesizerModeDesc
     */
    EngineList createEngineList(EngineModeDesc require) throws SecurityException;
}
