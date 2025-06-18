package javax.speech.recognition;

import java.util.EventListener;


/**
 * The methods of a ResultListener receive notifications of
 * events related to a Result object.  A ResultListener
 * may be attached to any of three entities:
 * <p>
 * <li>Recognizer: a ResultListener attached
 * to a Recognizer receives notification of all
 * ResultEvents for all results produced by that
 * recognizer.
 * <li>Grammar: a ResultListener attached to a
 * Grammar receives all events for all results that have
 * been finalized for that grammar.  Specifically, it receives the
 * GRAMMAR_FINALIZED event and following events.
 * (Note: it never receives a RESULT_CREATED event because
 * that event always precedes the GRAMMAR_FINALIZED event).
 * <li>Result: a ResultListener attached to
 * a Result receives all events for that result starting
 * from the time at which the listener is attached.
 * (Note: it never receives a RESULT_CREATED event
 * because a listener can only be attached to a Result
 * once a RESULT_CREATED event has already been issued.)
 * <p>
 * The events are issued to the listeners in the order of most specific
 * to least specific: ResultListeners attached to the
 * Result are notified first, then listeners attached
 * to the matched Grammar, and finally to listeners
 * attached to the Recognizer.
 * <p>
 * A single ResultListener may be attached to multiple objects
 * (Recognizer, Grammar or Result),
 * and multiple ResultListeners may be attached to a single
 * object.
 * <p>
 * The source for all ResultEvents issued to a
 * ResultListener is the Result that
 * generated the event.  The full state system for results and the
 * associated events are described in the documentation
 * for ResultEvent.
 * <p>
 * A trivial implementation of the ResultListener
 * interface is provided by the ResultAdapter class.
 *
 * @see javax.speech.recognition.ResultEvent
 * @see javax.speech.recognition.ResultAdapter
 * @see javax.speech.recognition.Result#addResultListener(javax.speech.recognition.ResultListener)
 * @see javax.speech.recognition.Grammar#addResultListener(javax.speech.recognition.ResultListener)
 * @see javax.speech.recognition.Recognizer#addResultListener(javax.speech.recognition.ResultListener)
 */
public interface ResultListener extends EventListener {

    /**
     * A AUDIO_RELEASED event has occurred.
     * This event is only issued to finalized results.
     * See the documentation of the isAudioAvailable method
     * the FinalResult interface for details.
     * <p>
     * The event is issued to each ResultListener attached
     * to the Recognizer and to the Result.
     * If a GRAMMAR_FINALIZED event was issued, then
     * the matched Grammar is known, and the
     * event is also issued to each ResultListener attached
     * to that Grammar.
     *
     * @see javax.speech.recognition.ResultEvent#AUDIO_RELEASED
     * @see javax.speech.recognition.FinalResult#isAudioAvailable()
     */
    void audioReleased(ResultEvent e);

    /**
     * A GRAMMAR_FINALIZED event has occurred because
     * the Recognizer has determined which Grammar
     * is matched by the incoming speech.
     * <p>
     * The event is issued to each ResultListener attached
     * to the Recognizer, Result, and
     * matched Grammar.
     *
     * @see javax.speech.recognition.ResultEvent#GRAMMAR_FINALIZED
     */
    void grammarFinalized(ResultEvent e);

    /**
     * An RESULT_ACCEPTED event has occurred indicating
     * that a Result has transitioned from the
     * UNFINALIZED state to the ACCEPTED
     * state.
     * <p>
     * Since the Result source for this event is
     * finalized, the Result object can be safely cast
     * to the FinalResult interface.
     * <p>
     * Because the result is accepted, the matched Grammar
     * for the result is guaranteed to be non-null.  If the matched
     * Grammar is a RuleGrammar, then the
     * result object can be safely cast to FinalRuleResult
     * (methods of FinalDictationResult throw an exception).
     * If the matched Grammar is a DictationGrammar,
     * then the result object can be safely cast to FinalDictationResult
     * (methods of FinalRuleResult throw an exception).
     * For example:
     * <pre>
     * void resultAccepted(ResultEvent e) {
     *   Object source = e.getSource();
     *
     *   // always safe
     *   Result result = (Result)source;
     *   FinalResult fr = (FinalResult)source;
     *
     *   // find the grammar-specific details
     *   // the null test is only useful for rejected results
     *   if (result.getGrammar() != null) {
     *     if (result.getGrammar() instanceof RuleGrammar) {
     *       FinalRuleResult frr = (FinalRuleResult)source;
     *       ...
     *     }
     *     else if (result.getGrammar() instanceof DictationGrammar) {
     *       FinalDictationResult fdr = (FinalDictationResult)source;
     *       ...
     *     }
     * </pew>
     * The event is issued to each ResultListener attached
     * to the Recognizer, Result, and
     * matched Grammar.
     * <p>
     * The RESULT_ACCEPTED event is issued following
     * the RECOGNIZER_SUSPENDED RecognizerEvent
     * and while the Recognizer is in the SUSPENDED
     * state.  Once the RESULT_ACCEPTED event has been
     * processed by all listeners, the Recognizer automatically
     * commits all changes to grammars and returns to the LISTENING
     * state.  The only exception is when a call has been made to
     * suspend without a following call to commitChanges.
     * In this case the Recognizer remains SUSPENDED
     * until commitChanges is called.
     *
     * @see javax.speech.recognition.ResultEvent#RESULT_ACCEPTED
     * @see javax.speech.recognition.FinalResult
     * @see javax.speech.recognition.FinalRuleResult
     * @see javax.speech.recognition.FinalDictationResult
     * @see javax.speech.recognition.RecognizerEvent
     * @see javax.speech.recognition.Recognizer#commitChanges()
     */
    void resultAccepted(ResultEvent e);

    /**
     * A RESULT_CREATED event is issued when a
     * Recognizer detects incoming speech that
     * may match an active grammar of an application.
     * <p>
     * The event is issued to each ResultListener
     * attached to the Recognizer.
     * (ResultListeners attached to a Grammar
     * or to a Result never receive a RESULT_CREATED
     * event.)
     * <p>
     * The RESULT_CREATED follows the
     * RECOGNIZER_PROCESSING event that is issued
     * RecognizerListeners to indicate that the
     * Recognizer has changed from the LISTENING
     * to the PROCESSING state.
     *
     * @see javax.speech.recognition.ResultEvent#RESULT_CREATED
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_PROCESSING
     */
    void resultCreated(ResultEvent e);

    /**
     * An RESULT_REJECTED event has occurred indicating
     * that a Result has transitioned from the
     * UNFINALIZED state to the REJECTED state.
     * <p>
     * The casting behavior of a rejected result is the same as for
     * a RESULT_ACCEPTED event.  The exception is that if the
     * grammar is not known (no GRAMMAR_FINALIZED event),
     * then the result cannot be cast to either
     * FinalRuleResult or FinalDictationResult.
     * <p>
     * The state behavior and grammar committing actions are the
     * same as for the RESULT_ACCEPTED event.
     * <p>
     * The event is issued to each ResultListener attached
     * to the Recognizer and to the Result.
     * If a GRAMMAR_FINALIZED event was issued, then
     * the matched Grammar is known, and the
     * event is also issued to each ResultListener attached
     * to that Grammar.
     *
     * @see javax.speech.recognition.ResultEvent#RESULT_REJECTED
     * @see javax.speech.recognition.FinalResult
     * @see javax.speech.recognition.FinalRuleResult
     * @see javax.speech.recognition.FinalDictationResult
     */
    void resultRejected(ResultEvent e);

    /**
     * A RESULT_UPDATED event has occurred because a token has
     * been finalized and/or the unfinalized text of a result has changed.
     * <p>
     * The event is issued to each ResultListener attached
     * to the Recognizer, to each ResultListener
     * attached to the Result, and if the GRAMMAR_FINALIZED
     * event has already been released to each ResultListener
     * attached to the matched Grammar.
     *
     * @see javax.speech.recognition.ResultEvent#RESULT_UPDATED
     */
    void resultUpdated(ResultEvent e);

    /**
     * A TRAINING_INFO_RELEASED event has occurred.
     * This event is only issued to finalized results.
     * See the documentation of the isTrainingInfoAvailable method
     * the FinalResult interface for details.
     * <p>
     * The event is issued to each ResultListener attached
     * to the Recognizer and to the Result.
     * If a GRAMMAR_FINALIZED event was issued, then
     * the matched Grammar is known, and the
     * event is also issued to each ResultListener attached
     * to that Grammar.
     *
     * @see javax.speech.recognition.ResultEvent#TRAINING_INFO_RELEASED
     * @see javax.speech.recognition.FinalResult#isTrainingInfoAvailable()
     */
    void trainingInfoReleased(ResultEvent e);
}
