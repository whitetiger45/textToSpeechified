package javax.speech.synthesis;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import javax.speech.AudioManager;
import javax.speech.Central;
import javax.speech.Engine;
import javax.speech.EngineListener;
import javax.speech.EngineModeDesc;
import javax.speech.EngineStateError;
import javax.speech.VocabManager;


/**
 * The Synthesizer interface provides primary access to speech
 * synthesis capabilities.  The Synthesizer interface extends
 * the Engine interface.  Thus, any Synthesizer
 * implements basic speech engine capabilities plus the specialized capabilities
 * required for speech synthesis.
 * <p>
 * The primary functions provided by the Synthesizer interface
 * are the ability to speak text, speak Java Speech Markup Language text,
 * and control an output queue of objects to be spoken.
 * <p>
 * Creating a Synthesizer
 * <p>
 * Typically, a Synthesizer is created by a call to the
 * Central.createSynthesizer method.
 * The procedures for locating, selecting, creating and
 * initializing a Synthesizer are described in the documentation for
 * <p>
 * {@link Central}
 * class.
 * <p>
 * Synthesis Package: Inherited and Extended Capabilities
 * <p>
 * A synthesis package inherits many of its important capabilities from
 * the Engine interface and its related support classes and interfaces.
 * The synthesis package adds specialized functionality for
 * performing speech synthesis.
 * <p>
 * Inherits location mechanism by
 * <p>
 * <A href="../Central.html#availableSynthesizers(javax.speech.EngineModeDesc)">
 * Central.availableSynthesizers</A>
 * <p>
 * method and
 * {@link EngineModeDesc}
 * .
 * <p>
 * Extends
 * <p>
 * {@link EngineModeDesc}
 * as
 * <p>
 * <A href="SynthesizerModeDesc.html">SynthesizerModeDesc</A>
 * .
 * <p>
 * Inherits
 * <p>
 * {@link Engine#allocate()}
 * and
 * <p>
 * {@link Engine#deallocate()}
 * methods from the
 * <p>
 * {@link Engine}
 * interface.
 * <p>
 * Inherits
 * {@link Engine#pause()}
 * and
 * <p>
 * {@link Engine#resume()}
 * methods from the
 * <p>
 * {@link Engine}
 * interface.
 * <p>
 * Inherits
 * <p>
 * {@link Engine#getEngineState()}
 * ,
 * <p>
 * {@link Engine#waitEngineState(long)}
 * and
 * <p>
 * {@link Engine#testEngineState(long)}
 * <p>
 * methods from the
 * <p>
 * {@link Engine}
 * interface.
 * <p>
 * Inherits the
 * <p>
 * {@link Engine#DEALLOCATED}
 * ,
 * <p>
 * {@link Engine#ALLOCATED}
 * ,
 * <p>
 * {@link Engine#ALLOCATING_RESOURCES}
 * ,
 * <p>
 * {@link Engine#DEALLOCATING_RESOURCES}
 * ,
 * <p>
 * {@link Engine#PAUSED}
 * and
 * <p>
 * {@link Engine#RESUMED}
 * states from the
 * <p>
 * {@link Engine}
 * interface.
 * <p>
 * Adds
 * <A href="#QUEUE_EMPTY">QUEUE_EMPTY</A>
 * and
 * <p>
 * <A href="#QUEUE_NOT_EMPTY">QUEUE_NOT_EMPTY</A>
 * <p>
 * sub-states to the
 * <p>
 * {@link Engine#ALLOCATED}
 * state.
 * <p>
 * Inherits audio management:
 * see
 * {@link Engine#getAudioManager()}
 * and
 * {@link AudioManager}
 * .
 * <p>
 * Inherits vocabulary management:
 * see
 * {@link Engine#getVocabManager()}
 * and
 * {@link VocabManager}
 * .
 * <p>
 * Inherits
 * <p>
 * <A href="../Engine.html#addEngineListener(javax.speech.EngineListener)">
 * addEngineListener</A>
 * and
 * <p>
 * <A href="../Engine.html#removeEngineListener(javax.speech.EngineListener)">
 * removeEngineListener</A>
 * <p>
 * methods and uses the
 * <p>
 * {@link EngineListener}
 * interface.
 * <p>
 * Extends
 * {@link EngineListener}
 * interface
 * to
 * <A href="SynthesizerListener.html">SynthesizerListener</A>
 * .
 * <p>
 * Adds
 * <p>
 * <A href="#speak(javax.speech.synthesis.Speakable, javax.speech.synthesis.SpeakableListener)">
 * speak(Speakable, Listener)</A>
 * ,
 * <p>
 * <A href="#speak(java.net.URL, javax.speech.synthesis.SpeakableListener)">
 * speak(URL, Listener)</A>
 * ,
 * <p>
 * <A href="#speak(java.lang.String, javax.speech.synthesis.SpeakableListener)">
 * speak(String, Listener)</A>
 * <p>
 * and
 * <p>
 * <A href="#speakPlainText(java.lang.String, javax.speech.synthesis.SpeakableListener)">
 * speakPlainText(String)</A>
 * <p>
 * methods to place text on the output queue of the synthesizer.
 * <p>
 * Adds
 * <p>
 * <A href="#phoneme(java.lang.String)"> phoneme(String)</A>
 * method that converts text to phonemes.
 * <p>
 * Adds
 * <p>
 * <A href="#enumerateQueue()">enumerateQueue</A>
 * ,
 * <p>
 * <A href="#cancel()">cancel()</A>
 * ,
 * <p>
 * <A href="#cancel(java.lang.Object)">cancel(Object)</A>
 * and
 * <p>
 * <A href="#cancelAll()">cancelAll</A>
 * methods
 * for management of output queue.
 * <p>
 * Speaking Text
 * <p>
 * The basic function of a Synthesizer is to speak text provided to
 * it by an application.  This text can be plain Unicode text in a String
 * or can be  marked up using the
 * <p>
 * <A href="http://java.sun.com/products/java-media/speech/forDevelopers/JSML/index.html">
 * Java Speech Markup Language (JSML)</A>
 * .
 * <p>
 * Plain text is spoken using the speakPlainText method.
 * JSML text is spoken using one of the three speak methods.  The
 * speak methods obtain the JSML text for a Speakable
 * object, from a URL, or from a String.
 * <p>
 * [Note: JSML text provided programmatically (by a Speakable object
 * or a String) does not require the full XML header.  JSML
 * text obtained from a URL requires the full XML header.]
 * <p>
 * A synthesizer is mono-lingual (it speaks a single language) so the text
 * should contain only the single language of the synthesizer.
 * An application requiring output of more than one language needs to
 * create multiple Synthesizer object through Central.
 * The language of the Synthesizer should be selected at
 * the time at which it is created.  The language for a created
 * Synthesizer can be checked through the Locale
 * of its EngineModeDesc (see getEngineModeDesc).
 * <p>
 * Each object provided to a synthesizer is spoken independently.
 * Sentences, phrases and other structures should not span multiple
 * call to the speak methods.
 * <p>
 * Synthesizer State System
 * <p>
 * Synthesizer extends the state system of the generic
 * Engine interface.  It inherits the four basic
 * allocation states, plus the PAUSED and RESUMED
 * states.
 * <p>
 * Synthesizer adds a pair of sub-states to the
 * ALLOCATED state to represent the state of the
 * speech output queue (queuing is described in more detail below).
 * For an ALLOCATED Synthesizer, the
 * speech output queue is either empty or not empty: represented
 * by the states QUEUE_EMPTY and QUEUE_NOT_EMPTY.
 * <p>
 * The queue status is independent of the pause/resume status.
 * Pausing or resuming a synthesizer does not effect the queue.
 * Adding or removing objects from the queue does not effect the
 * pause/resume status.  The only form of interaction between
 * these state systems is that the Synthesizer only
 * speaks in the RESUMED state, and therefore,
 * a transition from QUEUE_NOT_EMPTY to QUEUE_EMPTY
 * because of completion of speaking an object is only possible
 * in the RESUMED state.  (A transition from
 * QUEUE_NOT_EMPTY to QUEUE_EMPTY is
 * possible in the PAUSED state only through a call
 * to one of the cancel methods.)
 * <p>
 * Speech Output Queue
 * <p>
 * A synthesizer implements a queue of items provided to it
 * through the speak and speakPlainText methods.
 * The queue is "first-in, first-out (FIFO)" -- the objects are
 * spoken in exactly he order in which they are received.
 * The object at the top of the queue is the object that is
 * currently being spoken or about to be spoken.
 * <p>
 * The QUEUE_EMPTY and QUEUE_NOT_EMPTY states
 * of a Synthesizer indicate the current state of
 * of the speech output queue.  The state handling methods
 * inherited from the Engine interface
 * (getEngineState, waitEngineState and
 * testEngineState) can be used to test the queue state.
 * <p>
 * The items on the queue can be checked with the enumerateQueue
 * method which returns a snapshot of the queue.
 * <p>
 * The cancel methods allows an application to (a) stop
 * the output of item currently at the top of the speaking queue,
 * (b) remove an arbitrary item from the queue, or (c) remove
 * all items from the output queue.
 * <p>
 * Applications requiring more complex queuing mechanisms (e.g. a prioritized
 * queue) can implement their own queuing objects that control the synthesizer.
 * <p>
 * Pause and Resume
 * <p>
 * The pause and resume methods (inherited from the javax.speech.Engine
 * interface) have behavior like a "tape player".  Pause stops audio output
 * as soon as possible.  Resume restarts audio output from the point of the pause.
 * Pause and resume may occur within words, phrases or unnatural points
 * in the speech output.
 * <p>
 * Pause and resume do not affect the speech output queue.
 * <p>
 * In addition to the ENGINE_PAUSED and ENGINE_RESUMED
 * events issued to the EngineListener (or SynthesizerListener),
 * SPEAKABLE_PAUSED and SPEAKABLE_RESUMED events are
 * issued to appropriate SpeakableListeners for the Speakable
 * object at the top of the speaking queue.  (The SpeakableEvent
 * is first issued to any SpeakableListener provided with the
 * speak method, then to each SpeakableListener
 * attached to the Synthesizer.  Finally, the EngineEvent
 * is issued to each SynthesizerListener and EngineListener
 * attached to the Synthesizer.)
 * <p>
 * Applications can determine the approximate point at which a pause occurs by
 * monitoring the WORD_STARTED events.
 *
 * @see javax.speech.Central
 * @see javax.speech.synthesis.Speakable
 * @see javax.speech.synthesis.SpeakableListener
 * @see javax.speech.EngineListener
 * @see javax.speech.synthesis.SynthesizerListener
 */
public interface Synthesizer extends Engine {

    /**
     * Bit of state that is set when the speech output
     * queue of a Synthesizer is empty.
     * The QUEUE_EMPTY state is a sub-state
     * of the ALLOCATED state.  An allocated
     * Synthesizer is always in either the
     * QUEUE_NOT_EMPTY or QUEUE_EMPTY state.
     * <p>
     * A Synthesizer is always allocated in the
     * QUEUE_EMPTY state.  The Synthesizer
     * transitions from the QUEUE_EMPTY state
     * to the QUEUE_NOT_EMPTY state when a
     * call to one of the speak methods places
     * an object on the speech output queue.  A QUEUE_UPDATED
     * event is issued to indicate this change in state.
     * <p>
     * A Synthesizer returns from the
     * QUEUE_NOT_EMPTY state to the QUEUE_EMPTY
     * state once the queue is emptied because of completion
     * of speaking all objects or because of a cancel.
     * <p>
     * The queue status can be tested with the waitQueueEmpty,
     * getEngineState and testEngineState methods.
     * To block a thread until the queue is empty:
     * <p>
     * Synthesizer synth = ...;
     * synth.waitEngineState(QUEUE_EMPTY);
     *
     * @see javax.speech.synthesis.Synthesizer#QUEUE_NOT_EMPTY
     * @see javax.speech.Engine#ALLOCATED
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.Engine#waitEngineState(long)
     * @see javax.speech.Engine#testEngineState(long)
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_UPDATED
     */
    long QUEUE_EMPTY = 65536L;

    /**
     * Bit of state that is set when the speech output
     * queue of a Synthesizer is not empty.
     * The QUEUE_NOT_EMPTY state is a sub-state
     * of the ALLOCATED state.  An allocated
     * Synthesizer is always in either the
     * QUEUE_NOT_EMPTY or QUEUE_EMPTY state.
     * <p>
     * A Synthesizer enters the QUEUE_NOT_EMPTY
     * from the QUEUE_EMPTY state when one of the
     * speak methods is called to place an object
     * on the speech output queue.  A QUEUE_UPDATED
     * event is issued to mark this change in state.
     * <p>
     * A Synthesizer returns from the
     * QUEUE_NOT_EMPTY state to the QUEUE_EMPTY
     * state once the queue is emptied because of completion
     * of speaking all objects or because of a cancel.
     *
     * @see javax.speech.synthesis.Synthesizer#QUEUE_EMPTY
     * @see javax.speech.Engine#ALLOCATED
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.Engine#waitEngineState(long)
     * @see javax.speech.Engine#testEngineState(long)
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_UPDATED
     */
    long QUEUE_NOT_EMPTY = 131072L;

    /**
     * Request notifications of all SpeakableEvents for
     * all speech output objects for this Synthesizer.
     * An application can attach multiple SpeakableListeners
     * to a Synthesizer.
     * A single listener can be attached to multiple synthesizers.
     * <p>
     * When an event effects more than one item in the speech output
     * queue (e.g. cancelAll), the SpeakableEvents
     * are issued in the order of the items in the queue starting with
     * the top of the queue.
     * <p>
     * A SpeakableListener can also provided for an
     * individual speech output item by providing it as a parameter
     * to one of the speak or speakPlainText
     * methods.
     * <p>
     * A SpeakableListener can be attached or removed in any
     * Engine state.
     *
     * @param listener the listener that will receive SpeakableEvents
     * @see javax.speech.synthesis.Synthesizer#removeSpeakableListener(javax.speech.synthesis.SpeakableListener)
     */
    void addSpeakableListener(SpeakableListener listener);

    /**
     * Cancel output of the current object at the top of the output queue.
     * A SPEAKABLE_CANCELLED event is issued to
     * appropriate SpeakableListeners.
     * <p>
     * If there is another object in the speaking queue, it is moved to top
     * of queue and receives the TOP_OF_QUEUE event.  If the
     * Synthesizer is not paused, speech output continues
     * with that object.  To prevent speech output continuing with the
     * next object in the queue, call pause before calling
     * cancel.
     * <p>
     * A SynthesizerEvent is issued to indicate
     * QUEUE_UPDATED (if objects remain on the queue) or
     * QUEUE_EMPTIED (if the cancel leaves the queue empty).
     * <p>
     * It is not an exception to call cancel if the speech output
     * queue is empty.
     * <p>
     * The cancel methods work in the
     * ALLOCATED state.  The calls blocks if the
     * Synthesizer in the ALLOCATING_RESOURCES
     * state and complete when the engine reaches the ALLOCATED
     * state.  An error is thrown for synthesizers in the
     * DEALLOCATED or DEALLOCATING_RESOURCES states.
     *
     * @throws javax.speech.EngineStateError if called for a synthesizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.synthesis.Synthesizer#cancel(java.lang.Object)
     * @see javax.speech.synthesis.Synthesizer#cancelAll()
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_UPDATED
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_EMPTIED
     * @see javax.speech.synthesis.SpeakableEvent#TOP_OF_QUEUE
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_CANCELLED
     */
    void cancel() throws EngineStateError;

    /**
     * Remove a specified item from the speech output queue.  The
     * source object must be one of the items passed to a speak
     * method.  A SPEAKABLE_CANCELLED event is issued to
     * appropriate SpeakableListeners.
     * <p>
     * If the source object is the top item in the queue, the behavior
     * is the same as the cancel() method.
     * <p>
     * If the source object is not at the top of the queue, it is
     * removed from the queue without affecting the current top-of-queue
     * speech output.  A QUEUE_UPDATED
     * is then issued to SynthesizerListeners.
     * <p>
     * If the source object appears multiple times in the queue, only
     * the first instance is cancelled.
     * <p>
     * Warning: cancelling an object just after the synthesizer has
     * completed speaking it and has removed the object from the queue
     * will cause an exception.  In this instance, the exception can
     * be ignored.
     * <p>
     * The cancel methods work in the
     * ALLOCATED state.  The calls blocks if the
     * Synthesizer in the ALLOCATING_RESOURCES
     * state and complete when the engine reaches the ALLOCATED
     * state.  An error is thrown for synthesizers in the
     * DEALLOCATED or DEALLOCATING_RESOURCES states.
     *
     * @param source object to be removed from the speech output queue
     * @throws java.lang.IllegalArgumentException if the source object is not found in the speech output queue.
     * @throws javax.speech.EngineStateError      if called for a synthesizer in the DEALLOCATED or
     *                                            DEALLOCATING_RESOURCES states
     * @see javax.speech.synthesis.Synthesizer#cancel()
     * @see javax.speech.synthesis.Synthesizer#cancelAll()
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_UPDATED
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_EMPTIED
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_CANCELLED
     */
    void cancel(Object source) throws IllegalArgumentException, EngineStateError;

    /**
     * Cancel all objects in the synthesizer speech output queue and stop
     * speaking the current top-of-queue object.
     * <p>
     * The SpeakableListeners of each cancelled item on
     * the queue receive a SPEAKABLE_CANCELLED event.
     * A QUEUE_EMPTIED event is issued to attached
     * SynthesizerListeners.
     * <p>
     * A cancelAll is implicitly performed before
     * a Synthesizer is deallocated.
     * <p>
     * The cancel methods work in the
     * ALLOCATED state.  The calls blocks if the
     * Synthesizer in the ALLOCATING_RESOURCES
     * state and complete when the engine reaches the ALLOCATED
     * state.  An error is thrown for synthesizers in the
     * DEALLOCATED or DEALLOCATING_RESOURCES states.
     *
     * @throws javax.speech.EngineStateError if called for a synthesizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.synthesis.Synthesizer#cancel()
     * @see javax.speech.synthesis.Synthesizer#cancel(java.lang.Object)
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_EMPTIED
     * @see javax.speech.synthesis.SpeakableEvent#SPEAKABLE_CANCELLED
     */
    void cancelAll() throws EngineStateError;

    /**
     * Return an Enumeration containing a snapshot of
     * all the objects currently on the speech output queue.  The
     * first item is the top of the queue.  An empty queue returns a
     * null object.
     * <p>
     * If the return value is non-null then each object it contains
     * is guaranteed to be a SynthesizerQueueItem object
     * representing the source object (Speakable object,
     * URL, or a String) and the JSML or
     * plain text obtained from that object.
     * <p>
     * A QUEUE_UPDATED event is issued to each
     * SynthesizerListener whenever the speech output
     * queue changes.  A QUEUE_EMPTIED event is issued
     * whenever the queue the emptied.
     * <p>
     * This method returns only the items on the speech queue
     * placed there by the current application or applet.  For
     * security reasons, it is not possible to inspect items placed
     * by other applications.
     * <p>
     * The items on the speech queue cannot be modified by changing
     * the object returned from this method.
     * <p>
     * The enumerateQueue method works in the
     * ALLOCATED state.  The call blocks if the
     * Synthesizer in the ALLOCATING_RESOURCES
     * state and completes when the engine reaches the ALLOCATED
     * state.  An error is thrown for synthesizers in the
     * DEALLOCATED or DEALLOCATING_RESOURCES states.
     *
     * @return an Enumeration of the speech output queue or null
     * @throws javax.speech.EngineStateError if called for a synthesizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.synthesis.SynthesizerQueueItem
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_UPDATED
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_EMPTIED
     * @see javax.speech.Engine#addEngineListener(javax.speech.EngineListener)
     */
    Enumeration<?> enumerateQueue() throws EngineStateError;

    /**
     * Return the SynthesizerProperties object (a JavaBean).
     * The method returns exactly the same object as the
     * getEngineProperties method in the Engine
     * interface.  However, with the getSynthesizerProperties
     * method, an application does not need to cast the return value.
     * <p>
     * The SynthesizerProperties are available in any state of
     * an Engine. However, changes only take effect once an
     * engine reaches the ALLOCATED state.
     *
     * @return the SynthesizerProperties object for this engine
     * @see javax.speech.Engine#getEngineProperties()
     */
    SynthesizerProperties getSynthesizerProperties();

    /**
     * Returns the phoneme string for a text string.  The return string
     * uses the International Phonetic Alphabet subset of Unicode.
     * The input string is expected to be simple text (for example,
     * a word or phrase in English).  The text is not expected to
     * contain punctuation or JSML markup.
     * <p>
     * If the Synthesizer does not support text-to-phoneme
     * conversion or cannot process the input text it will return null.
     * <p>
     * If the text has multiple pronunciations, there is no way to
     * indicate which pronunciation is preferred.
     * <p>
     * The phoneme method operate as defined only when a
     * Synthesizer is in the ALLOCATED state.
     * The call blocks if the Synthesizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * for synthesizers in the DEALLOCATED or
     * DEALLOCATING_RESOURCES states.
     *
     * @param text plain text to be converted to phonemes
     * @return phonemic representation of text or null
     * @throws javax.speech.EngineStateError if called for a synthesizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     */
    String phoneme(String text) throws EngineStateError;

    /**
     * Remove a SpeakableListener from this Synthesizer.
     * <p>
     * A SpeakableListener can be attached or removed in any
     * Engine state.
     *
     * @see javax.speech.synthesis.Synthesizer#addSpeakableListener(javax.speech.synthesis.SpeakableListener)
     */
    void removeSpeakableListener(SpeakableListener listener);

    /**
     * Speak a string containing text formatted with the
     * <p>
     * <A href="http://java.sun.com/products/java-media/speech/forDevelopers/JSML/index.html">
     * Java Speech Markup Language</A>
     * .  The JSML text is checked for
     * formatting errors and a JSMLException is thrown
     * if any are found.  If legal, the text is placed at the end of
     * the speaking queue and will be spoken once it reaches the top
     * of the queue and the synthesizer is in the RESUMED state.
     * In all other respects is it identical to the speak
     * method that accepts a Speakable object.
     * <p>
     * The source of a SpeakableEvent issued to the
     * SpeakableListener is the String.
     * <p>
     * The speak methods operate as defined only when a
     * Synthesizer is in the ALLOCATED state.
     * The call blocks if the Synthesizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * for synthesizers in the DEALLOCATED or
     * DEALLOCATING_RESOURCES states.
     *
     * @param JSMLText String contains Java Speech Markup Language text to be spoken
     * @param listener receives notification of events as synthesis output proceeds
     * @throws JSMLException                 if any syntax errors are encountered in JSMLtext
     * @throws javax.speech.EngineStateError if called for a synthesizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.synthesis.Synthesizer#speak(javax.speech.synthesis.Speakable, SpeakableListener)
     * @see javax.speech.synthesis.Synthesizer#speak(java.net.URL, SpeakableListener)
     * @see javax.speech.synthesis.Synthesizer#speakPlainText(java.lang.String, SpeakableListener)
     */
    void speak(String JSMLText, SpeakableListener listener) throws JSMLException, EngineStateError;

    /**
     * Speak text from a URL formatted with the
     * <p>
     * <A href="http://java.sun.com/products/java-media/speech/forDevelopers/JSML/index.html">
     * Java Speech Markup Language</A>
     * .  The text is obtained from the
     * URL, checked for legal JSML formatting, and placed at the end of
     * the speaking queue.  It is spoken once it reaches the top
     * of the queue and the synthesizer is in the RESUMED state.
     * In other respects is it identical to the speak
     * method that accepts a Speakable object.
     * <p>
     * The source of a SpeakableEvent issued to the
     * SpeakableListener is the URL.
     * <p>
     * Because of the need to check JSML syntax, this speak
     * method returns only once the complete URL is loaded, or until
     * a syntax error is detected in the URL stream.  Network delays
     * will cause the method to return slowly.
     * <p>
     * Note: the full XML header is required in the JSML text provided
     * in the URL.  The header is optional on programmatically generated
     * JSML (ie. with the speak(String, Listener) and
     * speak(Speakable, Listener) methods.
     * <p>
     * The speak methods operate as defined only when a
     * Synthesizer is in the ALLOCATED state.
     * The call blocks if the Synthesizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * for synthesizers in the DEALLOCATED or
     * DEALLOCATING_RESOURCES states.
     *
     * @param JSMLurl  URL containing Java Speech Markup Language text to be spoken
     * @param listener receives notification of events as synthesis output proceeds
     * @throws JSMLException                 if any syntax errors are encountered in JSMLtext
     * @throws javax.speech.EngineStateError if called for a synthesizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.synthesis.Synthesizer#speak(javax.speech.synthesis.Speakable, SpeakableListener)
     * @see javax.speech.synthesis.Synthesizer#speak(java.lang.String, SpeakableListener)
     * @see javax.speech.synthesis.Synthesizer#speakPlainText(java.lang.String, SpeakableListener)
     * @see javax.speech.synthesis.SpeakableEvent
     * @see javax.speech.synthesis.Synthesizer#addSpeakableListener(javax.speech.synthesis.SpeakableListener)
     */
    void speak(URL JSMLurl, SpeakableListener listener) throws JSMLException, MalformedURLException, IOException, EngineStateError;

    /**
     * Speak an object that implements the Speakable interface
     * and provides text marked with the
     * <p>
     * <A href="http://java.sun.com/products/java-media/speech/forDevelopers/JSML/index.html">
     * Java Speech Markup Language</A>
     * .
     * The Speakable object is added to the end of
     * the speaking queue and will be spoken once it reaches the top
     * of the queue and the synthesizer is in the RESUMED state.
     * <p>
     * The synthesizer first requests the text of the Speakable
     * by calling its getJSMLText method.  It then checks the
     * syntax of the JSML markup and throws a JSMLException
     * if any problems are found.  If the JSML text is legal, the text
     * is placed on the speech output queue.
     * <p>
     * When the speech output queue is updated, a QUEUE_UPDATE
     * event is issued to SynthesizerListeners.
     * <p>
     * Events associated with the Speakable object are issued
     * to the SpeakableListener object.  The listener
     * may be null.  A listener attached with this method
     * cannot be removed with a subsequent remove call.  The source
     * for the SpeakableEvents is the JSMLtext object.
     * <p>
     * SpeakableEvents can also be received by attaching a
     * SpeakableListener to the Synthesizer with the
     * addSpeakableListener method.  A SpeakableListener
     * attached to the Synthesizer receives all SpeakableEvents
     * for all speech output items of the synthesizer (rather than for a
     * single Speakable).
     * <p>
     * The speak call is asynchronous: it returns once the text for the
     * Speakable has been obtained, checked for syntax,
     * and placed on the synthesizer's speech output queue.
     * An application needing to know when the Speakable has
     * been spoken should wait for the SPEAKABLE_ENDED
     * event to be issued to the SpeakableListener object.
     * The getEngineState, waitEngineState and
     * enumerateQueue methods can be used to determine
     * the speech output queue status.
     * <p>
     * An object placed on the speech output queue can be removed
     * with one of the cancel methods.
     * <p>
     * The speak methods operate as defined only when a
     * Synthesizer is in the ALLOCATED state.
     * The call blocks if the Synthesizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * for synthesizers in the DEALLOCATED or
     * DEALLOCATING_RESOURCES states.
     *
     * @param JSMLText object implementing the Speakable interface that provides
     *                 Java Speech Markup Language text to be spoken
     * @param listener receives notification of events as synthesis output proceeds
     * @throws javax.speech.synthesis.JSMLException if any syntax errors are encountered in JSMLtext
     * @throws javax.speech.EngineStateError        if called for a synthesizer in the DEALLOCATED or
     *                                              DEALLOCATING_RESOURCES states
     * @see javax.speech.synthesis.Synthesizer#speak(java.lang.String, SpeakableListener)
     * @see javax.speech.synthesis.Synthesizer#speak(java.net.URL, SpeakableListener)
     * @see javax.speech.synthesis.Synthesizer#speakPlainText(java.lang.String, SpeakableListener)
     * @see javax.speech.synthesis.SpeakableEvent
     * @see javax.speech.synthesis.Synthesizer#addSpeakableListener(javax.speech.synthesis.SpeakableListener)
     */
    void speak(Speakable JSMLText, SpeakableListener listener) throws JSMLException, EngineStateError;

    /**
     * Speak a plain text string.  The text is not interpreted as
     * containing the Java Speech Markup Language so JSML elements
     * are ignored.  The text is placed at the end of the speaking queue
     * and will be spoken once it reaches the top of the queue and the
     * synthesizer is in the RESUMED state.
     * In other respects it is similar to the speak
     * method that accepts a Speakable object.
     * <p>
     * The source of a SpeakableEvent issued to the
     * SpeakableListener is the String object.
     * <p>
     * The speak methods operate as defined only when a
     * Synthesizer is in the ALLOCATED state.
     * The call blocks if the Synthesizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * for synthesizers in the DEALLOCATED or
     * DEALLOCATING_RESOURCES states.
     *
     * @param JSMLText String contains playing text to be spoken
     * @param listener receives notification of events as synthesis output proceeds
     * @throws javax.speech.EngineStateError if called for a synthesizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.synthesis.Synthesizer#speak(javax.speech.synthesis.Speakable, SpeakableListener)
     * @see javax.speech.synthesis.Synthesizer#speak(java.net.URL, SpeakableListener)
     * @see javax.speech.synthesis.Synthesizer#speak(java.lang.String, SpeakableListener)
     */
    void speakPlainText(String JSMLText, SpeakableListener listener) throws EngineStateError;
}
