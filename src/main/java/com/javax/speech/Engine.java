package javax.speech;

/**
 * The Engine interface is the parent interface for all
 * speech engines including Recognizer and
 * Synthesizer.  A speech engine is a generic entity
 * that either processes speech input or produces speech output.
 * Engines - recognizers and synthesizers - derive the following
 * functionality from the Engine interface:
 * <p>
 * allocate and deallocate methods.
 * pause and resume methods.
 * Access to a AudioManager and VocabManager.
 * Access to EngineProperties.
 * Access to the engine's EngineModeDesc.
 * Methods to add and remove EngineListener objects.
 * <p>
 * Engines are located, selected and created through methods of the
 * Central class.
 * <p>
 * Engine State System: Allocation
 * <p>
 * Each type of speech engine has a well-defined set of states
 * of operation, and well-defined behavior for moving between
 * states.  These states are defined by constants of the
 * Engine, Recognizer and
 * Synthesizer interfaces.
 * <p>
 * The Engine interface defines three
 * methods for viewing and monitoring states: getEngineState,
 * waitEngineState and testEngineState.
 * An EngineEvent is issued to EngineListeners
 * each time an Engine changes state.
 * <p>
 * The basic states of any speech engine (Recognizer
 * or Synthesizer) are DEALLOCATED,
 * ALLOCATED, ALLOCATING_RESOURCES and
 * DEALLOCATING_RESOURCES.  An engine in the ALLOCATED
 * state has acquired all the resources it requires to perform
 * its core functions.
 * <p>
 * Engines are created in the DEALLOCATED state
 * and a call to allocate is required to prepare
 * them for usage.  The ALLOCATING_RESOURCES state is an
 * intermediate state between DEALLOCATED and
 * ALLOCATED which an engine occupies during
 * the resource allocation process (which may be a very short
 * period or takes 10s of seconds).
 * <p>
 * Once an application finishes using a speech engine it should
 * always explicitly free system resources by calling the
 * deallocate method.  This call transitions the
 * engine to the DEALLOCATED state via some period
 * in the DEALLOCATING_RESOURCES state.
 * <p>
 * The methods of Engine, Recognizer and
 * Synthesizer perform differently according to the
 * engine's allocation state.  Many methods cannot be performed
 * when an engine is in either the DEALLOCATED or
 * DEALLOCATING_RESOURCES state.  Many methods block (wait)
 * for an engine in the ALLOCATING_RESOURCES state until
 * the engine reaches the ALLOCATED state.  This
 * blocking/exception behavior is defined separately for each
 * method of Engine, Synthesizer and
 * Recognizer.
 * <p>
 * Engine State System: Sub-states of ALLOCATED
 * <p>
 * The ALLOCATED states has sub-states.
 * (The DEALLOCATED, ALLOCATING_RESOURCES and
 * DEALLOCATING_RESOURCES states do not have any sub-states.)
 * <p>
 * Any ALLOCATED engine (Recognizer or
 * Synthesizer) is either PAUSED
 * or RESUMED.  These state indicates whether
 * audio input/output is stopped or running.
 * <p>
 * An ALLOCATED Synthesizer has
 * additional sub-states for QUEUE_EMPTY and
 * QUEUE_NOT_EMPTY that indicate the status of
 * its speech output queue.  These two states are independent
 * of the PAUSED and RESUMED states.
 * <p>
 * An ALLOCATED Recognizer has
 * additional sub-states for LISTENING,
 * PROCESSING and SUSPENDED
 * that indicate the status of the recognition process.
 * These three states are independent of the PAUSED
 * and RESUMED states (with the exception of
 * minor interactions documented with Recognizer).
 * <p>
 * An ALLOCATED Recognizer also has
 * additional sub-states for FOCUS_ON and
 * FOCUS_OFF.  Focus determines when most of an
 * application's grammars are active or deactive for recognition.
 * The focus states are independent of the PAUSED
 * and RESUMED states and of the
 * LISTENING/PROCESSING/SUSPENDED states.
 * (Limited exceptions are discussed in the documentation for
 * Recognizer).
 * <p>
 * The pause and resume methods are used
 * to transition an engine between the PAUSED and
 * RESUMED states.  The PAUSED and
 * RESUMED states are shared by all applications that
 * use the underlying engine.  For instance, pausing a recognizer
 * pauses all applications that use that engine.
 * <p>
 * <A>Engine State System: get/test/wait</A>
 * <p>
 * The current state of an Engine is returned
 * by the getEngineState method.  The
 * waitEngineState method blocks the calling
 * thread until the Engine reaches a specified
 * state.  The testEngineState tests whether
 * an Engine is in a specified state.
 * <p>
 * The state values can be bitwise OR'ed (using the Java "|" operator).
 * For example, for an allocated, resumed synthesizer with
 * items in its speech output queue, the state is
 * <p>
 * Engine.ALLOCATED | Engine.RESUMED | Synthesizer.QUEUE_NOT_EMPTY
 * <p>
 * The states and sub-states defined above put constraints upon
 * the state of an engine.  The following are examples of illegal
 * states:
 *
 * @Illegal Engine states:
 * Engine.DEALLOCATED | Engine.RESUMED
 * @Illegal Engine states:
 * Engine.ALLOCATED | Engine.DEALLOCATED
 * @Illegal Synthesizer states:
 * Engine.DEALLOCATED | Engine.QUEUE_NOT_EMPTY
 * @Illegal Synthesizer states:
 * Engine.QUEUE_EMPTY | Engine.QUEUE_NOT_EMPTY
 * @Illegal Recognizer states:
 * Engine.DEALLOCATED | Engine.PROCESSING
 * @Illegal Recognizer states:
 * Engine.PROCESSING | Engine.SUSPENDED
 * <p>
 * Calls to the testEngineState and waitEngineState
 * methods with illegal state values cause an exception to be
 * thrown.
 * @see javax.speech.Central
 * @see javax.speech.synthesis.Synthesizer
 * @see javax.speech.recognition.Recognizer
 */
public interface Engine {

    /**
     * Bit of state that is set when an Engine is
     * in the deallocated state.  A deallocated engine does
     * not have the resources necessary for it to carry out
     * its basic functions.
     * <p>
     * In the DEALLOCATED state, many of the methods
     * of an Engine throw an exception when called.
     * The DEALLOCATED state has no sub-states.
     * <p>
     * An Engine is always created in the
     * DEALLOCATED state.  A DEALLOCATED
     * can transition to the ALLOCATED state
     * via the ALLOCATING_RESOURCES state following a
     * call to the allocate method.  An Engine
     * returns to the DEALLOCATED state via the
     * DEALLOCATING_RESOURCES state with a call to the
     * deallocate method.
     *
     * @see javax.speech.Engine#allocate()
     * @see javax.speech.Engine#deallocate()
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.Engine#waitEngineState(long)
     */
    long DEALLOCATED = 1L;

    /**
     * Bit of state that is set when an Engine is
     * being allocated - the transition state between DEALLOCATED
     * to ALLOCATED following a call to the allocate
     * method.  The ALLOCATING_RESOURCES state has no sub-states.
     * In the ALLOCATING_RESOURCES state, many of the methods
     * of Engine, Recognizer, and
     * Synthesizer will block until the Engine
     * reaches the ALLOCATED state and the action can
     * be performed.
     *
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.Engine#waitEngineState(long)
     */
    long ALLOCATING_RESOURCES = 2L;

    /**
     * Bit of state that is set when an Engine is
     * in the allocated state.  An engine in the ALLOCATED
     * state has acquired the resources required for it to
     * carry out its core functions.
     * <p>
     * The ALLOCATED states has sub-states for
     * RESUMED and PAUSED.  Both
     * Synthesizer and Recognizer
     * define additional sub-states of ALLOCATED.
     * <p>
     * An Engine is always created in the
     * DEALLOCATED state.  It reaches the
     * ALLOCATED state via the ALLOCATING_RESOURCES
     * state with a call to the allocate method.
     *
     * @see javax.speech.synthesis.Synthesizer
     * @see javax.speech.recognition.Recognizer
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.Engine#waitEngineState(long)
     */
    long ALLOCATED = 4L;

    /**
     * Bit of state that is set when an Engine is
     * being deallocated - the transition state between ALLOCATED
     * to DEALLOCATED.  The DEALLOCATING_RESOURCES
     * state has no sub-states.  In the DEALLOCATING_RESOURCES
     * state, most methods of Engine, Recognizer
     * and Synthesizer throw an exception.
     *
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.Engine#waitEngineState(long)
     */
    long DEALLOCATING_RESOURCES = 8L;

    /**
     * Bit of state that is set when an Engine is
     * is in the ALLOCATED state and is PAUSED.
     * In the PAUSED state, audio input or output
     * stopped.
     * <p>
     * An ALLOCATED engine is always in either in
     * the PAUSED or RESUMED.  The
     * PAUSED and RESUMED states are
     * sub-states of the ALLOCATED state.
     *
     * @see javax.speech.Engine#RESUMED
     * @see javax.speech.Engine#ALLOCATED
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.Engine#waitEngineState(long)
     */
    long PAUSED = 256L;

    /**
     * Bit of state that is set when an Engine is
     * is in the ALLOCATED state and is RESUMED.
     * In the RESUMED state, audio input or output active.
     * <p>
     * An ALLOCATED engine is always in either in
     * the PAUSED or RESUMED.  The
     * PAUSED and RESUMED states are
     * sub-states of the ALLOCATED state.
     *
     * @see javax.speech.Engine#RESUMED
     * @see javax.speech.Engine#ALLOCATED
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.Engine#waitEngineState(long)
     */
    long RESUMED = 512L;

    /**
     * Request notifications of events of related to the Engine.
     * An application can attach multiple listeners to an Engine.
     * A single listener can be attached to multiple engines.
     * <p>
     * The EngineListener is extended for both recognition
     * and synthesis.  Typically, a RecognizerListener is
     * attached to a Recognizer and a SynthesizerListener
     * is attached to a Synthesizer.
     * <p>
     * An EngineListener can be attached or removed in any
     * state of an Engine.
     *
     * @param listener the listener that will receive EngineEvents
     * @see javax.speech.recognition.Recognizer
     * @see javax.speech.recognition.RecognizerListener
     * @see javax.speech.synthesis.Synthesizer
     * @see javax.speech.synthesis.SynthesizerListener
     */
    void addEngineListener(EngineListener listener);

    /**
     * Allocate the resources required for the Engine and put it
     * into the ALLOCATED state.
     * When this method returns successfully the ALLOCATED
     * bit of engine state is set, and the
     * testEngineState(Engine.ALLOCATED) method returns true.
     * During the processing of the method, the Engine is
     * temporarily in the ALLOCATING_RESOURCES state.
     * <p>
     * When the Engine reaches the ALLOCATED state
     * other engine states are determined:
     * <p>
     * PAUSED or RESUMED: the pause state
     * depends upon the existing state of the engine.  In a multi-app
     * environment, the pause/resume state of the engine is shared
     * by all apps.
     * A Recognizer always starts in the
     * LISTENING state when newly allocated but may
     * transition immediately to another state.
     * A Recognizer may be allocated in either the
     * HAS_FOCUS state or LOST_FOCUS state
     * depending upon the activity of other applications.
     * A Synthesizer always starts in the
     * QUEUE_EMPTY state when newly allocated.
     * <p>
     * While this method is being processed events are issued to
     * any EngineListeners attached to the Engine
     * to indicate state changes.  First, as the Engine
     * changes from the DEALLOCATED to the
     * ALLOCATING_RESOURCES state, an
     * ENGINE_ALLOCATING_RESOURCES event is issued.
     * As the allocation process completes, the engine moves from
     * the ALLOCATING_RESOURCES state to the
     * ALLOCATED state and an ENGINE_ALLOCATED
     * event is issued.
     * <p>
     * The allocate method should be called for an
     * Engine in the DEALLOCATED state.
     * The method has no effect for an Engine is either
     * the ALLOCATING_RESOURCES or ALLOCATED states.
     * The method throws an exception in the DEALLOCATING_RESOURCES
     * state.
     * <p>
     * If any problems are encountered during the allocation process
     * so that the engine cannot be allocated, the engine returns
     * to the DEALLOCATED state (with an
     * ENGINE_DEALLOCATED event), and an EngineException
     * is thrown.
     * <p>
     * <A/>
     * Allocating the resources for an engine may be fast (less than
     * a second) or slow (several 10s of seconds) depending upon a range
     * of factors.  Since the allocate method does not
     * return until allocation is completed applications may want to perform
     * allocation in a background thread and proceed with other activities.
     * The following code uses an inner class implementation of Runnable
     * to create a background thread for engine allocation:
     * <pre>
     * static Engine engine;
     *
     * public static void main(String argv[])
     * {
     *  engine = Central.createRecognizer();
     *
     *  new Thread(new Runnable() {
     *    public void run() {
     *     engine.allocate();
     *    }
     *  }).start();
     *
     *  // Do other stuff while allocation takes place
     *  ...
     *
     *  // Now wait until allocation is complete
     *  engine.waitEngineState(Engine.ALLOCATED);
     * }
     * </pre>
     *
     * @throws javax.speech.EngineException  if an allocation error occurred or the engine is not operational.
     * @throws javax.speech.EngineStateError if called for an engine in the DEALLOCATING_RESOURCES state
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.Engine#deallocate()
     * @see javax.speech.Engine#ALLOCATED
     * @see javax.speech.EngineEvent#ENGINE_ALLOCATED
     */
    void allocate() throws EngineException, EngineStateError;

    /**
     * Free the resources of the engine that were acquired during
     * allocation and during operation and return the engine
     * to the DEALLOCATED.  When this method returns
     * the DEALLOCATED bit of engine state is set so the
     * testEngineState(Engine.DEALLOCATED) method returns
     * true.  During the processing of the method, the
     * Engine is temporarily in the DEALLOCATING_RESOURCES state.
     * <p>
     * A deallocated engine can be re-started with a subsequent
     * call to allocate.
     * <p>
     * Engines need to clean up current activities before being deallocated.
     * A Synthesizer must be in the QUEUE_EMPTY
     * state before being deallocated.  If the queue is not empty,
     * any objects on the speech output queue must be cancelled
     * with appropriate events issued.
     * A Recognizer cannot be in the PROCESSING
     * state when being deallocated.  If necessary, there must be a
     * forceFinalize of any unfinalized result.
     * <p>
     * While this method is being processed events are issued to any
     * EngineListeners attached to the Engine
     * to indicate state changes. First, as the Engine
     * changes from the ALLOCATED to the
     * DEALLOCATING_RESOURCES state, an
     * ENGINE_DEALLOCATING_RESOURCES event is issued.
     * As the deallocation process completes, the engine moves from
     * the DEALLOCATING_RESOURCES state to the
     * DEALLOCATED state and an ENGINE_DEALLOCATED
     * event is issued.
     * <p>
     * The deallocate method should only be called for an
     * Engine in the ALLOCATED state.
     * The method has no effect for an Engine is either
     * the DEALLOCATING_RESOURCES or DEALLOCATED states.
     * The method throws an exception in the ALLOCATING_RESOURCES
     * state.
     * <p>
     * Deallocating resources for an engine is not always immediate.
     * Since the deallocate method does not return until complete,
     * applications may want to perform deallocation in a separate thread.
     * The documentation for the allocate method shows an
     * example of an
     * <A href="#inner">inner class implementation of
     * Runnable</A>
     * that creates a separate thread.
     *
     * @throws javax.speech.EngineException  if a deallocation error occurs
     * @throws javax.speech.EngineStateError if called for an engine in the ALLOCATING_RESOURCES state
     * @see javax.speech.Engine#allocate()
     * @see javax.speech.EngineEvent#ENGINE_DEALLOCATED
     * @see javax.speech.synthesis.Synthesizer#QUEUE_EMPTY
     */
    void deallocate() throws EngineException, EngineStateError;

    /**
     * Return an object which provides management of the audio
     * input or output for the Engine.
     * <p>
     * The AudioManager is available in any
     * state of an Engine.
     *
     * @return the AudioManager for the engine
     */
    AudioManager getAudioManager();

    /**
     * Return a mode descriptor that defines the operating properties
     * of the engine.  For a Recognizer the return value
     * is a RecognizerModeDesc.  For a Synthesizer
     * the return value is a SynthesizerModeDesc.
     * <p>
     * The EngineModeDesc is available in any
     * state of an Engine.
     *
     * @return an EngineModeDesc for the engine.
     * @throws java.lang.SecurityException if the application does not have accessEngineModeDesc permission
     */
    EngineModeDesc getEngineModeDesc() throws SecurityException;

    /**
     * Return the EngineProperties object (a JavaBean).
     * <p>
     * A Recognizer returns a RecognizerProperties object.
     * The Recognizer interface also defines a
     * getRecognizerProperties method that returns the same
     * object as getEngineProperties, but without requiring a cast
     * to be useful.
     * <p>
     * A Synthesizer returns a SynthesizerProperties object.
     * The Synthesizer interface also defines a
     * getSynthesizerProperties method that returns the same
     * object as getEngineProperties, but without requiring a cast
     * to be useful.
     * <p>
     * The EngineProperties are available in any
     * state of an Engine.  However, changes only
     * take effect once an engine reaches the ALLOCATED state.
     *
     * @return the EngineProperties object for this engine
     * @see javax.speech.recognition.Recognizer#getRecognizerProperties()
     * @see javax.speech.recognition.RecognizerProperties
     * @see javax.speech.synthesis.Synthesizer#getSynthesizerProperties()
     * @see javax.speech.synthesis.SynthesizerProperties
     */
    EngineProperties getEngineProperties();

    /**
     * Returns a or'ed set of flags indicating the current state of
     * an Engine.  The format of the returned
     * state value is
     * <A href="#values">described above</A>
     * .
     * <p>
     * An EngineEvent is issued each time the
     * Engine changes state.
     * <p>
     * The getEngineState method can be called successfully
     * in any Engine state.
     *
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.Engine#waitEngineState(long)
     * @see javax.speech.EngineEvent#getNewEngineState()
     * @see javax.speech.EngineEvent#getOldEngineState()
     */
    long getEngineState();

    /**
     * Return an object which provides management of the
     * vocabulary for the Engine.  See the
     * VocabManager documentation for a description
     * of vocabularies and their use with speech engines.
     * Returns null if the Engine
     * does not provide vocabulary management capabilities.
     * <p>
     * The VocabManager is available for engines
     * in the ALLOCATED state.  The call blocks
     * for engines in the ALLOCATING_RESOURCES.  An
     * error is thrown for engines in the DEALLOCATED
     * or DEALLOCATING_RESOURCES states.
     *
     * @return the VocabManager for the engine or null
     * if it does not have a VocabManager
     * @throws javax.speech.EngineStateError if called for an engine in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.Word
     */
    VocabManager getVocabManager() throws EngineStateError;

    /**
     * Pause the audio stream for the engine and put the Engine
     * into the PAUSED state.  Pausing an engine
     * pauses the underlying engine for all applications that
     * are connected to that engine.  Engines are typically paused and resumed
     * by request from a user.
     * <p>
     * Applications may pause an engine indefinitely.  When an engine
     * moves from the RESUMED state to the PAUSED
     * state, an ENGINE_PAUSED event is issued to each
     * EngineListener attached to the Engine.
     * The PAUSED bit of the engine state is set to
     * true when paused, and can be tested by the
     * getEngineState method and other engine state
     * methods.
     * <p>
     * The PAUSED state is a sub-state of the
     * ALLOCATED state.  An ALLOCATED
     * Engine is always in either the PAUSED
     * or the RESUMED state.
     * <p>
     * It is not an exception to pause an Engine
     * that is already paused.
     * <p>
     * The pause method operates as defined for engines in the
     * ALLOCATED state.  When pause is called for an
     * engine in the ALLOCATING_RESOURCES state, the method
     * blocks (waits) until the ALLOCATED state is
     * reached and then operates normally.  An error is thrown
     * when pause is called for an engine is either the
     * DEALLOCATED is DEALLOCATING_RESOURCES states.
     * state.
     * <p>
     * The pause method does not always return immediately.
     * Some applications need to execute pause in a separate
     * thread.  The documentation for the allocate
     * method includes an
     * <A href="#inner">example implementation
     * of Runnable with inner classes</A>
     * that can perform
     * pause in a separate thread.
     * <h3>
     * Pausing a
     * <A href="synthesis/Synthesizer.html">Synthesizer</A>
     * </h3>
     * The pause/resume mechanism for a synthesizer is analogous
     * to pause/resume on a tape player or CD player.
     * The audio output stream is paused.  The speaking
     * queue is left intact and a subsequent resume continues output from
     * the point at which the pause took effect.
     * <h3>
     * Pausing a
     * <A href="recognition/Recognizer.html">Recognizer</A>
     * </h3>
     * Pause and resume for a recognizer are analogous to turning
     * a microphone off and on.
     * Pausing stops the input audio input stream as close as
     * possible to the time of the call to pause.  The incoming
     * audio between the pause and the resume calls is ignored.
     * <p>
     * Anything a user says while the recognizer is paused will not be
     * heard by the recognizer.
     * Pausing a recognizer during the middle of user speech forces
     * the recognizer to finalize or reject processing of that
     * incoming speech - a recognition result cannot cross a pause/resume
     * boundary.
     * <p>
     * Most recognizers have some amount of internal audio buffering.
     * This means that some recognizer processing may continue after the
     * pause.  For example, results can be created and finalized.
     * <p>
     * Note: recognizers add a special suspend method
     * that allows applications to temporarily stop the recognizer to
     * modify grammars and grammar activation.  Unlike a paused
     * recognizer, a suspended recognizer buffers incoming audio input
     * to be processed once it returns to a listening state, so
     * no audio is lost.
     *
     * @throws javax.speech.EngineStateError if called for an engine in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.Engine#resume()
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.EngineEvent#ENGINE_PAUSED
     * @see javax.speech.recognition.Recognizer#suspend()
     */
    void pause() throws EngineStateError;

    /**
     * Remove a listener from this Engine.
     * An EngineListener can be attached or removed in any
     * state of an Engine.
     *
     * @param listener the listener to be removed
     */
    void removeEngineListener(EngineListener listener);

    /**
     * Put the Engine in the RESUMED state to
     * resume audio streaming to or from a paused engine.
     * Resuming an engine resuming the underlying engine for
     * all applications that are connected to that engine.
     * Engines are typically paused and resumed by request from a user.
     * <p>
     * The specific pause/resume behavior of recognizers and synthesizers
     * is defined in the documentation for the
     * <p>
     * <A href="#pause()">pause</A>
     * method.
     * <p>
     * When an engine moves from the PAUSED state
     * to the RESUMED state, an ENGINE_RESUMED
     * event is issued to each EngineListener attached to
     * the Engine.  The RESUMED bit of the
     * engine state is set to true when resumed, and
     * can be tested by the getEngineState method and other
     * engine state methods.
     * <p>
     * The RESUMED state is a sub-state of the
     * ALLOCATED state. An ALLOCATED
     * Engine is always in either the PAUSED
     * or the RESUMED state.
     * <p>
     * It is not an exception to resume a engine
     * that is already in the RESUMED state.  An exception
     * may be thrown if the audio resource required by the engine
     * (audio input or output) is not available.
     * <p>
     * The resume method operates as defined for engines in the
     * ALLOCATED state.  When resume is called for an
     * engine in the ALLOCATING_RESOURCES state, the method
     * blocks (waits) until the ALLOCATED state is
     * reached and then operates normally.  An error is thrown
     * when resume is called for an engine is either the
     * DEALLOCATED is DEALLOCATING_RESOURCES states.
     * state.
     * <p>
     * The resume method does not always return immediately.
     * Some applications need to execute resume in a separate
     * thread.  The documentation for the allocate
     * method includes an
     * <A href="#inner">example implementation
     * of Runnable with inner classes</A>
     * that could
     * also perform resume in a separate thread.
     *
     * @throws javax.speech.AudioException   if unable to gain access to the audio channel
     * @throws javax.speech.EngineStateError if called for an engine in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.Engine#pause()
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.EngineEvent#ENGINE_RESUMED
     */
    void resume() throws AudioException, EngineStateError;

    /**
     * Returns true if the current engine state matches the
     * specified state.  The format of the state
     * value is
     * <A href="#values">described above</A>
     * .
     * <p>
     * The test performed is not an exact match to the current
     * state.  Only the specified states are tested.  For
     * example the following returns true only if the
     * Synthesizer queue is empty, irrespective
     * of the pause/resume and allocation states.
     * <pre>
     * if (synth.testEngineState(Synthesizer.QUEUE_EMPTY)) ...
     * </pre>
     * The testEngineState method is equivalent to:
     * <pre>
     * if ((engine.getEngineState() & state) == state)
     * </pre>
     * The testEngineState method can be called
     * successfully in any Engine state.
     *
     * @throws java.lang.IllegalArgumentException if the specified state is unreachable
     */
    boolean testEngineState(long state) throws IllegalArgumentException;

    /**
     * Blocks the calling thread until the Engine
     * is in a specified state.  The format of the state
     * value is
     * <A href="#values">described above</A>
     * .
     * <p>
     * All state bits specified in the state parameter
     * must be set in order for the method to return, as defined
     * for the testEngineState method.  If the state
     * parameter defines an unreachable state (e.g. PAUSED | RESUMED)
     * an exception is thrown.
     * <p>
     * The waitEngineState method can be called successfully
     * in any Engine state.
     *
     * @throws java.lang.InterruptedException     if another thread has interrupted this thread.
     * @throws java.lang.IllegalArgumentException if the specified state is unreachable
     * @see javax.speech.Engine#testEngineState(long)
     * @see javax.speech.Engine#getEngineState()
     */
    void waitEngineState(long state) throws InterruptedException, IllegalArgumentException;
}
