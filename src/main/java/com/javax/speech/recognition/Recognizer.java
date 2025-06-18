package javax.speech.recognition;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import javax.speech.AudioManager;
import javax.speech.Central;
import javax.speech.Engine;
import javax.speech.EngineListener;
import javax.speech.EngineModeDesc;
import javax.speech.EngineStateError;
import javax.speech.VendorDataException;
import javax.speech.VocabManager;


/**
 * A Recognizer provides access to speech recognition
 * capabilities.  The Recognizer interface extends the
 * <p>
 * interface and so inherits the basic engine capabilities and
 * provides additional specialized capabilities.
 * <p>
 * The primary capabilities provided by a recognizer are grammar management and
 * result handling.  An application is responsible for providing a recognizer
 * with grammars.  A
 * <A href="Grammar.html">Grammar</A>
 * defines a set
 * of words (technically known as tokens) and defines patterns in which the tokens may
 * be spoken.  When a grammar is active the recognizer listens for speech
 * in the incoming audio which matches the grammar.  When speech is detected
 * the recognizer produces a Result.  The result object
 * is passed to the application and contains information on which words were heard.
 * <p>
 * The types of grammars, mechanisms for creating, modifying and managing
 * grammars, types of results, result handling and other recognizer functions
 * are described in more detail below.
 * <p>
 * Creating a Recognizer
 * <p>
 * A Recognizer is created by a call to
 * <p>
 * <A href="../Central.html#createRecognizer(javax.speech.EngineModeDesc)">
 * createRecognizer</A>
 * method of the Central class.
 * Detailed descriptions of the procedures for locating, selecting, creating and
 * initializing a Recognizer are provided in the documentation for
 * <p>
 * {@link Central}
 * .
 * <p>
 * Inherited and Extended Engine Capabilities
 * <p>
 * The Recognizer interface and the other classes and
 * interfaces of the javax.speech.recognition package
 * extend and modify the basic engine capabilities in the following ways.
 * <p>
 * Inherits engine location by
 * <p>
 * <A href="../Central.html#availableRecognizers(javax.speech.EngineModeDesc)">
 * Central.availableRecognizers</A>
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
 * <A href="RecognizerModeDesc.html">RecognizerModeDesc</A>
 * .
 * <p>
 * Inherits
 * {@link Engine#allocate()}
 * and
 * <p>
 * {@link Engine#deallocate()}
 * methods from
 * <p>
 * {@link Engine}
 * .
 * <p>
 * Inherits
 * {@link Engine#pause()}
 * and
 * <p>
 * {@link Engine#resume()}
 * methods from
 * <p>
 * {@link Engine}
 * .
 * <p>
 * Inherits the
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
 * interface for handling
 * engine state.
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
 * and
 * <p>
 * {@link Engine#DEALLOCATING_RESOURCES}
 * states from the
 * <p>
 * {@link Engine}
 * interface.
 * <p>
 * Inherits the
 * <p>
 * {@link Engine#PAUSED}
 * and
 * <p>
 * {@link Engine#RESUMED}
 * sub-states
 * of the
 * {@link Engine#ALLOCATED}
 * state.
 * <p>
 * Adds
 * <A href="#PROCESSING">PROCESSING</A>
 * ,
 * <p>
 * <A href="#LISTENING">LISTENING</A>
 * and
 * <p>
 * <A href="#SUSPENDED">SUSPENDED</A>
 * sub-states
 * of the
 * {@link Engine#ALLOCATED}
 * state.
 * <p>
 * Adds
 * <A href="#FOCUS_ON">FOCUS_ON</A>
 * and
 * <p>
 * <A href="#FOCUS_OFF">FOCUS_OFF</A>
 * sub-states
 * of the
 * {@link Engine#ALLOCATED}
 * state.
 * <p>
 * Adds
 * <p>
 * <A href="#requestFocus()">requestFocus</A>
 * and
 * <p>
 * <A href="#releaseFocus()">releaseFocus</A>
 * methods for managing an application's speech focus.
 * <p>
 * Adds special suspend and commit mechanisms for updating definitions and
 * enabling of grammars:
 * <p>
 * <A href="#suspend()">suspend</A>
 * ,
 * <p>
 * <A href="#commitChanges()">commitChanges</A>
 * .
 * <p>
 * Inherits audio management:
 * see
 * {@link Engine#getAudioManager()}
 * and
 * {@link AudioManager}
 * .
 * <p>
 * Inherits the
 * <p>
 * {@link Engine#getEngineProperties()}
 * method adds the
 * <A href="#getRecognizerProperties()">getRecognizerProperties</A>
 * so that a cast is not required.
 * <p>
 * Extends audio event mechanism:
 * see
 * <A href="RecognizerAudioListener.html">RecognizerAudioListener</A>
 * and
 * <A href="RecognizerAudioEvent.html">RecognizerAudioEvent</A>
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
 * methods from the
 * <p>
 * {@link Engine}
 * interface.
 * <p>
 * Extends
 * {@link EngineListener}
 * interface
 * as
 * <A href="RecognizerListener.html">RecognizerListener</A>
 * .
 * <p>
 * Adds grammar management of
 * <p>
 * <A href="Grammar.html">Grammar</A>
 * ,
 * <p>
 * <A href="RuleGrammar.html">RuleGrammar</A>
 * ,
 * <p>
 * <A href="DictationGrammar.html">DictationGrammar</A>
 * <p>
 * <A href="GrammarEvent.html">GrammarEvent</A>
 * ,
 * and
 * <p>
 * <A href="GrammarListener.html">GrammarListener</A>
 * ,
 * <p>
 * Adds recognition result mechanisms through
 * <p>
 * <A href="Result.html">Result</A>
 * ,
 * <p>
 * <A href="FinalResult.html">FinalResult</A>
 * ,
 * <p>
 * <A href="FinalRuleResult.html">FinalRuleResult</A>
 * ,
 * <p>
 * <A href="FinalDictationResult.html">FinalDictationResult</A>
 * ,
 * <p>
 * <A href="ResultListener.html">ResultListener</A>
 * and
 * <p>
 * <A href="ResultEvent.html">ResultEvent</A>
 * .
 * <p>
 * Grammars
 * <p>
 * The creation, modification, enabling, activation and other management of
 * grammars is a core function provided by any recognizer.
 * A recognizer must be provided with one or more grammars that indicate what
 * words and word sequences it should listen for.  The basic
 * process for dealing with a grammar are:
 * <p>
 * Create a new Grammar or obtain a reference
 * to an existing grammar.
 * Attach a ResultListener to either the
 * Recognizer or Grammar to get
 * Result events.
 * As necessary, setup or modify the grammar according to the application context.
 * Enable and disable the Grammar for recognition as required.
 * Commit changes to grammar definition and enabled status.
 * Repeat steps 1 through 5 as required.
 * Delete application-created grammars when they are no longer needed.
 * <p>
 * Each grammar must be identified by a unique grammar name
 * which is defined when the Grammar is created.
 * The following methods deal with grammar names:
 * <p>
 * <A href="Grammar.html#getName()">Grammar.getName</A>
 * returns the name of a Grammar object.
 * <p>
 * <A href="#getRuleGrammar(java.lang.String)">getRuleGrammar</A>
 * <p>
 * returns a reference to a Grammar given the grammar's names.
 * <p>
 * A Recognizer provides several methods for the creation and loading of
 * RuleGrammars:
 * <p>
 * <A href="#newRuleGrammar(java.lang.String)">newRuleGrammar</A>
 * :
 * create a RuleGrammar from scratch
 * <p>
 * <A href="#loadJSGF(java.io.Reader)">loadJSGF</A>
 * : create a
 * RuleGrammar(s)
 * from Java Speech Grammar Format text obtained from either a
 * <p>
 * <A href="#loadJSGF(java.io.Reader)">Reader</A>
 * or a
 * <p>
 * <A href="#loadJSGF(java.net.URL, java.lang.String)">URL</A>
 * .
 * The advanced
 * <p>
 * <A href="#loadJSGF(java.net.URL, java.lang.String, boolean, boolean, java.util.Vector)">
 * loadJSGF</A>
 * method provides additional load controls.
 * <p>
 * <A href="#readVendorGrammar(java.io.InputStream)">readVendorGrammar</A>
 * :
 * read a grammar stored in a vendor-specific (non-portable) format.
 * <p>
 * Other important rule grammar functions are:
 * <p>
 * <A href="#deleteRuleGrammar(javax.speech.recognition.RuleGrammar)">
 * deleteRuleGrammar</A>
 * deletes a loaded grammar.
 * <p>
 * <A href="#listRuleGrammars()">listRuleGrammars</A>
 * returns an array
 * of references to all grammars loaded into a Recognizer.
 * <p>
 * <A href="RuleGrammar.html#toString()">RuleGrammar.toString</A>
 * produces a string representing a RuleGrammar in Java Speech Grammar Format.
 * <p>
 * <A href="#writeVendorGrammar(java.io.OutputStream, javax.speech.recognition.Grammar)">
 * writeVendorGrammar</A>
 * : write a grammar
 * in a vendor-specific (non-portable) format.
 * <p>
 * In addition to RuleGrammars that an application creates, some recognizers
 * have "built-in" grammars.  A built-in grammar is automatically loaded into
 * the recognizer when the recognizer is created and are accessible
 * through the listRuleGrammars method.  Different recognizers
 * will have different built-in grammars so applications should not rely
 * upon built-in grammars if they need to be portable.
 * <p>
 * A DictationGrammar is the most important kind of built-in
 * grammar.  A dictation grammar supports relatively free-form input of
 * text.  If a recognizer supports dictation, then the
 * getDictationGrammar method returns non-null and the
 * isDictationGrammarSupported method of the
 * RecognizerModeDesc returns true.
 * <p>
 * Grammar Scope
 * <p>
 * Each Recognizer object has a separate name-space.  Applications should
 * be aware that changes to a Grammar object affect any part of the
 * application that uses that Grammar.  However, if two separate applications
 * create a Recognizer and use a Grammar with the same name, they
 * are effectively independent - changes by on app do not affect the
 * operation of the other app.
 * <p>
 * Efficiency
 * <p>
 * The processing of grammars (particularly large grammars) can be
 * computationally expensive.  Loading a new grammar
 * can take seconds or sometimes minutes.  Updates to a
 * Grammar may also be slow.  Therefore, applications should take
 * precautions to build grammars in advance and to modify them only
 * when necessary.  Furthermore, an application should
 * minimize the number of grammars active at any point in time.
 * <p>
 * Recognizer States
 * <p>
 * A Recognizer inherits the PAUSED and
 * RESUMED sub-states of the ALLOCATED
 * from the Engine interface.  The Recognizer
 * interface adds two more independent sub-states systems to
 * the ALLOCATED state:
 * <p>
 * LISTENING, PROCESSING, SUSPENDED
 * FOCUS_ON, FOCUS_OFF
 * <p>
 * <A/>
 * Recognizer States: Focus
 * <p>
 * A Recognizer adds the two state sub-state-system for
 * FOCUS_ON and FOCUS_OFF to indicate
 * whether this instance of the Recognizer currently
 * has the speech focus.  Focus is important in a multi-application
 * environment because more than one application can connect to
 * an underlying speech recognition engine, but the user gives
 * speech focus to only one application at a time.  Since it is
 * normal for an application to use only one Recognizer,
 * Recognizer focus and application focus normally
 * mean the same thing.  (Multi-recognizer apps cannot make this
 * assumption.)
 * <p>
 * Focus is not usually relevant in telephony applications in which
 * there is a single input audio stream to a single application.
 * <p>
 * The focus status is a key factor in determining the activation of
 * grammars and therefore in determining when results will and will
 * not be generated.  The activation conditions for grammars and the
 * role of focus are described in the documentation for the
 * <p>
 * <A href="Grammar.html">Grammar</A>
 * interface.
 * <p>
 * When recognizer focus is received a FOCUS_GAINED event
 * is issued to RecognizerListeners.  When recognizer
 * focus is released or otherwise lost, a FOCUS_LOST
 * event is issued to RecognizerListeners.
 * <p>
 * Applications can request speech focus for the Recognizer
 * by calling the requestFocus mechanism.  This
 * asynchronous method may return before focus in received.
 * To determine when focus is on, check for FOCUS_GAINED
 * events or test the FOCUS_ON bit of engine state.
 * <p>
 * Applications can release speech focus from the Recognizer
 * by calling the releaseFocus mechanism.  This
 * asynchronous method may return before focus in lost.
 * To determine whether focus is off, check for FOCUS_LOST
 * events or test the FOCUS_OFF bit of engine state.
 * <p>
 * In desktop environments, it is normal (but not a requirement) for
 * speech focus to follow window focus.  Therefore, it is common
 * for the requestFocus method to be called on
 * AWT events or Swing events such as FocusEvent and
 * WindowEvent.
 * <p>
 * A well-behaved application only requests focus when it knows that
 * it has the speech focus of the user (the user is talking to it
 * and not to other applications).  A well-behaved application will
 * also release focus as soon as it finishes with it.
 * <p>
 * Recognizer States: Result Recognition
 * <p>
 * A Recognizer adds the three state sub-state-system for
 * LISTENING, PROCESSING and
 * SUSPENDED to indicate the status of the
 * recognition of incoming speech.  These three states are loosely
 * coupled with the PAUSED and RESUMED states
 * but only to the extent that turning on and off audio input will
 * affect the recognition process.  An ALLOCATED recognizer
 * is always in one of these three states:
 * <p>
 * LISTENING state: the Recognizer
 * is listening to incoming audio for speech that may match an
 * active grammar but has not detected speech yet.
 * PROCESSING state: the Recognizer
 * is processing incoming speech that may match an active
 * grammar to produce a result.
 * SUSPENDED state: the Recognizer
 * is temporarily suspended while grammars are updated.
 * While suspended, audio input is buffered for processing
 * once the recognizer returns to the LISTENING
 * and PROCESSING states.
 * <p>
 * <A/>
 * Recognizer States: Result Recognition: Typical Event Cycle
 * <p>
 * The typical state cycle is as follows.
 * <p>
 * A Recognizer starts in the LISTENING
 * state with a certain set of grammars enabled.  When incoming
 * audio is detected that may match an active grammar, the
 * Recognizer transitions to the PROCESSING
 * state with a RECOGNIZER_PROCESSING RecognizerEvent.
 * The Recognizer then creates a new Result
 * and issues a RESULT_CREATED event to provide
 * it to the application.
 * <p>
 * The Recognizer remains in the PROCESSING
 * state until it completes recognition of the result.
 * <p>
 * The recognizer indicates completion of recognition by
 * issuing a RECOGNIZER_SUSPENDED
 * RecognizerEvent to transition from the
 * PROCESSING state to the SUSPENDED
 * state.  Once in that state, it issues a result finalization
 * event to ResultListeners (RESULT_ACCEPTED
 * or RESULT_REJECTED event).
 * <p>
 * The Recognizer remains in the SUSPENDED
 * state until processing of the result finalization event
 * is completed.  Applications will usually make any necessary
 * grammar changes while the recognizer is SUSPENDED.
 * In this state the Recognizer buffers incoming audio.
 * This buffering allows a user to continue speaking without speech
 * data being lost.  Once the Recognizer
 * returns to the LISTENING state the buffered audio
 * is processed to give the user the perception of real-time
 * processing.
 * <p>
 * The Recognizer
 * <A href="Grammar.html#commit">
 * commits all grammar changes</A>
 * , issues a CHANGES_COMMITTED
 * event to RecognizerListeners to return to the
 * LISTENING state.  It also issues GRAMMAR_CHANGES_COMMITTED
 * events to GrammarListeners of changed grammars.
 * The commit applies all grammar changes made at any point up to
 * the end of result finalization, typically including changes made in
 * the result finalization events.
 * <p>
 * The Recognizer is back in the LISTENING
 * state listening for speech that matches the new grammars.
 * <p>
 * In this state cycle, the RECOGNIZER_PROCESSING and
 * RECOGNIZER_SUSPENDED events are triggered by user
 * actions: starting and stopping speaking.  The third state
 * transition -- CHANGES_COMMITTED -- is triggered
 * programmatically some time after the RECOGNIZER_SUSPENDED
 * event.
 * <p>
 * <A/>
 * Recognizer States: Result Recognition: Non-Speech Events
 * <p>
 * For applications that deal only with spoken input the state
 * cycle above handles most normal speech interactions.  For
 * applications that handle other asynchronous input, additional state
 * transitions are possible.  Other types of asynchronous input
 * include graphical user interface events (e.g., AWTEvents),
 * timer events, multi-threading events, socket events and much more.
 * <p>
 * When a non-speech event occurs which changes the application
 * state or application data it is often necessary to update
 * the recognizer's grammars.  Furthermore, it is typically
 * necessary to do this as if the change occurred in real time
 * - at exactly the point in time at which the event occurred.
 * <p>
 * The suspend and commitChanges
 * methods of a Recognizer are used to handle
 * non-speech asynchronous events.  The typical cycle for
 * updating grammars is as follows:
 * <p>
 * Assume that the Recognizer is in the
 * LISTENING state (the user is not currently
 * speaking).  As soon as the event is received, the
 * application calls suspend to
 * indicate that it is about to change grammars.
 * <p>
 * The recognizer issues a RECOGNIZER_SUSPENDED
 * event and transitions from the LISTENING
 * state to the SUSPENDED state.
 * <p>
 * The application makes all necessary changes to the grammars.
 * <p>
 * Once all changes are completed the application calls
 * the commitChanges method.  The recognizer
 * applies the new grammars, issues a CHANGES_COMMITTED
 * event to transition from the SUSPENDED
 * state back to the LISTENING, and issues
 * GRAMMAR_CHANGES_COMMITTED events to all
 * changed grammars.
 * <p>
 * The Recognizer resumes recognition of the
 * buffered audio and then live audio with the new grammars.
 * <p>
 * Because audio is buffered from the time of the asynchronous
 * event to the time at which the CHANGES_COMMITTED
 * occurs, the audio is processed as if the new grammars
 * were applied exactly at the time of the asynchronous event.
 * The user has the perception of real-time processing.
 * <p>
 * Although audio is buffered in the SUSPENDED
 * state, applications should makes changes and commitChanges
 * as quickly as possible.  This minimizes the possibility of
 * a buffer overrun.  It also reduces delays in recognizing
 * speech and responding to the user.
 * <p>
 * Note: an application is not technically required to
 * call suspend prior to calling commitChanges.
 * If the suspend call is omitted the
 * Recognizer behaves as if suspend
 * had been called immediately prior to calling commitChanges.
 * An application that does not call suspend risks
 * a commit occurring unexpectedly and leaving grammars in
 * an inconsistent state.
 * <p>
 * <A/>
 * Recognizer States: Result Recognition: Mixing Speech and Non-Speech Events
 * <p>
 * There is no guarantee that a speech and non-speech events will
 * not be mixed.  If a speech event occurs in the absence of
 * non-speech events, the
 * <A href="#normalEvents">normal event
 * cycle</A>
 * takes place.
 * If a non-speech event occurs in the absence of any speech
 * events, the
 * <A href="#nonspeechEvents">non-speech event
 * cycle</A>
 * takes place.
 * <p>
 * We need to consider two cases in which speech and non-speech
 * events interact: (1) when a non-speech event occurs during
 * the processing of a speech event, and (2) when a speech event
 * occurs during the processing of a non-speech event.
 * <p>
 * Non-speech event occurs during processing of a speech event:
 * <p>
 * Technically, this is the case in which a non-speech event is
 * issued while the Recognizer is in either the
 * PROCESSING state or the SUSPENDED
 * state.  In both cases the event processing for the non-speech
 * is no different than normal.  The non-speech event handler
 * calls suspend to indicate it is about
 * to change grammars, makes the grammar changes, and then
 * calls commitChanges to apply the changes.
 * <p>
 * The effect is that the CHANGES_COMMITTED
 * event that would normally occur in the normal event cycle
 * may be delayed until the commitChanges method
 * is explicitly called and that the commit applies changes
 * made in response to both the speech and non-speech events.
 * If the commitChanges call for the non-speech
 * event is made before the end of the result finalization event,
 * there is no delay of the CHANGES_COMMITTED event.
 * <p>
 * Speech event occurs during processing of a non-speech event:
 * <p>
 * This case is simpler.  If the user starts speaking while a
 * non-speech event is being processed, then the Recognizer
 * is in the SUSPENDED state, that speech
 * is buffered, and the speech event is actually delayed until
 * the Recognizer returns to the LISTENING
 * state.  Once the Recognizer returns to the
 * LISTENING state, the incoming speech is
 * processed with the
 * <A href="normalEvents">normal event cycle</A>
 * .
 *
 * @see javax.speech.recognition.RecognizerEvent
 * @see javax.speech.EngineListener
 * @see javax.speech.recognition.RecognizerListener
 * @see javax.speech.recognition.Grammar
 * @see javax.speech.recognition.RuleGrammar
 * @see javax.speech.recognition.DictationGrammar
 * @see javax.speech.recognition.Result
 * @see javax.speech.recognition.FinalResult
 */
public interface Recognizer extends Engine {

    /**
     * LISTENING is the bit of state that is set when
     * an ALLOCATED Recognizer is listening
     * to incoming audio for speech that may match an active grammar
     * but has not yet detected speech.
     * <p>
     * A
     * <A href="RecognizerEvent.html#RECOGNIZER_PROCESSING">
     * RECOGNIZER_PROCESSING</A>
     * event is issued to
     * indicate a transition out of the LISTENING
     * state and into the PROCESSING state.
     * <p>
     * A
     * <A href="RecognizerEvent.html#RECOGNIZER_SUSPENDED">
     * RECOGNIZER_SUSPENDED</A>
     * event is issued to
     * indicate a transition out of the LISTENING
     * state and into the SUSPENDED state.
     * <p>
     * A
     * <A href="RecognizerEvent.html#CHANGES_COMMITTED">
     * CHANGES_COMMITTED</A>
     * event is issued to
     * indicate a transition into the LISTENING
     * state from the SUSPENDED state.
     *
     * @see javax.speech.Engine#ALLOCATED
     * @see javax.speech.recognition.Recognizer#PROCESSING
     * @see javax.speech.recognition.Recognizer#SUSPENDED
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_PROCESSING
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_SUSPENDED
     * @see javax.speech.recognition.RecognizerEvent#CHANGES_COMMITTED
     */
    long LISTENING = 4294967296L;

    /**
     * PROCESSING is the bit of state that is set when
     * an ALLOCATED Recognizer is producing
     * a Result for incoming speech that may match an
     * active grammar.
     * <p>
     * A
     * <A href="RecognizerEvent.html#RECOGNIZER_SUSPENDED">
     * RECOGNIZER_SUSPENDED</A>
     * event is issued to
     * indicate a transition out of the PROCESSING
     * state and into the SUSPENDED state
     * when recognition of a Result is completed.
     * <p>
     * A
     * <A href="RecognizerEvent.html#RECOGNIZER_PROCESSING">
     * RECOGNIZER_PROCESSING</A>
     * event is issued to
     * indicate a transition into the PROCESSING
     * state from the LISTENING state when
     * the start of a new result is detected.
     *
     * @see javax.speech.Engine#ALLOCATED
     * @see javax.speech.recognition.Recognizer#PROCESSING
     * @see javax.speech.recognition.Recognizer#SUSPENDED
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_PROCESSING
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_SUSPENDED
     */
    long PROCESSING = 8589934592L;

    /**
     * SUSPENDED is the bit of state that is set
     * when an ALLOCATED Recognizer is
     * temporarily suspended while grammar definition and grammar
     * enabled settings are updated.
     * The Recognizer enters this state whenever
     * recognition of a Result is finalized, and
     * in response to a call to suspend.
     * <p>
     * The primary difference between the SUSPENDED
     * and PAUSED states of a Recognizer is
     * that audio input is buffered in the SUSPENDED
     * state.  By contrast, the PAUSED state indicates
     * that audio input to the Recognizer is being ignored.
     * In addition, the SUSPENDED state is
     * a temporary state, whereas a Recognizer
     * can stay in the PAUSED state indefinitely.
     * <p>
     * A
     * <A href="RecognizerEvent.html#CHANGES_COMMITTED">
     * CHANGES_COMMITTED</A>
     * event is issued to
     * indicate a transition out of the SUSPENDED
     * state and into the LISTENING state when
     * all changes to grammars are committed to the recognition process.
     * <p>
     * A
     * <A href="RecognizerEvent.html#RECOGNIZER_SUSPENDED">
     * RECOGNIZER_SUSPENDED</A>
     * event is issued to
     * indicate a transition into the SUSPENDED
     * state from either the LISTENING state or
     * PROCESSING state.
     *
     * @see javax.speech.Engine#ALLOCATED
     * @see javax.speech.recognition.Recognizer#PROCESSING
     * @see javax.speech.recognition.Recognizer#SUSPENDED
     * @see javax.speech.Engine#PAUSED
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.recognition.RecognizerEvent#CHANGES_COMMITTED
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_SUSPENDED
     */
    long SUSPENDED = 17179869184L;

    /**
     * FOCUS_ON is the bit of state that is set when
     * an ALLOCATED Recognizer has the
     * speech focus of the underlying speech recognition engine.
     * <p>
     * As recognizer focus is gained and lost, a
     * FOCUS_GAINED or FOCUS_LOST
     * event is issued to indicate the state change.  The
     * requestFocus and releaseFocus
     * methods allow management of speech focus.
     *
     * @see javax.speech.Engine#ALLOCATED
     * @see javax.speech.recognition.Recognizer#FOCUS_OFF
     * @see javax.speech.recognition.Recognizer#requestFocus()
     * @see javax.speech.recognition.Recognizer#releaseFocus()
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_GAINED
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_LOST
     * @see javax.speech.Engine#getEngineState()
     */
    long FOCUS_ON = 281474976710656L;

    /**
     * FOCUS_OFF is the bit of state that is set when
     * an ALLOCATED Recognizer does not
     * have the speech focus of the underlying speech recognition engine.
     * <p>
     * As recognizer focus is gained and lost, a
     * FOCUS_GAINED or FOCUS_LOST
     * event is issued to indicate the state change.  The
     * requestFocus and releaseFocus
     * methods allow management of speech focus.
     *
     * @see javax.speech.Engine#ALLOCATED
     * @see javax.speech.recognition.Recognizer#FOCUS_ON
     * @see javax.speech.recognition.Recognizer#requestFocus()
     * @see javax.speech.recognition.Recognizer#releaseFocus()
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_GAINED
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_LOST
     * @see javax.speech.Engine#getEngineState()
     */
    long FOCUS_OFF = 562949953421312L;

    /**
     * Request notifications of all events for all Result
     * produced by this Recognizer.  An application
     * can attach multiple ResultListeners to a
     * Recognizer.
     * A listener is removed with the removeResultListener method.
     * <p>
     * ResultListeners attached to a Recognizer
     * are the only ResultListeners to receive the
     * RESULT_CREATED event and all subsequent events.
     * <p>
     * ResultListener objects can also be attached to
     * any Grammar or to any Result.
     * A listener attached to the Grammar receives
     * all events that match that Grammar following a
     * GRAMMAR_FINALIZED event.
     * A listener attached to a Result receives
     * all events for that result from the time at which the listener
     * is attached.
     * <p>
     * A ResultListener can be attached or removed in any
     * Engine state.
     *
     * @see javax.speech.recognition.Recognizer#removeResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Grammar#addResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Result#addResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.ResultEvent#RESULT_CREATED
     * @see javax.speech.recognition.ResultEvent#GRAMMAR_FINALIZED
     */
    void addResultListener(ResultListener listener);

    /**
     * Commit changes to all loaded grammars and all changes
     * of grammar enabling since the last commitChanges.
     * Because all changes are applied atomically (all at once)
     * the application does not need to be concerned with intermediate
     * states as it changes grammar definitions and enabling.
     * <p>
     * The commitChanges call first checks that all the loaded
     * grammars are legal.  If there are any problems with the
     * current definition of any RuleGrammar an
     * exception is thrown.  Problems might include undefined
     * rule name, illegal recursion and so on (see the
     * <p>
     * <A href="http://java.sun.com/products/java-media/speech/forDevelopers/JSGF/index.html">
     * Java Speech Grammar Format Specification</A>
     * and the
     * <p>
     * <A href="GrammarSyntaxDetail.html">GrammarSyntaxDetail</A>
     * class documentation for details).
     * <p>
     * The commitChanges call is asynchronous (the changes
     * have not necessarily been committed when the call returns).
     * When the changes have been committed, a CHANGES_COMMITTED
     * event is issued to all RecognizerListeners and to the
     * GrammarListeners of all changed Grammars.
     * <p>
     * Immediately following the CHANGES_COMMITTED event,
     * a GRAMMAR_CHANGES_COMMITTED GrammarEvent
     * is issued to the GrammarListeners of all changed grammars.
     * <p>
     * The roll of commitChanges in
     * <A href="Grammar.html#commit">
     * applying grammar changes</A>
     * in described in the documentation for the
     * <p>
     * <A href="Grammar.html">Grammar</A>
     * interface.
     * The effect of the commitChanges method upon
     * Recognizer states is
     * <A href="#nonspeechEvents">
     * described above</A>
     * .  The use of suspend
     * with commitChanges and their use for processing
     * <p>
     * <A href="#nonspeechEvents">asynchronous non-speech events</A>
     * are also described above.
     * <p>
     * It is not an error to commitChanges when no
     * grammars have been changed.  However, the Recognizer
     * performs state transitions in the same way as when grammars
     * are changed.
     * <p>
     * The commitChanges method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @throws javax.speech.recognition.GrammarException if the loaded grammars contain any logical errors
     * @throws javax.speech.EngineStateError             if called for a Recognizer in the DEALLOCATED or
     *                                                   DEALLOCATING_RESOURCES states
     * @see javax.speech.recognition.Recognizer#LISTENING
     * @see javax.speech.recognition.Recognizer#PROCESSING
     * @see javax.speech.recognition.Recognizer#SUSPENDED
     * @see javax.speech.recognition.Recognizer#suspend()
     * @see javax.speech.recognition.RecognizerEvent#CHANGES_COMMITTED
     */
    void commitChanges() throws GrammarException, EngineStateError;

    /**
     * Delete a RuleGrammar from the Recognizer.
     * The grammar deletion only takes effect when all
     * <p>
     * <A href="Grammar.html">grammar changes are committed</A>
     * .
     * <p>
     * Recognizers may chose to ignore the deletion of built-in grammars.
     * <p>
     * The deleteRuleGrammar method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @throws java.lang.IllegalArgumentException if the Grammar is not known to the Recognizer
     * @throws javax.speech.EngineStateError      if called for a Recognizer in the DEALLOCATED or
     *                                            DEALLOCATING_RESOURCES states
     * @see javax.speech.recognition.Recognizer#commitChanges()
     * @see javax.speech.recognition.RecognizerEvent#CHANGES_COMMITTED
     */
    void deleteRuleGrammar(RuleGrammar grammar) throws IllegalArgumentException, EngineStateError;

    /**
     * If the Recognizer is in the PROCESSING state
     * (producing a Result), force the Recognizer
     * to immediately complete processing of that result by finalizing it.
     * It is acceptable behavior for a Recognizer to
     * automatically reject the current result.
     * <p>
     * The flush flag indicates whether the recognizer's
     * internally buffered audio should be processed before forcing the
     * finalize.  Applications needing immediate cessation of recognition
     * should request a flush.  If the force finalize is a response to
     * a user event (e.g. keyboard or mouse press) then the buffer is
     * typically not flushed because incoming speech from a user could be lost.
     * <p>
     * The state behavior of the Recognizer is the
     * same as if the Result had been finalized because
     * of end-of-utterance.  The Recognizer will transition
     * to the SUSPENDED state.
     * <p>
     * The forceFinalize method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @throws javax.speech.EngineStateError if called for a Recognizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     */
    void forceFinalize(boolean flush) throws EngineStateError;

    /**
     * Return the DictationGrammar for a Recognizer.
     * Typically, the name parameter is null to
     * get access to the default DictationGrammar for the
     * Recognizer.
     * <p>
     * If the Recognizer does not support dictation, or
     * if it does have a DictationGrammar with the specified
     * name, then the method returns null.
     * <p>
     * An application can determine whether the Recognizer
     * supports dictation by calling the isDictationGrammarSupported
     * method of the RecognizerModeDesc.
     * <p>
     * Note: the name parameter is provided for future extension
     * of the API to allow more than one DictationGrammar to
     * be defined.
     * <p>
     * The getDictationGrammar method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @throws javax.speech.EngineStateError if called for a Recognizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.recognition.RecognizerModeDesc#isDictationGrammarSupported()
     */
    DictationGrammar getDictationGrammar(String name) throws EngineStateError;

    /**
     * Return the RecognizerProperties object (a JavaBean).
     * The method returns exactly the same object as the
     * getEngineProperties method in the Engine
     * interface.  However, with the getRecognizerProperties
     * method, an application does not need to cast the return value.
     * <p>
     * The RecognizerProperties are available in any state of
     * an Engine. However, changes only take effect once an
     * engine reaches the ALLOCATED state.
     *
     * @return the RecognizerProperties object for this engine
     * @see javax.speech.Engine#getEngineProperties()
     */
    RecognizerProperties getRecognizerProperties();

    /**
     * Get the RuleGrammar with the specified name.
     * Returns null if the
     * grammar is not known to the Recognizer.
     * <p>
     * The getRuleGrammar method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @param name the name of the grammar to be returned
     * @return a RuleGrammar reference or null
     * @throws javax.speech.EngineStateError if called for a Recognizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     */
    RuleGrammar getRuleGrammar(String name) throws EngineStateError;

    /**
     * Return an object which provides management of the speakers
     * of a Recognizer.  Returns null if the
     * Recognizer does not store speaker data - that is,
     * if it is a speaker-independent recognizer in which
     * all speakers are handled the same.
     * <p>
     * A getSpeakerManager returns successfully in any
     * state of a Recognizer.  The
     * SpeakerManager methods that list speakers and set
     * the current speaker operate in any Recognizer state
     * but only take effect in the ALLOCATED state.
     * This allows an application can set the initial speaker
     * prior to allocating the engine.  Other methods of the
     * SpeakerManager only operate in the
     * ALLOCATED state.
     *
     * @return the SpeakerManager for this Recognizer
     * @throws java.lang.SecurityException if the application does not have accessSpeakerProfiles permission
     */
    SpeakerManager getSpeakerManager() throws SecurityException;

    /**
     * List the RuleGrammars known to the Recognizer.
     * Returns null if there are no grammars.
     * <p>
     * The listRuleGrammars method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @throws javax.speech.EngineStateError if called for a Recognizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     */
    RuleGrammar[] listRuleGrammars() throws EngineStateError;

    /**
     * Create a RuleGrammar from Java Speech Grammar Format text
     * provided by the Reader.  If the grammar contained
     * in the Reader already exists, it is over-written.
     * The new grammar is used for recognition only after
     * <p>
     * <A href="Grammar.html">changes are committed</A>
     * .
     * <p>
     * It is often useful to load JSGF from a String
     * by creating a StringReader object with that string.
     * <p>
     * The caller is responsible for determining the character encoding
     * of the JSGF document.  The character encoding information contained
     * in the JSGF header is ignored.
     * <p>
     * The loadJSGF methods operate as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @param JSGF the Reader from which the grammar text is loaded
     * @throws javax.speech.recognition.GrammarException if the JSGF text contains any errors
     * @throws java.io.IOException                       if an I/O error occurs
     * @throws javax.speech.EngineStateError             if called for a Recognizer in the DEALLOCATED or
     *                                                   DEALLOCATING_RESOURCES states
     * @see javax.speech.recognition.Recognizer#loadJSGF(java.net.URL, java.lang.String)
     * @see javax.speech.recognition.Recognizer#loadJSGF(java.net.URL, java.lang.String
     * @see boolean
     * @see boolean
     * @see java.util.Vector)
     */
    RuleGrammar loadJSGF(Reader JSGF) throws GrammarException, IOException, EngineStateError;

    /**
     * Load a RuleGrammar and its imported grammars
     * from Java Speech Grammar Format text from URLs or from system
     * resources.  The loaded grammars are used for recognition only after
     * <p>
     * <A href="Grammar.html">changes are committed</A>
     * .
     * <p>
     * The method returns a reference to the named RuleGrammar.
     * The method never returns null since an exception is
     * thrown if grammarName cannot be loaded successfully.
     * <p>
     * The method attempts to load all imports of grammarName,
     * all imports of the imported grammars, and so on.  This recursive
     * load stops when grammars are reached that have already been
     * loaded or when no more imports are found.  The intent is to
     * ensure that every grammar needed to use the named grammar is loaded.
     * <p>
     * For example, if we load grammar X, which imports grammar Y, which
     * imports grammars A and B, then all four grammars are loaded.
     * If any of the grammars are already loaded, then it and its
     * imports are not reloaded.
     * <p>
     * JSGF allows fully-qualified rulename references without
     * a corresponding import statement.  This method also attempts to load
     * all grammars referenced by a fully-qualified rulename not already
     * referenced and loaded by a corresponding import statement.
     * <p>
     * The advanced
     * <p>
     * <A href="#loadJSGF(java.net.URL, java.lang.String, boolean, boolean, java.util.Vector)">
     * loadJSGF</A>
     * method provides more control of the loading process.
     * This method is equivalent to:
     * <p>
     * loadJSGF(url, name, true, false, null);
     * <p>
     * (load imports, don't reload existing grammars, don't provide
     * a list of loaded grammars.)
     * <p>
     * Locating Grammars
     * <p>
     * The context URL parameter is used as the first
     * parameter of the URL(URL, String) constructor in
     * the java.net package.
     * <p>
     * The grammarName is converted to a grammar filename.
     * The conversion changes each each period character ('.') to a
     * file separator ('/').  The ".gram" suffix is appended to identify
     * the grammar file.  The grammarName "com.sun.numbers"
     * becomes the filename "com/sun/numbers.gram".  This filename is
     * used as the spec parameter in the URL constructor.
     * <p>
     * For example,
     * <p>
     * loadJSGF(new URL("http://www.sun.com/"), "com.sun.numbers")
     * <p>
     * will look for the grammar in the URL "http://www.sun.com/com/sun/numbers.gram"
     * and for its imports in a similar location.
     * <p>
     * If the derived URL does not exist, loadJSGF checks
     * for a system resource with the grammar file using the
     * ClassLoader.getSystemResource method.  This allows
     * grammars to be included in the CLASSPATH.
     * <p>
     * If the context is null, the grammars are
     * searched for only as a system resource.
     * <p>
     * Reading Grammars
     * <p>
     * For each grammar it loads, the recognizer determines the file's
     * character encoding by reading the JSGF header.
     * <p>
     * An exception is thrown if (1) any JSGF syntax problems are found,
     * (2) if a grammar found in a URL does not match the expected name,
     * or (3) if a grammar cannot be located as either a URL or system resource.
     * <p>
     * If an exception is thrown part way through loading a set of grammars
     * the list of loaded grammars not explicitly available.  The recognizer
     * does not attempt to remove any partially loaded grammars.
     * <p>
     * The loadJSGF methods operate as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @param base        the URL context from which grammar locations are derived or
     *                    null to load exclusively from system resources
     * @param grammarName the name of the grammar to be loaded
     * @return a reference to grammarName
     * @throws javax.speech.recognition.GrammarException if any loaded JSGF text contains an error
     * @throws java.net.MalformedURLException            if problem encountered creating a URL
     * @throws java.io.IOException                       if an I/O error occurs
     * @throws javax.speech.EngineStateError             if called for a Recognizer in the DEALLOCATED or
     *                                                   DEALLOCATING_RESOURCES states
     * @see javax.speech.recognition.Recognizer#loadJSGF(java.io.Reader)
     * @see javax.speech.recognition.Recognizer#loadJSGF(java.net.URL, java.lang.String, boolean, boolean, java.util.Vector)
     * @see java.net.URL#URL(java.lang.String)
     */
    RuleGrammar loadJSGF(URL base, String grammarName) throws GrammarException, MalformedURLException, IOException, EngineStateError;

    /**
     * Load a RuleGrammar in Java Speech Grammar Format text
     * from a URL or from system resources and optionally load its imports.
     * This method provide an additional control over whether grammars
     * are reloaded even if they have already been loaded, and allows
     * caller to receive a list of all grammars loaded by the Recognizer.
     * The loaded grammars are used for recognition only after
     * <p>
     * <A href="Grammar.html">changes are committed</A>
     * .
     * <p>
     * The three additional parameters of this method provide the following
     * extensions over the
     * <p>
     * <A href="#loadJSGF(java.net.URL, java.lang.String)">loadJSGF(URL, String)</A>
     * method:
     * <p>
     * loadImports: if false, the method
     * only loads the RuleGrammar specified by the
     * name parameter.
     * If true, the method behaves like the
     * <p>
     * <A href="#loadJSGF(java.net.URL, java.lang.String)">loadJSGF(URL, String)</A>
     * method and recursively loads imported grammars.
     * reloadGrammars: if true, the method
     * always loads a RuleGrammar, even if it is
     * already loaded into the Recognizer.  The previous
     * version of the grammar is overwritten.
     * <p>
     * If false, the method behaves like the
     * <p>
     * <A href="#loadJSGF(java.net.URL, java.lang.String)">loadJSGF(URL, String)</A>
     * method and does not load grammars that are already loaded, or
     * their imports.
     * loadedGrammars: if non-null, then as the
     * Recognizer loads any grammar it appends a
     * reference to that RuleGrammar to the Vector.
     *
     * @param base           the URL context from which grammar locations are derived or
     *                       null to load exclusively from system resources
     * @param grammarName    the name of the grammar to be loaded
     * @param loadImports    if true, grammars imported by grammarName
     *                       are loaded plus their imports
     * @param reloadGrammars if true reload all grammars and imports, if false
     *                       do not load grammars already loaded into the Recognizer
     * @param loadedGrammars if non-null a reference to each loaded RuleGrammar
     *                       is appended as it is loaded
     * @return a reference to grammarName
     * @throws javax.speech.recognition.GrammarException if any loaded JSGF text contains an error
     * @throws java.net.MalformedURLException            if problem encountered creating a URL
     * @throws java.io.IOException                       if an I/O error occurs
     * @throws javax.speech.EngineStateError             if called for a Recognizer in the DEALLOCATED or
     *                                                   DEALLOCATING_RESOURCES states
     * @see javax.speech.recognition.Recognizer#loadJSGF(java.io.Reader)
     * @see javax.speech.recognition.Recognizer#loadJSGF(java.net.URL, java.lang.String)
     */
    RuleGrammar loadJSGF(URL base, String grammarName, boolean loadImports, boolean reloadGrammars, Vector<?> loadedGrammars) throws GrammarException, MalformedURLException, IOException, EngineStateError;

    /**
     * Create a new RuleGrammar for this recognizer with
     * a specified grammar name.  The new grammar is used for
     * recognition only after
     * <p>
     * <A href="Grammar.html">changes are committed</A>
     * .
     * <p>
     * The newRuleGrammar method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @param name name of the grammar to be created
     * @throws java.lang.IllegalArgumentException if grammar with name already exists
     * @throws javax.speech.EngineStateError      if called for a Recognizer in the DEALLOCATED or
     *                                            DEALLOCATING_RESOURCES states
     */
    RuleGrammar newRuleGrammar(String name) throws IllegalArgumentException, EngineStateError;

    /**
     * Create a new grammar by reading in a grammar stored in a
     * vendor-specific format.  The data could have been stored
     * using the writeVendorGrammar method.  The documentation for
     * the writeVendorGrammar method describes the use of vendor grammar formats.
     * <p>
     * If a grammar of the same name already exists, it is over-written.
     * <p>
     * The readVendorGrammar method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @param input InputStream from which grammar is loaded
     * @return reference to the loaded grammar
     * @throws javax.speech.VendorDataException if the input data format is not known to the Recognizer
     * @throws java.io.IOException              if an I/O error occurs.
     * @throws javax.speech.EngineStateError    if called for a Recognizer in the DEALLOCATED or
     *                                          DEALLOCATING_RESOURCES states
     */
    Grammar readVendorGrammar(InputStream input) throws VendorDataException, IOException, EngineStateError;

    /**
     * Read a Result object from a stream in a vendor-specific format.
     * The return value will include the best-guess tokens, and may
     * include N-best results, audio data, timing data, training information and
     * other data the is optionally provided with a finalized Result.
     * <p>
     * The call returns null upon failure.
     * Because result objects are stored in a vendor-specific format
     * they cannot normally be loaded by incompatible recognizers.
     * <p>
     * The readVendorResult method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @param input InputStream from which Result is loaded
     * @return reference to the loaded result
     * @throws javax.speech.VendorDataException if the input data format is not known to the Recognizer
     * @throws java.io.IOException              if an I/O error occurs.
     * @throws javax.speech.EngineStateError    if called for a Recognizer in the DEALLOCATED or
     *                                          DEALLOCATING_RESOURCES states
     */
    Result readVendorResult(InputStream input) throws VendorDataException, IOException, EngineStateError;

    /**
     * Release the speech focus from this Recognizer.
     * A FOCUS_LOST event is issued to
     * RecognizerListeners once the focus
     * is released and the Recognizer state changes
     * from FOCUS_OFF to FOCUS_ON.
     * <p>
     * Since one one application may have recognition focus at
     * any time, applications should release focus whenever it
     * is not required.
     * <p>
     * <A href="#focus">Speech focus</A>
     * and other focus issues
     * are discussed above in more detail.
     * <p>
     * It is not an error for an application to release focus
     * for a Recognizer that does not have speech focus.
     * <p>
     * Focus is implicitly released when a Recognizer
     * is deallocated.
     * <p>
     * The releaseFocus method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @throws javax.speech.EngineStateError if called for a Recognizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.recognition.Recognizer#requestFocus()
     * @see javax.speech.recognition.Recognizer#FOCUS_OFF
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_LOST
     * @see javax.speech.Engine#getEngineState()
     */
    void releaseFocus() throws EngineStateError;

    /**
     * Remove a ResultListener from this Recognizer.
     * <p>
     * A ResultListener can be attached or removed in any
     * Engine state.
     *
     * @see javax.speech.recognition.Recognizer#addResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Result#removeResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Grammar#removeResultListener(javax.speech.recognition.ResultListener)
     */
    void removeResultListener(ResultListener listener);

    /**
     * Request the speech focus for this Recognizer from
     * the underlying speech recognition engine.  When the focus
     * is received, a FOCUS_GAINED event is issued
     * to RecognizerListeners and the Recognizer
     * changes state from FOCUS_ON to FOCUS_OFF.
     * <p>
     * Since one one application may have recognition focus  at
     * any time, applications should only request focus when
     * confident that the user is speaking to that application.
     * <p>
     * <A href="#focus">Speech focus</A>
     * and other focus issues
     * are discussed above in more detail.
     * <p>
     * It is not an error for an application to request focus
     * for a Recognizer that already has speech focus.
     * <p>
     * The requestFocus method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @throws javax.speech.EngineStateError if called for a Recognizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.recognition.Recognizer#releaseFocus()
     * @see javax.speech.recognition.Recognizer#FOCUS_ON
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_GAINED
     * @see javax.speech.Engine#getEngineState()
     */
    void requestFocus() throws EngineStateError;

    /**
     * Temporarily suspend recognition while the application updates
     * grammars prior to a commitChanges call.  The
     * suspend call places the Recognizer
     * in the SUSPENDED state. While in that state
     * the incoming audio is buffered.  The buffered audio is processed
     * after the recognizer has committed grammar changes and returned to
     * the LISTENING state.
     * <p>
     * The primary difference between the suspend and
     * pause methods is that audio is buffered while
     * a Recognizer is suspended whereas incoming audio is ignored
     * while in the PAUSED state.  Also, the SUSPENDED
     * state should only be visited temporarily, whereas a
     * Recognizer can be PAUSED indefinitely.
     * <p>
     * The suspend method is asynchronous.  When
     * the call returns, the recognizer is not necessarily suspended.
     * The getEngineState method, or the
     * RECOGNIZER_SUSPENDED events can be used to determine
     * when the recognizer is actually suspended.
     * <p>
     * The use of suspend with commitChanges
     * for handling atomic grammar changes and for handling
     * asynchronous events are
     * <A href="#nonspeechEvents">described above</A>
     * .
     * <p>
     * Calls to suspend and commitChanges
     * do not nest.  A single call to commitChanges
     * can release the Recognizer after multiple
     * calls to suspend.
     * <p>
     * The suspend method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @throws javax.speech.EngineStateError if called for a Recognizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     * @see javax.speech.recognition.Recognizer#commitChanges()
     * @see javax.speech.recognition.Recognizer#SUSPENDED
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_SUSPENDED
     */
    void suspend() throws EngineStateError;

    /**
     * Store a grammar in a vendor-specific format.  The data can be
     * re-loaded at a future time by the readVendorGrammar method.
     * <p>
     * The output format will be specific to the type of recognizer that
     * writes it.  For example, data written by an Acme Command and Control
     * Recognizer will be readable only by another Acme Command and Control
     * Recognizer or by other recognizers that understand it's format.
     * When portability is required, use the Java Speech Grammar Format.
     * <p>
     * Why is a Vendor grammar format useful?  The recognizer can store
     * information that makes reloading of the grammar faster than
     * when JSGF is used.  The recognizer may also store additional
     * information (e.g. pronunciation, statistical and acoustic data)
     * that improve recognition using this grammar.
     * <p>
     * The writeVendorGrammar method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @param output  OutputStream where grammar is written
     * @param grammar Grammar to be written
     * @throws java.io.IOException           if an I/O error occurs.
     * @throws javax.speech.EngineStateError if called for a Recognizer in the DEALLOCATED or
     *                                       DEALLOCATING_RESOURCES states
     */
    void writeVendorGrammar(OutputStream output, Grammar grammar) throws IOException, EngineStateError;

    /**
     * Store a finalized Result object in a vendor-specific format so
     * that it can be re-loaded in a future session.  All the current
     * information associated with the result is stored.  If the application
     * will not need audio data or training information in a future session,
     * they should release that information (through the FinalResult
     * interface) before writing the result to reduce storage requirements.
     * <p>
     * The writeVendorGrammar method operates as defined when
     * the Recognizer is in the ALLOCATED state.
     * The call blocks if the Recognizer in the
     * ALLOCATING_RESOURCES state and completes when the engine
     * reaches the ALLOCATED state.  An error is thrown
     * if the Recognizer is in the DEALLOCATED or
     * DEALLOCATING_RESOURCES state.
     *
     * @param output OutputStream where the result is written
     * @param result Result to be written
     * @throws javax.speech.recognition.ResultStateError if the Result is not in a finalized state
     * @throws java.io.IOException                       if an I/O error occurs.
     * @throws javax.speech.EngineStateError             if called for a Recognizer in the DEALLOCATED or
     *                                                   DEALLOCATING_RESOURCES states
     */
    void writeVendorResult(OutputStream output, Result result) throws IOException, ResultStateError, EngineStateError;
}
