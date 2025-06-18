package javax.speech.recognition;

import javax.speech.SpeechEvent;


/**
 * A ResultEvent is issued by a Result object
 * to indicate changes in the recognized tokens and changes in state.
 * An event is issued to all appropriate ResultListener objects.
 * ResultListeners can be attached in three places:
 * <p>
 * A ResultListener attached to the Recognizer
 * receives all events for all results produced by the
 * recognizer.
 * A ResultListener attached to a Grammar
 * receives all events for all results that have been
 * finalized for that Grammar: the
 * GRAMMAR_FINALIZED event and following events.
 * (Note: it never receives a RESULT_CREATED event
 * because that event always precedes GRAMMAR_FINALIZED).
 * A ResultListener attached to a Result
 * receives all events for that result starting from the
 * time at which the listener is attached.  (Note: it never
 * receives a RESULT_CREATED event because a listener
 * can only be attached to a Result once a
 * RESULT_CREATED event has already been issued.)
 * <p>
 * In all three forms of listener attachment, one listener can be
 * attached to multiple objects, and multiple listeners can be
 * attached to a single object.
 * <p>
 * The
 * <A href="Result.html#states">three states of a recognition result</A>
 * <p>
 * are described in the documentation for the Result interface
 * and can be tested by the getResultState method of that interface.
 * The RESULT_CREATED, RESULT_ACCEPTED and
 * RESULT_REJECTED events indicate result state changes.
 * <p>
 * In brief, the three states are:
 * <p>
 * UNFINALIZED state: initial state of result indicating that
 * recognition is still taking place.  A new result is created
 * in the UNFINALIZED state with a RESULT_CREATED
 * event.
 * ACCEPTED state: the result is finalized and the recognizer
 * is confident it has recognized the result correctly.  The
 * RESULT_ACCEPTED event indicates a state change
 * from the UNFINALIZED state to the ACCEPTED state.
 * REJECTED state: the result is finalized but the recognizer
 * is not confident it has recognized the result correctly.  The
 * RESULT_REJECTED event indicates a state change
 * from the UNFINALIZED state to the REJECTED state.
 * <p>
 * The
 * <A href="Result.html#events">sequence of ResultEvents
 * associated with a recognition result</A>
 * are described in the documentation for the Result interface.
 * <p>
 * In brief, the events that occur depend upon the Result state:
 * <p>
 * A RESULT_CREATED event creates a Result
 * object.  A new result is starts in the UNFINALIZED
 * state.
 * UNFINALIZED state: RESULT_UPDATED
 * events indicate a change in finalized and/or unfinalized tokens;
 * a GRAMMAR_FINALIZED event indicates that the
 * Grammar matched by this result has been identified.
 * The RESULT_ACCEPTED event finalizes a result by
 * indicating a change in state from UNFINALIZED
 * to ACCEPTED.
 * The RESULT_REJECTED event finalizes a result by
 * indicating a change in state from UNFINALIZED
 * to REJECTED.
 * In the finalized states - ACCEPTED and REJECTED -
 * the AUDIO_RELEASED and TRAINING_INFO_RELEASED
 * may be issued.
 */
public class ResultEvent extends SpeechEvent {

    /**
     * RESULT_CREATED is issued when a new Result
     * is created.  The event is received by each ResultListener
     * attached to the Recognizer.
     * <p>
     * When a result is created, it is in the UNFINALIZED
     * state.  When created the result may have zero or more
     * finalized tokens and zero or more unfinalized tokens.  The
     * presence of finalized and unfinalized tokens is indicated
     * by the isTokenFinalized and
     * isUnfinalizedTokensChanged flags.
     * <p>
     * The RESULT_CREATED event follows the
     * RECOGNIZER_PROCESSING event which transitions the
     * Recognizer from the LISTENING state to
     * the PROCESSING state.
     *
     * @see javax.speech.recognition.ResultListener#resultCreated(javax.speech.recognition.ResultEvent)
     * @see javax.speech.recognition.Result#UNFINALIZED
     * @see javax.speech.recognition.Recognizer#PROCESSING
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_PROCESSING
     * @see javax.speech.recognition.ResultEvent#isTokenFinalized()
     * @see javax.speech.recognition.ResultEvent#isUnfinalizedTokensChanged()
     */
    public static final int RESULT_CREATED = 801;

    /**
     * RESULT_UPDATED is issued when one or more tokens of
     * a Result are finalized or when the unfinalized tokens
     * of a result are changed.  The isTokenFinalized and
     * isUnfinalizedTokensChanged flags are set appropriately.
     * <p>
     * The RESULT_UPDATED event only occurs when a
     * Result is in the UNFINALIZED state.
     *
     * @see javax.speech.recognition.ResultEvent#isTokenFinalized()
     * @see javax.speech.recognition.ResultEvent#isUnfinalizedTokensChanged()
     * @see javax.speech.recognition.Result#UNFINALIZED
     * @see javax.speech.recognition.ResultListener#resultUpdated(javax.speech.recognition.ResultEvent)
     */
    public static final int RESULT_UPDATED = 802;

    /**
     * GRAMMAR_FINALIZED is issued when the Grammar
     * matched by a Result is identified and finalized.
     * Before this event the getGrammar method of a
     * Result returns null. Following the
     * event it is guaranteed to return non-null and the grammar
     * is guaranteed not to change.
     * <p>
     * The GRAMMAR_FINALIZED event only occurs for a
     * Result is in the UNFINALIZED state.
     * <p>
     * A GRAMMAR_FINALIZED event does not affect finalized
     * or unfinalized tokens.
     *
     * @see javax.speech.recognition.ResultEvent#isTokenFinalized()
     * @see javax.speech.recognition.ResultEvent#isUnfinalizedTokensChanged()
     * @see javax.speech.recognition.Result#UNFINALIZED
     * @see javax.speech.recognition.ResultListener#grammarFinalized(javax.speech.recognition.ResultEvent)
     */
    public static final int GRAMMAR_FINALIZED = 803;

    /**
     * RESULT_ACCEPTED event is issued when a Result
     * is successfully finalized and indicates a state change from
     * UNFINALIZED to ACCEPTED.
     * <p>
     * In the finalization transition, zero or more tokens may be finalized
     * and the unfinalized tokens are set to null.
     * The isTokenFinalized and isUnfinalizedTokensChanged
     * flags are set appropriately.
     * <p>
     * Since the Result is finalized (accepted),
     * the methods of FinalResult and either
     * FinalRuleResult or FinalDictationResult
     * can be used.  (Use the getGrammar method of Result
     * to determine the type of grammar matched.)
     * Applications should use type casting to ensure that only
     * the appropriate interfaces and method are used.
     * <p>
     * The RESULT_ACCEPTED event is issued after the
     * Recognizer issues a RECOGNIZER_SUSPENDED
     * event to transition from the PROCESSING state to
     * the SUSPENDED state.  Any changes made
     * to grammars or the enabled state of grammars during the processing
     * of the RESULT_ACCEPTED event are automatically
     * committed once the RESULT_ACCEPTED event has been
     * processed by all ResultListeners.  Once those
     * changes have been committed, the Recognizer returns
     * to the LISTENING state with a CHANGES_COMMITTED
     * event.  A call to commitChanges is not required.
     * (Except, if there is a call to suspend without
     * a subsequent call to commitChanges, the Recognizer
     * defers the commit until the commitChanges call is
     * received.)
     *
     * @see javax.speech.recognition.ResultListener#resultAccepted(javax.speech.recognition.ResultEvent)
     * @see javax.speech.recognition.ResultEvent#isTokenFinalized()
     * @see javax.speech.recognition.ResultEvent#isUnfinalizedTokensChanged()
     * @see javax.speech.recognition.Result#getResultState()
     * @see javax.speech.recognition.Result#getGrammar()
     * @see javax.speech.recognition.Recognizer#SUSPENDED
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_SUSPENDED
     */
    public static final int RESULT_ACCEPTED = 804;

    /**
     * RESULT_REJECTED event is issued when a Result
     * is unsuccessfully finalized and indicates a change from the
     * UNFINALIZED state to the REJECTED state.
     * <p>
     * In the state transition, zero or more tokens may be finalized
     * and the unfinalized tokens are set to null.
     * The isTokenFinalized and isUnfinalizedTokensChanged
     * flags are set appropriately.  However, because the result is
     * rejected, the tokens are quite likely to be incorrect.
     * <p>
     * Since the Result is finalized (rejected),
     * the methods of FinalResult can be used.
     * If the grammar is known (GRAMMAR_FINALIZED event
     * was issued and the getGrammar methods returns
     * non-null) then the FinalRuleResult or
     * FinalDictationResult interface can also be used
     * depending upon whether the matched grammar was a
     * RuleGrammar or DictationGrammar.
     * <p>
     * Other state transition behavior for RESULT_REJECTED
     * is the same as for the RESULT_ACCEPTED event.
     *
     * @see javax.speech.recognition.ResultEvent#isTokenFinalized()
     * @see javax.speech.recognition.ResultEvent#isUnfinalizedTokensChanged()
     * @see javax.speech.recognition.Result#getResultState()
     * @see javax.speech.recognition.Result#getGrammar()
     * @see javax.speech.recognition.ResultListener#resultRejected(javax.speech.recognition.ResultEvent)
     */
    public static final int RESULT_REJECTED = 805;

    /**
     * AUDIO_RELEASED event is issued when the audio information
     * associated with a FinalResult object is released.
     * The release may have been requested by an application call to
     * releaseAudio in the FinalResult interface
     * or may be initiated by the recognizer to reclaim memory.
     * The FinalResult.isAudioAvailable
     * method returns false after this event.
     * <p>
     * The AUDIO_RELEASED event is only issued for results
     * in a finalized state (getResultState returns either
     * ACCEPTED or REJECTED).
     *
     * @see javax.speech.recognition.FinalResult#releaseAudio()
     * @see javax.speech.recognition.FinalResult#isAudioAvailable()
     * @see javax.speech.recognition.Result#getResultState()
     * @see javax.speech.recognition.ResultListener#audioReleased(javax.speech.recognition.ResultEvent)
     */
    public static final int AUDIO_RELEASED = 806;

    /**
     * TRAINING_INFO_RELEASED event is issued when the
     * training information for a finalized result is released.
     * The release may have been requested by an application call to the
     * releaseTrainingInfo method in the FinalResult
     * interface or may be initiated by the recognizer to reclaim memory.
     * The isTrainingInfoAvailable method of FinalResult
     * returns false after this event.
     * <p>
     * The TRAINING_INFO_RELEASED event is only issued for
     * results in a finalized state (getResultState returns either
     * ACCEPTED or REJECTED).
     *
     * @see javax.speech.recognition.FinalResult#releaseTrainingInfo()
     * @see javax.speech.recognition.FinalResult#isTrainingInfoAvailable()
     * @see javax.speech.recognition.Result#getResultState()
     * @see javax.speech.recognition.ResultListener#trainingInfoReleased(javax.speech.recognition.ResultEvent)
     */
    public static final int TRAINING_INFO_RELEASED = 807;

    /**
     * True if the ResultEvent indicates that one or more
     * tokens have been finalized.  Tokens may be finalized with
     * any of the following result events:
     * RESULT_CREATED, RESULT_UPDATED,
     * RESULT_ACCEPTED, RESULT_REJECTED.
     *
     * @see javax.speech.recognition.ResultEvent#isTokenFinalized()
     */
    protected boolean tokenFinalized;

    /**
     * True if the ResultEvent indicates that the
     * unfinalized tokens have changed.  The unfinalized tokens
     * may change with the following result events:
     * RESULT_CREATED, RESULT_UPDATED,
     * RESULT_ACCEPTED, RESULT_REJECTED.
     *
     * @see javax.speech.recognition.ResultEvent#isUnfinalizedTokensChanged()
     */
    protected boolean unfinalizedTokensChanged;

    /**
     * Constructs a ResultEvent with an event type identifier.
     * The isTokenFinalized and isUnfinalizedTokensChanged
     * flags are set to false.
     *
     * @param source the object that issued the event
     * @param id     the identifier for the event type
     */
    public ResultEvent(Result source, int id) {
        super(source, id);
        this.tokenFinalized = false;
        this.unfinalizedTokensChanged = false;
    }

    /**
     * Constructs a ResultEvent for a specified source
     * Result and result event id.  The two boolean flags
     * indicating change in tokens should be set appropriately for
     * RESULT_CREATED, RESULT_UPDATED,
     * RESULT_ACCEPTED and RESULT_REJECTED events.
     * (For other event types these flags should be false).
     *
     * @param source                     the Result object that issued the event
     * @param id                         the identifier for the event type
     * @param isTokenFinalized           true if any token is finalized with this event
     * @param isUnfinalizedTokensChanged true if the unfinalized text is changed with this event
     * @see javax.speech.recognition.ResultEvent#RESULT_CREATED
     * @see javax.speech.recognition.ResultEvent#GRAMMAR_FINALIZED
     * @see javax.speech.recognition.ResultEvent#RESULT_UPDATED
     * @see javax.speech.recognition.ResultEvent#RESULT_ACCEPTED
     * @see javax.speech.recognition.ResultEvent#RESULT_REJECTED
     * @see javax.speech.recognition.ResultEvent#AUDIO_RELEASED
     * @see javax.speech.recognition.ResultEvent#TRAINING_INFO_RELEASED
     */
    public ResultEvent(Result source, int id, boolean isTokenFinalized, boolean isUnfinalizedTokensChanged) {
        super(source, id);
        this.tokenFinalized = isTokenFinalized;
        this.unfinalizedTokensChanged = isUnfinalizedTokensChanged;
    }

    /**
     * For RESULT_CREATED, RESULT_UPDATED,
     * RESULT_ACCEPTED and RESULT_REJECTED events
     * returns true if any tokens were finalized.
     * For other events, return false.
     * If true, the number of tokens returned by numTokens
     * and getBestTokens has increased.
     *
     * @see javax.speech.recognition.Result#numTokens()
     * @see javax.speech.recognition.Result#getBestTokens()
     */
    public boolean isTokenFinalized() {
        return this.tokenFinalized;
    }

    /**
     * For RESULT_CREATED, RESULT_UPDATED,
     * RESULT_ACCEPTED and RESULT_REJECTED
     * events returns true if the unfinalized tokens changed.
     * For other events, return false.
     * If true, the value returned by getUnfinalizedTokens
     * has changed.
     * <p>
     * Note that both RESULT_ACCEPTED and RESULT_REJECTED
     * events implicitly set the unfinalized text to null.  The
     * isUnfinalizedTokensChanged method should return true
     * only if the unfinalized text was non-null prior to finalization.
     *
     * @see javax.speech.recognition.Result#getUnfinalizedTokens()
     */
    public boolean isUnfinalizedTokensChanged() {
        return this.unfinalizedTokensChanged;
    }

    /**
     * Returns a parameter string identifying this  event.
     * This method is useful for event-logging and for debugging.
     *
     * @return a string identifying the event
     */
    @Override
    public String paramString() {
        StringBuilder sb = new StringBuilder();
        switch (super.id) {
        case 801:
            sb.append("RESULT_CREATED");
            break;
        case 802:
            sb.append("RESULT_UPDATED");
            break;
        case 803:
            sb.append("GRAMMAR_FINALIZED");
            break;
        case 804:
            sb.append("RESULT_ACCEPTED");
            break;
        case 805:
            sb.append("RESULT_REJECTED");
            break;
        case 806:
            sb.append("AUDIO_RELEASED");
            break;
        case 807:
            sb.append("TRAINING_INFO_RELEASED");
            break;
        default:
            return super.paramString();
        }

        switch (super.id) {
        case 801:
        case 802:
        case 804:
        case 805:
            if (this.tokenFinalized) {
                sb.append(": token finalized");
            }

            if (this.unfinalizedTokensChanged) {
                sb.append(": unfinalized tokens changed");
            }
        case 803:
        default:
            return sb.toString();
        }
    }
}
