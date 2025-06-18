package javax.speech.recognition;

import javax.speech.SpeechError;


/**
 * Signals an error caused by an illegal call to a method
 * of FinalResult, FinalRuleResult
 * or FinalDictationResult.
 * <p>
 * Methods of these three interfaces of a result can only
 * be called for a finalized Result.  The
 * getResultState method of the Result
 * interface tests the result state and must return
 * either ACCEPTED or REJECTED.
 * Furthermore, the methods of FinalRuleResult
 * should only be called for a finalized result matching
 * a RuleGrammar.  Similarly, the methods of
 * FinalDictationResult should only
 * be called for a finalized result matching
 * a DictationGrammar.
 */
public class ResultStateError extends SpeechError {

    /**
     * Empty constructor for ResultStateError.
     */
    public ResultStateError() {
    }

    /**
     * Construct a ResultStateError with a message string.
     */
    public ResultStateError(String s) {
        super(s);
    }
}
