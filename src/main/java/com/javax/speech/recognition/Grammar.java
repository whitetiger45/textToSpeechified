package javax.speech.recognition;

/**
 * Parent interface supported by all recognition grammars
 * including DictationGrammar and RuleGrammar.
 * A Grammar defines a set of tokens (words) that may be
 * spoken and the patterns in which those tokens may be spoken.
 * Different grammar types (dictation vs. rule) define the words and the
 * patterns in different ways.
 * <p>
 * The core functionality provided through the Grammar interface
 * includes:
 * <p>
 * Naming: each grammar of a Recognizer has a unique name.
 * Enabling: grammars may be enabled or disabled for activation of recognition.
 * Activation: the activation mode can be set and the activation state tested.
 * Adding and removing GrammarListeners for grammar-related events.
 * Adding and removing ResultListeners to receive result events
 * for results matching the Grammar.
 * Access the Recognizer that owns the grammar
 * <p>
 * Each Grammar is associated with a specific Recognizer.
 * All grammars are located, created and managed through the Recognizer
 * interface.  The basic steps in using any Grammar are:
 * <p>
 * Create a new Grammar or get a reference to an existing grammar
 * through the Recognizer interface.
 * Attach a ResultListener to get results.
 * As necessary, setup or modify the grammar for the application's context.
 * Enabled and disable recognition of the Grammar as required
 * by the application's context.
 * Commit grammar changes and enabled state into the recognition process.
 * Repeat the update, enable and commit steps as required.
 * For application-created grammars,
 * delete the Grammar when it is no longer needed.
 * <p>
 * Grammar Types
 * <p>
 * There are two types of Grammar:
 * <p>
 * <A href="DictationGrammar.html">DictationGrammar</A>
 * and
 * <A href="RuleGrammar.html">RuleGrammar</A>
 * Both are defined by interfaces that extend the Grammar
 * interface. All recognizers support RuleGrammars.
 * A recognizer may optionally support a DictationGrammar.
 * <p>
 * The RuleGrammar and DictationGrammar
 * interfaces define specialized mechanisms for controlling and modifying
 * those types of grammar.
 * <p>
 * <A/>
 * Grammar Activation
 * <p>
 * When a Grammar is active, the Recognizer
 * listens to incoming audio for speech that matches that Grammar.
 * To be activated, an application must first set the enabled
 * property to true (enable activation) and set the
 * activationMode property to indicate the
 * activation conditions.
 * <p>
 * There are three activation modes: RECOGNIZER_FOCUS,
 * RECOGNIZER_MODAL and GLOBAL.  For each
 * mode a certain set of activation conditions must be met for the
 * grammar to be activated for recognition.  The activation
 * conditions are determined by Recognizer focus and
 * possibly by the activation of other grammars.  Recognizer
 * focus is managed with the
 * <p>
 * <A href="Recognizer.html#requestFocus">requestFocus</A>
 * and
 * <p>
 * <A href="Recognizer.html#releaseFocus">releaseFocus</A>
 * <p>
 * methods of a Recognizer.
 * <p>
 * The modes are listed here by priority.  Always use the lowest
 * priority mode possible.
 * <p>
 * GLOBAL activation mode: if enabled,
 * the Grammar is always active irrespective of
 * whether the Recognizer of this application
 * has focus.
 * <p>
 * RECOGNIZER_MODAL activation mode: if enabled,
 * the Grammar is always active when the
 * Recognizer of this application has focus.
 * Furthermore, enabling a modal grammar deactivates
 * any grammars in the same Recognizer with the
 * RECOGNIZER_FOCUS activation mode.
 * <p>
 * RECOGNIZER_FOCUS activation mode (default):
 * if enabled, the Grammar is active when
 * the Recognizer of this application has focus.  The
 * exception is that if any other grammar of this application is
 * enabled with RECOGNIZER_MODAL activation mode, then
 * this grammar is not activated.
 * <p>
 * The current activation state of a grammar can be tested with the
 * isActive method.  Whenever a grammar's activation changes
 * either a GRAMMAR_ACTIVATED or
 * GRAMMAR_DEACTIVATED event is issued to each
 * attached GrammarListener.
 * <p>
 * An application may have zero, one or many grammar enabled at
 * any time.  As the conventions below indicate, well-behaved
 * applications always minimize the number of active grammars.
 * <p>
 * The activation and deactivation of grammars is independent of
 * PAUSED and RESUMED states of the
 * Recognizer.  However, when a Recognizer
 * is PAUSED, audio input to the Recognizer
 * is turned off, so speech can't be detected.  Note that just after
 * pausing a recognizer there may be some remaining audio in the input
 * buffer that could contain recognizable speech and thus an active
 * grammar may continue to receive result events.
 * <p>
 * Well-behaved applications adhere to the following conventions
 * to minimize impact on other applications and other components
 * of the same application:
 * <p>
 * Never apply the GLOBAL activation mode to
 * a DictationGrammar.
 * Always use the default activation mode RECOGNIZER_FOCUS
 * unless there is a good reason to use another mode.
 * Only use the RECOGNIZER_MODAL when certain
 * that deactivating RECOGNIZER_FOCUS grammars
 * will not adversely affect the user interface.
 * Minimize the complexity and the number of RuleGrammars
 * with GLOBAL activation mode.
 * Only enable a grammar when it is appropriate for a
 * user to say something matching that grammar.  Otherwise
 * disable the grammar to improve recognition response time
 * and recognition accuracy for other grammars.
 * Only request focus when confident that the user's speech focus
 * (attention) is directed to grammars of your application.
 * Release focus when it is not required.
 * <p>
 * The general principle underlying these conventions is that
 * increasing the number of active grammars and/or increasing the
 * complexity of those grammars can lead to slower response time, greater CPU
 * load and reduced recognition accuracy (more mistakes).
 * <p>
 * <A/>
 * Committing Changes
 * <p>
 * Grammars can be dynamically changed and enabled and disabled.
 * Changes may be necessary as the application changes context,
 * as new information becomes available and so on.  As with graphical
 * interface programming most grammar updates occur during processing
 * of events.  Very often grammars are updated in response to
 * speech input from a user (a ResultEvent).
 * Other asynchronous events (e.g., AWTEvents)
 * are another common trigger of grammar changes.  Changing
 * grammars during normal operation of a recognizer is usually
 * necessary to ensure natural and usable speech-enabled applications.
 * <p>
 * Different grammar types allow different types of run-time change.
 * RuleGrammars can be created and deleted
 * during normal recognizer operation.  Also, any aspect of a created
 * RuleGrammar can be redefined: rules can be modified,
 * deleted or added, imports can be changed an so on.   Certain
 * properties of a DictationGrammar can be changed:
 * the context can be updated and the vocabulary modified.
 * <p>
 * For any grammar changes to take effect they must be committed.
 * Committing changes builds the grammars into a format that can be used
 * by the internal processes of the recognizer and applies those changes to
 * the processing of incoming audio.
 * <p>
 * There are two ways in which a commit takes place:
 * <p>
 * An explicit call by an application to the
 * <p>
 * <A href="Recognizer.html#commitChanges()">commitChanges</A>
 * method of the Recognizer.  The documentation for the
 * method describes when and how changes are committed when called.
 * (The commitChanges method is typically used in
 * conjunction with the suspend method of
 * Recognizer.)
 * <p>
 * Changes to all grammars are implicitly committed at the completion
 * of processing of a result finalization event (either a
 * RESULT_ACCEPTED or RESULT_REJECTED event).
 * This simplifies the common scenario in which grammars are changed
 * during result finalization process because the user's input
 * has changed the application's state.  (This implicit commit
 * is deferred following a call to suspend
 * and until a call to commitChanges.)
 * <p>
 * The Recognizer issues a CHANGES_COMMITTED
 * event to signal that changes have been committed.  This event signals
 * a transition from the SUSPENDED state to the
 * LISTENING state.  Once in the LISTENING state
 * the Recognizer resumes the processing of incoming
 * audio with the newly committed grammars.
 * <p>
 * Also each changed Grammar receives a
 * GRAMMAR_CHANGES_COMMITTED through attached
 * GrammarListeners whenever changes to it are
 * committed.
 * <p>
 * The commit changes mechanism has two important properties:
 * <p>
 * Updates to grammar definitions and the enabled property take
 * effect atomically (all changes take effect at once).  There
 * are no intermediate states in which some, but not all,
 * changes have been applied.
 * It is a method of Recognizer so all changes to all
 * Grammars are committed at once.  Again, there
 * are no intermediate states in which some, but not all,
 * changes have been applied.
 * <p>
 * Grammar and Result Listeners
 * <p>
 * An application can attach one or more GrammarListeners
 * to any Grammar.  The listener is notified of
 * status changes for the Grammar: when changes are
 * committed and when activation changes.
 * <p>
 * An application can attach one or more ResultListener
 * objects to each Grammar.  The listener is notified of
 * all events for all results that match this grammar.  The listeners
 * receive ResultEvents starting with the
 * GRAMMAR_FINALIZED event (not the preceding
 * RESULT_CREATED or RESULT_UPDATED
 * events).
 *
 * @see javax.speech.recognition.RuleGrammar
 * @see javax.speech.recognition.DictationGrammar
 * @see javax.speech.recognition.GrammarListener
 * @see javax.speech.recognition.ResultListener
 * @see javax.speech.recognition.Result
 * @see javax.speech.recognition.Recognizer
 * @see javax.speech.recognition.Recognizer#commitChanges()
 */
public interface Grammar {

    /**
     * Default value of activation mode that requires the Recognizer
     * have focus and that there be no enabled grammars with
     * RECOGNIZER_MODAL mode for the grammar to be
     * activated.  This is the lowest priority activation mode
     * and should be used unless there is a user interface design
     * reason to use another mode.
     * <p>
     * The
     * <A href="#activation">activation conditions</A>
     * for the RECOGNIZER_FOCUS mode are describe above.
     *
     * @see javax.speech.recognition.Grammar#setActivationMode(int)
     * @see javax.speech.recognition.Grammar#RECOGNIZER_MODAL
     * @see javax.speech.recognition.Grammar#GLOBAL
     */
    int RECOGNIZER_FOCUS = 900;

    /**
     * Value of activation mode that requires the Recognizer
     * have focus for the grammar to be activated.
     * <p>
     * The
     * <A href="#activation">activation conditions</A>
     * for the RECOGNIZER_MODAL mode are describe above.
     *
     * @see javax.speech.recognition.Grammar#setActivationMode(int)
     * @see javax.speech.recognition.Grammar#RECOGNIZER_FOCUS
     * @see javax.speech.recognition.Grammar#GLOBAL
     */
    int RECOGNIZER_MODAL = 901;

    /**
     * Value of activation mode in which the Grammar
     * is active for recognition irrespective of the focus state
     * of the Recognizer.
     * <p>
     * The
     * <A href="#activation">activation conditions</A>
     * for the GLOBAL mode are describe above.
     *
     * @see javax.speech.recognition.Grammar#setActivationMode(int)
     * @see javax.speech.recognition.Grammar#RECOGNIZER_FOCUS
     * @see javax.speech.recognition.Grammar#RECOGNIZER_MODAL
     */
    int GLOBAL = 902;

    /**
     * Request notifications of events of related to this Grammar.
     * An application can attach multiple listeners to a Grammar.
     *
     * @see javax.speech.recognition.Grammar#removeGrammarListener(javax.speech.recognition.GrammarListener)
     */
    void addGrammarListener(GrammarListener listener);

    /**
     * Request notifications of events from any Result
     * that matches this Grammar.  An application
     * can attach multiple ResultListeners to a Grammar.
     * A listener is removed with the removeResultListener method.
     * <p>
     * A ResultListener attached to a Grammar
     * receives result events starting from the GRAMMAR_FINALIZED
     * event - the event which indicates that the matched grammar is
     * known.  A ResultListener attached to a
     * Grammar will never receive a RESULT_CREATED
     * event and does not receive any RESULT_UPDATED
     * events that occurred before the GRAMMAR_FINALIZED
     * event.  A ResultListener attached to a Grammar
     * is guaranteed to receive a result finalization event -
     * RESULT_ACCEPTED or RESULT_REJECTED -
     * some time after the GRAMMAR_FINALIZED event.
     * <p>
     * ResultListener objects can also be attached to
     * a Recognizer and to any Result.
     * A listener attached to the Recognizer receives
     * all events for all results produced by that Recognizer.
     * A listener attached to a Result receives
     * all events for that result from the time at which the listener
     * is attached.
     *
     * @see javax.speech.recognition.Grammar#removeResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Recognizer#addResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Result#addResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.ResultEvent#RESULT_CREATED
     * @see javax.speech.recognition.ResultEvent#GRAMMAR_FINALIZED
     * @see javax.speech.recognition.ResultEvent#RESULT_ACCEPTED
     * @see javax.speech.recognition.ResultEvent#RESULT_REJECTED
     */
    void addResultListener(ResultListener listener);

    /**
     * Return the current activation mode for a Grammar.
     * The default value for a grammar is RECOGNIZER_FOCUS.
     *
     * @see javax.speech.recognition.Grammar#setActivationMode(int)
     * @see javax.speech.recognition.Grammar#setEnabled(boolean)
     * @see javax.speech.recognition.Grammar#isActive()
     */
    int getActivationMode();

    /**
     * Get the name of a grammar.  A grammar's name must be unique for
     * a recognizer.  Grammar names use a similar naming convention to
     * Java classes.  The naming convention are defined in the
     * Java Speech Grammar Format Specification.
     * <p>
     * Grammar names are used with a RuleGrammar
     * for resolving imports and references between grammars.
     * The name of a RuleGrammar is set when the grammar
     * is created (either by loading a JSGF document or creating
     * a new RuleGrammar).
     * <p>
     * The name of a DictationGrammar should reflect the language
     * domain it supports.  For example: com.acme.dictation.us.general
     * for general US Dictation from Acme corporation.  Since a DictationGrammar
     * is built into a Recognizer, its name is determined by
     * the Recognizer.
     *
     * @see javax.speech.recognition.DictationGrammar
     * @see javax.speech.recognition.RuleGrammar
     */
    String getName();

    /**
     * Returns a reference to the Recognizer that
     * owns this grammar.
     */
    Recognizer getRecognizer();

    /**
     * Test whether a Grammar is currently active for recognition.
     * When a grammar is active, the recognizer is matching incoming audio
     * against the grammar (and other active grammars) to detect speech
     * that matches the grammar.
     * <p>
     * A Grammar is activated for recognition if the
     * enabled property is set to true and
     * the
     * <A href="#activation">activation conditions</A>
     * are met.
     * Activation is not directly controlled by applications and so can
     * only be tested (there is no setActive method).
     * <p>
     * Rules of a RuleGrammar can be individually enabled
     * and disabled.  However all rules share the same ActivationMode
     * and the same activation state.  Thus, when a RuleGrammar is
     * active, all the enabled rules of the grammar are active for recognition.
     * <p>
     * Changes in the activation state are indicated by
     * GRAMMAR_ACTIVATED and GRAMMAR_DEACTIVATED
     * events issued to the GrammarListener.  A change in
     * activation state can follow these RecognizerEvents:
     * <p>
     * A CHANGES_COMMITTED event that applies a
     * change in the enabled state or
     * ActivationMode of this or another Grammar.
     * A FOCUS_GAINED or FOCUS_LOST
     * event.
     *
     * @see javax.speech.recognition.Grammar#setEnabled(boolean)
     * @see javax.speech.recognition.Grammar#setActivationMode(int)
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_ACTIVATED
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_DEACTIVATED
     * @see javax.speech.recognition.RecognizerEvent#CHANGES_COMMITTED
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_GAINED
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_LOST
     */
    boolean isActive();

    /**
     * Return the enabled property of a Grammar.
     * More specialized behaviour is specified by the
     * RuleGrammar interface.
     *
     * @see javax.speech.recognition.Grammar#setEnabled(boolean)
     * @see javax.speech.recognition.RuleGrammar#isEnabled()
     * @see javax.speech.recognition.RuleGrammar#isEnabled(java.lang.String)
     */
    boolean isEnabled();

    /**
     * Remove a listener from this Grammar.
     *
     * @see javax.speech.recognition.Grammar#addGrammarListener(javax.speech.recognition.GrammarListener)
     */
    void removeGrammarListener(GrammarListener listener);

    /**
     * Remove a ResultListener from this Grammar.
     *
     * @see javax.speech.recognition.Grammar#addResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Recognizer#removeResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Grammar#removeResultListener(javax.speech.recognition.ResultListener)
     */
    void removeResultListener(ResultListener listener);

    /**
     * Set the activation mode of a Grammar as
     * RECOGNIZER_FOCUS, RECOGNIZER_MODAL,
     * or GLOBAL.  The role of the activation mode
     * in the
     * <A href="#activation">activation conditions</A>
     * for a Grammar are described above.
     * The default activation mode - RECOGNIZER_FOCUS -
     * should be used unless there is a user interface design
     * reason to use another mode.
     * <p>
     * The individual rules of a RuleGrammar can
     * be separately enabled and disabled.  However, all rules
     * share the same ActivationMode since the
     * mode is a property of the complete Grammar.
     * A consequence is that all enabled rules of a RuleGrammar
     * are activated and deactivated together.
     * <p>
     * A change in activation mode only takes effect once
     * <p>
     * <A href="#commit">changes are committed</A>
     * .  For some
     * recognizers changing the activation mode is computationally
     * expensive.
     * <p>
     * The activation mode of a grammar can be tested by the
     * getActivationMode method.
     * <p>
     * [Note: future releases may modify the set of activation modes.]
     *
     * @throws java.lang.IllegalArgumentException if an attempt is made to set GLOBAL mode for a DictationGrammar
     * @see javax.speech.recognition.Grammar#getActivationMode()
     * @see javax.speech.recognition.Grammar#RECOGNIZER_FOCUS
     * @see javax.speech.recognition.Grammar#RECOGNIZER_MODAL
     * @see javax.speech.recognition.Grammar#GLOBAL
     * @see javax.speech.recognition.Grammar#setEnabled(boolean)
     * @see javax.speech.recognition.Grammar#isActive()
     * @see javax.speech.recognition.Recognizer#commitChanges()
     */
    void setActivationMode(int mode) throws IllegalArgumentException;

    /**
     * Set the enabled property of a Grammar.  A change
     * in the enabled property takes effect only after
     * <p>
     * <A href="#commit">grammar changes are committed</A>
     * .
     * Once a grammar is enabled and when the
     * <p>
     * <A href="#activation">activation conditions</A>
     * are met, it
     * is activated for recognition.  When a grammar is activated, the
     * Recognizer listens to incoming audio for speech
     * that matches the grammar and produces a Result when
     * matching speech is detected.
     * <p>
     * The enabled property of a grammar is tested with the isEnabled method.
     * The activation state of a grammar is tested with the isActive method.
     * <p>
     * The RuleGrammar interface extends the enabling property
     * to allow individual rules to be enabled and disabled.
     *
     * @see javax.speech.recognition.Recognizer#commitChanges()
     * @see javax.speech.recognition.RuleGrammar#setEnabled(boolean)
     * @see javax.speech.recognition.RuleGrammar#setEnabled(java.lang.String, boolean)
     * @see javax.speech.recognition.RuleGrammar#setEnabled(java.lang.String[], boolean)
     * @see javax.speech.recognition.RuleGrammar#isEnabled()
     */
    void setEnabled(boolean enabled);
}
