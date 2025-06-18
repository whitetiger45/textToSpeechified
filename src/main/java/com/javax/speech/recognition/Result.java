package javax.speech.recognition;

/**
 * A Result is issued by a Recognizer as it recognizes
 * an incoming utterance that matches an active Grammar.
 * The Result interface provides the application with access
 * to the following information about a recognized utterance:
 * <ol>
 * <li>A sequence of finalized tokens (words) that have been recognized,</li>
 * <li>A sequence of unfinalized tokens,</li>
 * <li>Reference to the grammar matched by the result,</li>
 * <li>The result state: UNFINALIZED,
 * ACCEPTED or REJECTED.</li>
 * </ol>
 * <h3>
 * Multiple Result Interfaces
 * </h3>
 * Every Result object provided by a Recognizer implements
 * both the FinalRuleResult and FinalDictationResult
 * interfaces.  Thus, by extension every result also implements the
 * FinalResult and Result interfaces.
 * <p>
 * These multiple interfaces are designed to explicitly indicate (a) what
 * information is available at what times in the result life-cycle and
 * (b) what information is available for different types of results.
 * Appropriate casting of results allows compile-time checking of
 * result-handling code and fewer bugs.
 * <p>
 * The FinalResult extends the Result interface.
 * It provides access to the additional information about a result
 * that is available once it has been finalized (once it is in either
 * of the ACCEPTED or REJECTED states).
 * Calling any method of the FinalResult interface for a
 * result in the UNFINALIZED state causes a
 * ResultStateError to be thrown.
 * <p>
 * The FinalRuleResult extends the FinalResult
 * interface.  It provides access to the additional information about
 * a finalized result that matches a RuleGrammar.  Calling
 * any method of the FinalRuleResult interface for a
 * non-finalized result or a result that matches a DictationGrammar
 * causes a ResultStateError to be thrown.
 * <p>
 * The FinalDictationResult also extends the FinalResult
 * interface.  It provides access to the additional information about
 * a finalized result that matches a DictationGrammar.  Calling
 * any method of the FinalDictationResult interface for a
 * non-finalized result or a result that matches a RuleGrammar
 * causes a ResultStateError to be thrown.
 * <p>
 * Note: every result implements both the FinalRuleResult
 * and FinalDictationResult interfaces even though the result
 * will match either a RuleGrammar or DictationGrammar,
 * but never both.  The reason for this is that when the result is
 * created (RESULT_CREATED event), the grammar is not always known.
 * <h3>
 * <A>Result States</A>
 * </h3>
 * The separate interfaces determine what information is available for a result
 * in the different stages of its life-cycle.  The state of a Result
 * is determined by calling the getResultState method.  The
 * three possible states are UNFINALIZED,
 * ACCEPTED and REJECTED.
 * <p>
 * A new result starts in the UNFINALIZED state.  When
 * the result is finalized is moves to either the
 * ACCEPTED or REJECTED state.
 * An accepted or rejected result is termed a finalized result.
 * All values and information regarding a finalized result are fixed
 * (excepting that audio and training information may be released).
 * <p>
 * Following are descriptions of a result object in each of the three states
 * including information on which interfaces can be used in each state.
 * <h4>
 * getResultState() == Result.UNFINALIZED
 * </h4>
 * <li>Recognition of the result is in progress.
 * <li>A new result is created with a RESULT_CREATED event
 * that is issued to each ResultListener attached
 * to a Recognizer.  The new result is created in
 * in the UNFINALIZED state.
 * <li>A result remains in the UNFINALIZED state until
 * it is finalized by either a RESULT_ACCEPTED
 * or RESULT_REJECTED event.
 * <li>Applications should only call the methods of the Result
 * interface.  A ResultStateError is issued on calls to the
 * methods of FinalResult, FinalRuleResult
 * and FinalDictationResult interfaces.
 * <li>Events 1: zero or more RESULT_UPDATED events
 * may be issued as (a) tokens are finalized, or (b) as the
 * unfinalized tokens changes.
 * <li>Events 2: one GRAMMAR_FINALIZED event must be
 * issued in the UNFINALIZED state before result
 * finalization by an RESULT_ACCEPTED event.
 * (Not required if a result is rejected.)
 * <li>Events 3: the GRAMMAR_FINALIZED event is optional
 * if the result is finalized by a RESULT_REJECTED
 * event.  (It is not always possible for a recognizer to identify
 * a best-match grammar for a rejected result.)
 * <li>Prior to the GRAMMAR_FINALIZED event, the
 * getGrammar returns null.
 * Following the GRAMMAR_FINALIZED event the
 * getGrammar method returns a non-null reference to
 * the active Grammar that is matched by this result.
 * numTokens returns the number of finalized tokens.
 * While in the UNFINALIZED this number may increase
 * as ResultEvent.RESULT_UPDATED events are issued.
 * <li>The best guess for each finalized token is available through
 * getBestToken(int num).  The best guesses are guaranteed
 * not to change through the remaining life of the result.
 * getUnfinalizedTokens may return zero or more tokens and
 * these may change at any time when a ResultEvent.RESULT_UPDATED
 * event is issued.
 * <h4>
 * getResultState() == Result.ACCEPTED
 * </h4>
 * <li>Recognition of the Result is complete and the recognizer
 * is confident it has the correct result (not a rejected result).
 * Non-rejection is not a guarantee of a correct result - only sufficient
 * confidence that the guess is correct.
 * <li>Events 1: a result transitions from the UNFINALIZED state to
 * the ACCEPTED state when an RESULT_ACCEPTED
 * event is issued.
 * <li>Events 2: AUDIO_RELEASED and TRAINING_INFO_RELEASED
 * events may occur optionally (once) in the ACCEPTED state.
 * <li>numTokens will return 1 or greater (there must be at least
 * one finalized token) and the number of finalized tokens will not change.
 * [Note: A rejected result may have zero finalized tokens.]
 * <li>The best guess for each finalized token is available through the
 * getBestToken(int tokNum) method.  The best guesses will
 * not change through the remaining life of the result.
 * <li>getUnfinalizedTokens method returns null.
 * <li>The getGrammar method returns the grammar matched by
 * this result.  It may be either a RuleGrammar or
 * DictationGrammar.
 * <li>For either a RuleGrammar or DictationGrammar
 * the methods of FinalResult may be used to access
 * audio data and to perform correction/training.
 * <li>If the result matches a RuleGrammar, the methods
 * of FinalRuleResult may be used to get alternative
 * guesses for the complete utterance and to get tags and other
 * information associated with the RuleGrammar.
 * (Calls to any methods of the FinalDictationResult
 * interface cause a ResultStateError.)
 * <li>If the result matches a DictationGrammar, the methods
 * of FinalDictationResult may be used to get alternative
 * guesses for tokens and token sequences.
 * (Calls to any methods of the FinalRuleResult
 * interface cause a ResultStateError.)
 * <h4>
 * getResultState() == Result.REJECTED
 * </h4>
 * <li>Recognition of the Result is complete but the recognizer
 * believes it does not have the correct result.  Programmatically,
 * an accepted and rejected result are very similar but the contents
 * of a rejected result must be treated differently - they are likely to be wrong.
 * <li>Events 1: a result transitions from the UNFINALIZED state to
 * the REJECTED state when an RESULT_REJECTED
 * event is issued.
 * <li>Events 2: (same as for the ACCEPTED state)
 * AUDIO_RELEASED and TRAINING_INFO_RELEASED
 * events may occur optionally (once) in the REJECTED state.
 * <li>numTokens will return 0 or greater.
 * The number of tokens will not change for the remaining life
 * of the result.  [Note: an accepted result always has at least
 * one finalized token.]
 * <li>As with an accepted result,
 * the best guess for each finalized token is available through the
 * getBestToken(int num) method and the tokens are guaranteed
 * not to change through the remaining life of the result.  Because
 * the result has been rejected the guesses are not likely to be correct.
 * <li>getUnfinalizedTokens method returns null.
 * <li>If the GRAMMAR_FINALIZED was issued during recognition
 * of the result, the getGrammar method returns the
 * grammar matched by this result otherwise it returns null.
 * It may be either a RuleGrammar or DictationGrammar.
 * For rejected results, there is a greater chance that this
 * grammar is wrong.
 * <li>The FinalResult interface behaves the same as
 * for a result in the ACCEPTED state expect that the information
 * is less likely to be reliable.
 * <li>If the grammar is known, the FinalRuleResult and
 * FinalDictationResult interfaces behave the same as
 * for a result in the ACCEPTED state expect that the information
 * is less likely to be reliable.  If the grammar is unknown, then
 * a ResultStateError is thrown on calls to the methods of both
 * FinalRuleResult and FinalDictationResult.
 * <h3>
 * Result State and Recognizer States
 * </h3>
 * The state system of a Recognizer is linked to the
 * state of recognition of the current result.  The Recognizer
 * interface documents the
 * <A href="Recognizer.html#normalEvents">normal
 * event cycle</A>
 * for a Recognizer and for Results.
 * The following is an overview of the ways in which the two state
 * systems are linked:
 * <p>
 * <li>The ALLOCATED state of a Recognizer has
 * three sub-states.  In the LISTENING state, the
 * recognizer is listening to background audio and there is
 * no result being produced.  In the SUSPENDED
 * state, the recognizer is temporarily buffering audio input
 * while its grammars are updated.  In the PROCESSING
 * state, the recognizer has detected incoming audio that
 * may match an active grammar and is producing a Result.
 * <li>The Recognizer moves from the LISTENING
 * state to the PROCESSING state with a
 * RECOGNIZER_PROCESSING event immediately prior to
 * issuing a RESULT_CREATED event.
 * <li>The RESULT_UPDATED and GRAMMAR_FINALIZED
 * events are produced while the Recognizer is in
 * the PROCESSING state.
 * <li>The Recognizer finalizes a Result with
 * RESULT_ACCEPTED or RESULT_REJECTED
 * event immediately after it transitions from the PROCESSING
 * state to the SUSPENDED state with a
 * RECOGNIZER_SUSPENDED event.
 * <li>Unless there is a pending suspend, the
 * Recognizer commits grammar changes with a
 * CHANGES_COMMITTED event as soon as the
 * result finalization event is processed.
 * <li>The TRAINING_INFO_RELEASED and AUDIO_RELEASED
 * events can occur in any state of an ALLOCATED
 * Recognizer.
 * <h3>
 * Accept or Reject?
 * </h3>
 * Rejection of a result indicates that the recognizer is not confident that it
 * has accurately recognized what a user says.  Rejection can be controlled
 * through the RecognizerProperties interface with the
 * setConfidenceLevel method.  Increasing the confidence level
 * requires the recognizer to have greater confident to accept a result, so
 * more results are likely to be rejected.
 * <p>
 * Important: the acceptance of a result (an RESULT_ACCEPTED
 * event rather than a RESULT_REJECTED event) does not mean the
 * result is correct.  Instead, acceptance implies that the recognizer has a
 * sufficient level of confidence that the result is correct.
 * <p>
 * It is difficult for recognizers to reliably determine when they make mistakes.
 * Applications need to determine the cost of incorrect recognition of any
 * particular results and take appropriate actions.  For example, confirm
 * with a user that they said "delete all files" before deleting anything.
 * <h3>
 * <A>Result Events</A>
 * </h3>
 * Events are issued when a new result is created and when there is any change
 * in the state or information content of a result.  The following
 * describes the event sequence for an accepted result.  It provides the
 * same information as above for result states, but focuses on legal event
 * sequences.
 * <p>
 * Before a new result is created for incoming speech, a recognizer
 * usually issues a
 * <p>
 * <A href="RecognizerAudioEvent.html#SPEECH_STARTED">SPEECH_STARTED</A>
 * event to the
 * <p>
 * <A href="RecognizerAudioListener.html#speechStarted(javax.speech.recognition.RecognizerEvent)">
 * speechStarted</A>
 * method of
 * <p>
 * <A href="RecognizerAudioListener.html">RecognizerAudioListener</A>
 * .
 * The
 * <p>
 * A newly created Result is provided to the application
 * by calling the resultCreated method of each ResultListener
 * attached to the Recognizer with a RESULT_CREATED
 * event.  The new result may or may not have any finalized tokens
 * or unfinalized tokens.
 * <p>
 * At any time following the RESULT_CREATED event, an application
 * may attach a ResultListener to an individual result.
 * That listener will receive all subsequent events associated with
 * that Result.
 * <p>
 * A new Result is created in the UNFINALIZED
 * state.  In this state, zero or more RESULT_UPDATED events
 * may be issued to each ResultListener attached to the
 * Recognizer and to each ResultListener
 * attached to that Result.  The RESULT_UPDATED
 * indicates that one or more tokens have been finalized, or that
 * the unfinalized tokens have changed, or both.
 * <p>
 * When the recognizer determines which grammar is the best match for
 * incoming speech, it issues a GRAMMAR_FINALIZED event.
 * This event is issued to each ResultListener attached to the
 * Recognizer and to each ResultListener
 * attached to that Result.
 * <p>
 * The GRAMMAR_FINALIZED event is also issued to each
 * ResultListener attached to the matched Grammar.
 * This is the first ResultEvent received by
 * ResultListeners attached to the Grammar.
 * All subsequent result events are issued to all
 * ResultListeners attached to the matched Grammar
 * (as well as to ResultListeners attached to the
 * Result and to the Recognizer).
 * <p>
 * Zero or more RESULT_UPDATED events may be
 * issued after the GRAMMAR_FINALIZED event but before
 * the result is finalized.
 * <p>
 * Once the recognizer completes recognition of the Result
 * that it chooses to accept, it finalizes the result with an
 * RESULT_ACCEPTED event that is issued to the
 * ResultListeners attached to the Recognizer,
 * the matched Grammar, and the Result.
 * This event may also indicate finalization of zero or more tokens,
 * and/or the resetting of the unfinalized tokens to null.
 * The result finalization event occurs immediately after the
 * Recognizer makes a transition from the PROCESSING
 * state to the SUSPENDED state with a
 * RECOGNIZER_SUSPENDED event.
 * <p>
 * A finalized result (accepted or rejected state) may issue a
 * AUDIO_RELEASED or TRAINING_INFO_RELEASED
 * event.  These events may be issued in response to relevant release
 * methods of FinalResult and FinalDictationResult
 * or may be issued when the recognizer independently determines to
 * release audio or training information.
 * <p>
 * When a result is rejected some of the events described above may be skipped.
 * A result may be rejected with the RESULT_REJECTED
 * event at any time after a RESULT_CREATED event instead
 * of an RESULT_ACCEPTED event.
 * A result may be rejected with or without any unfinalized or finalized
 * tokens being created (no RESULT_UPDATED events), and
 * with or without a GRAMMAR_FINALIZED event.
 * <h3>
 * When does a Result start and end?
 * </h3>
 * A new result object is created when a recognizer has detected
 * possible incoming speech which may match an active grammar.
 * <p>
 * To accept the result (i.e. to issue a RESULT_ACCEPTED event),
 * the best-guess tokens of the result must match the token patterns
 * defined by the matched grammar.  For a  RuleGrammar
 * this implies that a call to the parse
 * method of the matched RuleGrammar must return successfully.
 * (Note: the parse is not guaranteed if the grammar has been changed.)
 * <p>
 * Because there are no programmatically defined constraints upon word
 * patterns for a DictationGrammar, a single result may
 * represent a single word, a short phrase or sentence, or possibly many
 * pages of text.
 * <p>
 * The set of conditions that may cause a result matching a
 * DictationGrammar to be finalized includes:
 * <p>
 * <li>The user pauses for a period of time (a timeout).
 * <li>A call to the
 * <A href="Recognizer.html#forceFinalize(boolean)">forceFinalize</A>
 * method of the recognizer.
 * <li>User has spoken text matching an active RuleGrammar
 * (the dictation result is finalized and a new Result
 * is issued for the RuleGrammar).
 * <li>The engine is paused.
 * <p>
 * The following conditions apply to all finalized results:
 * <p>
 * <li>N-best alternative token guesses available through the
 * FinalRuleResult and FinalDictationResult
 * interfaces cannot cross result boundaries.
 * <li>Correction/training is only possible within a single result object.
 *
 * @see javax.speech.recognition.FinalResult
 * @see javax.speech.recognition.FinalRuleResult
 * @see javax.speech.recognition.FinalDictationResult
 * @see javax.speech.recognition.ResultEvent
 * @see javax.speech.recognition.ResultListener
 * @see javax.speech.recognition.ResultAdapter
 * @see javax.speech.recognition.Grammar
 * @see javax.speech.recognition.RuleGrammar
 * @see javax.speech.recognition.DictationGrammar
 * @see javax.speech.recognition.Recognizer#forceFinalize(boolean)
 * @see javax.speech.recognition.RecognizerEvent
 * @see javax.speech.recognition.RecognizerProperties#setConfidenceLevel(float)
 */
public interface Result {

    /**
     * getResultState returns UNFINALIZED while a result
     * is still being recognized.  A Result is in the
     * UNFINALIZED state when the RESULT_CREATED
     * event is issued.
     * <A href="#states">Result states</A>
     * are
     * described above in detail.
     *
     * @see javax.speech.recognition.Result#getResultState()
     * @see javax.speech.recognition.ResultEvent#RESULT_CREATED
     */
    int UNFINALIZED = 300;

    /**
     * getResultState returns ACCEPTED once
     * recognition of the result is completed and the Result
     * object has been finalized by being accepted.  When a Result
     * changes to the ACCEPTED state a
     * RESULT_ACCEPTED event is issued.
     * <p>
     * <A href="#states">Result states</A>
     * are described above in detail.
     *
     * @see javax.speech.recognition.Result#getResultState()
     * @see javax.speech.recognition.ResultEvent#RESULT_ACCEPTED
     */
    int ACCEPTED = 301;

    /**
     * getResultState returns REJECTED once
     * recognition of the result complete and the Result object
     * has been finalized by being rejected.  When a Result
     * changes to the REJECTED state a
     * RESULT_REJECTED event is issued.
     * <p>
     * <A href="#states">Result states</A>
     * are described above in detail.
     *
     * @see javax.speech.recognition.Result#getResultState()
     * @see javax.speech.recognition.ResultEvent#RESULT_REJECTED
     */
    int REJECTED = 302;

    /**
     * Request notifications of events of related to this Result.
     * An application can attach multiple listeners to a Result.
     * A listener can be removed with the removeResultListener method.
     * <p>
     * ResultListener objects can also be attached to
     * a Recognizer and to any Grammar.
     * A listener attached to the Recognizer receives
     * all events for all results produced by that Recognizer.
     * A listener attached to a Grammar receives
     * all events for all results that have been finalized for
     * that Grammar (all events starting with and including
     * the GRAMMAR_FINALIZED event).
     * <p>
     * A ResultListener attached to a Result
     * only receives events following the point in time at which the
     * listener is attached.  Because the listener can only be attached
     * during or after the RESULT_CREATED, it will not receive
     * the RESULT_CREATED event.  Only ResultListeners
     * attached to the Recognizer receive RESULT_CREATED
     * events.
     *
     * @see javax.speech.recognition.Result#removeResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Recognizer#addResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Grammar#addResultListener(javax.speech.recognition.ResultListener)
     */
    void addResultListener(ResultListener listener);

    /**
     * Returns the best guess for the tokNumth token.
     * tokNum must be in the range 0 to
     * numTokens()-1.
     * <p>
     * If the result has zero tokens (possible in both the
     * UNFINALIZED and REJECTED
     * states) an exception is thrown.
     * <p>
     * If the result is in the REJECTED state,
     * then the returned tokens are likely to be incorrect.  In the
     * ACCEPTED state (not rejected) the recognizer
     * is confident that the tokens are correct but applications should
     * consider the possibility that the tokens are incorrect.
     * <p>
     * The FinalRuleResult and FinalDictationResult
     * interfaces provide getAlternativeTokens methods that
     * return alternative token guesses for finalized results.
     *
     * @throws java.lang.IllegalArgumentException if tokNum is out of range.
     * @see javax.speech.recognition.Result#getUnfinalizedTokens()
     * @see javax.speech.recognition.Result#getBestTokens()
     * @see javax.speech.recognition.FinalRuleResult#getAlternativeTokens(int)
     * @see javax.speech.recognition.FinalDictationResult#getAlternativeTokens(ResultToken, ResultToken, int)
     */
    ResultToken getBestToken(int tokNum) throws IllegalArgumentException;

    /**
     * Returns all the best guess tokens for this result.
     * If the result has zero tokens, the return value is null.
     */
    ResultToken[] getBestTokens();

    /**
     * Return the Grammar matched by the best-guess finalized
     * tokens of this result or null if the grammar
     * is not known.  The return value is null before
     * a GRAMMAR_FINALIZED event and non-null afterwards.
     * <p>
     * The grammar is guaranteed to be non-null for an accepted
     * result.  The grammar may be null or non-null for a rejected
     * result, depending upon whether a GRAMMAR_FINALIZED
     * event was issued prior to finalization.
     * <p>
     * For a finalized result, an application should determine the
     * type of matched grammar with an instanceof test.
     * For a result that matches a RuleGrammar, the
     * methods of FinalRuleResult can be used (the
     * methods of FinalDictationResult throw an error).
     * For a result that matches a DictationGrammar, the
     * methods of FinalDictationResult can be used (the
     * methods of FinalRuleResult throw an error).
     * The methods of FinalResult can be used for
     * a result matching either kind of grammar.
     * <p>
     * Example:
     * <pre>
     * Result result;
     * if (result.getGrammar() instanceof RuleGrammar) {
     *   FinalRuleResult frr = (FinalRuleResult)result;
     *   ...
     * }
     * </pre>
     *
     * @see javax.speech.recognition.Result#getResultState()
     */
    Grammar getGrammar();

    /**
     * Returns the current state of the Result object: UNFINALIZED,
     * ACCEPTED or REJECTED.  The details of
     * a Result in each state are
     * <A href="#states">described above</A>.
     *
     * @see javax.speech.recognition.Result#UNFINALIZED
     * @see javax.speech.recognition.Result#ACCEPTED
     * @see javax.speech.recognition.Result#REJECTED
     */
    int getResultState();

    /**
     * In the UNFINALIZED state, return the current guess of
     * the tokens following the finalized tokens.
     * Unfinalized tokens provide an indication of what a recognizer
     * is considering as possible recognition tokens for speech
     * following the finalized tokens.
     * <p>
     * .  * Unfinalized tokens can provide users with feedback on the recognition
     * process.  The array may be any length (zero or more tokens),  the
     * length may change at any time, and successive calls to
     * getUnfinalizedTokens may return different
     * tokens or even different numbers of tokens.  When the
     * unfinalized tokens are changed, a RESULT_UPDATED
     * event is issued to the ResultListener.
     * The RESULT_ACCEPTED and RESULT_REJECTED
     * events finalize a result and always guarantee that the
     * return value is null.  A new result created
     * with a RESULT_CREATED event may have a null or non-null
     * value.
     * <p>
     * The returned array is null if there are currently no unfinalized tokens,
     * if the recognizer does not support unfinalized tokens,
     * or after a Result is finalized (in the ACCEPTED or
     * REJECTED state).
     *
     * @see javax.speech.recognition.ResultEvent#isUnfinalizedTokensChanged()
     * @see javax.speech.recognition.ResultEvent#RESULT_UPDATED
     * @see javax.speech.recognition.ResultEvent#RESULT_ACCEPTED
     * @see javax.speech.recognition.ResultEvent#RESULT_REJECTED
     */
    ResultToken[] getUnfinalizedTokens();

    /**
     * Returns the number of finalized tokens in a Result.
     * Tokens are numbered from 0 to numTokens()-1
     * and are obtained through the getBestToken and
     * getBestTokens method of this (Result)
     * interface and the getAlternativeTokens methods
     * of the FinalRuleResult and FinalDictationResult
     * interfaces for a finalized result.
     * <p>
     * Starting from the RESULT_CREATED event and while the
     * result remains in the UNFINALIZED state, the number
     * of finalized tokens may be zero or greater and can increase as
     * tokens are finalized.  When one or more tokens are finalized
     * in the UNFINALIZED state, a RESULT_UPDATED
     * event is issued with the tokenFinalized flag set true.
     * The RESULT_ACCEPTED and RESULT_REJECTED
     * events which finalize a result can also indicate that one or
     * more tokens have been finalized.
     * <p>
     * In the ACCEPTED and REJECTED states,
     * numTokens indicates the total number of tokens that
     * were finalized.  The number of finalized tokens never changes in
     * these states.  An ACCEPTED result must have
     * one or more finalized token.  A REJECTED result
     * may have zero or more tokens.
     *
     * @see javax.speech.recognition.ResultEvent#RESULT_UPDATED
     * @see javax.speech.recognition.Result#getBestToken(int)
     * @see javax.speech.recognition.Result#getBestTokens()
     * @see javax.speech.recognition.FinalRuleResult#getAlternativeTokens(int)
     * @see javax.speech.recognition.FinalDictationResult#getAlternativeTokens(ResultToken, ResultToken, int)
     */
    int numTokens();

    /**
     * Remove a listener from this Result.
     *
     * @see javax.speech.recognition.Result#addResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Recognizer#removeResultListener(javax.speech.recognition.ResultListener)
     * @see javax.speech.recognition.Grammar#removeResultListener(javax.speech.recognition.ResultListener)
     */
    void removeResultListener(ResultListener listener);
}
