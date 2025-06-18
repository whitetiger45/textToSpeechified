package javax.speech.recognition;

/**
 * Provides information on a finalized result
 * for an utterance that matches a DictationGrammar.
 * A finalized result is a result that is in either the
 * ACCEPTED or REJECTED state
 * (tested by the getResultState method of the
 * Result interface).
 * <p>
 * The FinalDictationResult interface extends the
 * Result and FinalResult interfaces
 * with a single method.  The getAlternativeTokens
 * method provides access to alternative guesses for tokens
 * in a dictation result.
 * <p>
 * Every Result object provided by a Recognizer
 * implements the FinalDictationResult and the
 * FinalRuleResult interfaces (and by inheritance the
 * FinalResult and Result interfaces).
 * However, the methods of FinalDictationResult
 * should only be called if the Result.getGrammar
 * method returns a DictationGrammar and once the
 * Result has been finalized with either an
 * RESULT_ACCEPTED or RESULT_REJECTED event.
 * Inappropriate calls will cause a ResultStateError.
 *
 * @see javax.speech.recognition.Result#ACCEPTED
 * @see javax.speech.recognition.Result#REJECTED
 * @see javax.speech.recognition.Result#getResultState()
 * @see javax.speech.recognition.DictationGrammar
 * @see javax.speech.recognition.Result
 * @see javax.speech.recognition.FinalResult
 * @see javax.speech.recognition.FinalRuleResult
 * @see javax.speech.recognition.ResultStateError
 */
public interface FinalDictationResult extends FinalResult {

    ResultToken[][] getAlternativeTokens(ResultToken fromToken, ResultToken toToken, int max) throws ResultStateError, IllegalArgumentException;
}
