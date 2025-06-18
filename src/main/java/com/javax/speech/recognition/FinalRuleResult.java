package javax.speech.recognition;

/**
 * Provides information on a finalized result for an utterance that matches
 * a RuleGrammar.  A finalized result is one that is in
 * either the ACCEPTED or REJECTED state
 * (tested by the getResultState method of the
 * Result interface).
 * <p>
 * This interface provides the following information for
 * a finalized result for a RuleGrammar (in addition
 * to the information provided through the Result and
 * FinalResult interfaces):
 * <p>
 * N-best alternatives for the entire result.  For each alternative
 * guess the interface provides the token sequence and the names
 * of the grammar and rule matched by the tokens.
 * Tags for the best-guess result.
 * <p>
 * Every Result object provided by a Recognizer
 * implements the FinalRuleResult and the
 * FinalDictationResult interfaces (and by inheritance the
 * FinalResult and Result interfaces).
 * However, the methods of FinalRuleResult
 * should only be called if the Result.getGrammar
 * method returns a RuleGrammar and once the
 * Result has been finalized with either an
 * RESULT_ACCEPTED or RESULT_REJECTED event.
 * Inappropriate calls will cause a ResultStateError.
 *
 * @see javax.speech.recognition.RuleGrammar
 * @see javax.speech.recognition.Result
 * @see javax.speech.recognition.FinalResult
 * @see javax.speech.recognition.FinalDictationResult
 * @see javax.speech.recognition.Result#getResultState()
 * @see javax.speech.recognition.Result#getGrammar()
 */
public interface FinalRuleResult extends FinalResult {

    /**
     * Get the N-best token sequence for this result.  The nBest
     * value should be in the range of 0 to
     * (getNumberGuesses()-1) inclusive.  For out-of-range
     * values, the method returns null.
     * <p>
     * If nBest==0, the method returns the best-guess sequence
     * of tokens - identical to the token sequence returned by the
     * getBestTokens method of the Result interface
     * for the same object.
     * <p>
     * If nBest==1 (or 2, 3...) the method returns the 1st (2nd, 3rd...)
     * alternative to the best guess.
     * <p>
     * The number of tokens for the best guess and the alternatives do
     * not need to be the same.
     * <p>
     * The getRuleGrammar(int) and getRuleName(int)
     * methods indicate which RuleGrammar and ruleName
     * are matched by each alternative result sequence.
     * <p>
     * If the Result is in the ACCEPTED state (not rejected),
     * then the best guess and all the alternatives are accepted.  Moreover,
     * each alternative set of tokens must be a legal match of the
     * RuleGrammar and RuleName returned by
     * the getRuleGrammar and getRuleName methods.
     * <p>
     * If the Result is in the REJECTED state (not accepted),
     * the recognizer is not confident that the best guess or any of the
     * alternatives are what the user said.  Rejected guesses do not need
     * to match the corresponding RuleGrammar and rule name.
     * <p>
     * Example
     * <p>
     * Say we have two simple grammars loaded and active for recognition.
     * The first is grammar.numbers with a public rule
     * digits that matches spoken digit sequences
     * (e.g. "1 2 3 4").  The grammar is grammar.food
     * with the rule whoAteWhat
     * which matches statements about eating (e.g. "he ate mine").
     * [Yes, this is artificial!]
     * <p>
     * The user says "two eight nine" and the recognizer
     * correctly recognizes the speech, but also provides
     * 2 alternatives.  The
     * <p>
     * FinalRuleResult result = ...;
     * result.getNumberGuesses() -
     * 3
     * <p>
     * result.getAlternativeTokens(0) -
     * two eight nine // array of tokens
     * result.getRuleGrammar(0) -
     * [reference to grammar.numbers]
     * result.getRuleName(0) -
     * "digits"
     * <p>
     * result.getAlternativeTokens(1) -
     * you ate mine
     * result.getRuleGrammar(1) -
     * [reference to grammar.food]
     * result.getRuleName(1) -
     * "whoAteWhat"
     * <p>
     * result.getAlternativeTokens(2) -
     * two eight five
     * result.getRuleGrammar(2) -
     * [reference to grammar.numbers]
     * result.getRuleName(2) -
     * "digits"
     *
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.FinalRuleResult#getRuleGrammar(int)
     * @see javax.speech.recognition.FinalRuleResult#getRuleName(int)
     */
    ResultToken[] getAlternativeTokens(int nBest) throws ResultStateError;

    /**
     * Return the number of guesses for this result.  The guesses
     * are numbered from 0 up.  The 0th guess is the best guess for
     * the result and provides the same tokens as the getBestTokens
     * method of the Result interface.
     * <p>
     * If only the best guess is available (no alternatives) the return
     * value is 1.  If the result is was rejected (REJECTED
     * state), the return value may be 0 if no tokens are available.
     * If a best guess plus alternatives are available, the return value
     * is greater than 1.
     * <p>
     * The integer parameter to the getAlternativeTokens,
     * getRuleGrammar and getRuleName methods
     * are indexed from 0 to (getNumberGuesses()-1).
     *
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.Result#getBestTokens()
     * @see javax.speech.recognition.Result#REJECTED
     * @see javax.speech.recognition.FinalRuleResult#getAlternativeTokens(int)
     * @see javax.speech.recognition.FinalRuleResult#getRuleGrammar(int)
     * @see javax.speech.recognition.FinalRuleResult#getRuleName(int)
     */
    int getNumberGuesses() throws ResultStateError;

    /**
     * Return the RuleGrammar matched by the nth guess.
     * Return null if nBest is out-of-range.
     * <p>
     * getRuleName returns the rule matched in the
     * RuleGrammar.  See the documentation for
     * getAlternativeTokens for a description of how
     * tokens, grammars and rules correspond.
     * <p>
     * An application can use the parse method of the
     * matched grammar to analyse a result.  e.g.
     * <p>
     * int nBest = 2;
     * FinalRuleResult res;
     * RuleParse parse = res.getRuleGrammar(nbest).parse(
     * res.getAlternativeTokens(nBest),
     * res.getRuleName(nBest));
     *
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.RuleGrammar#parse(java.lang.String, java.lang.String)
     * @see javax.speech.recognition.FinalRuleResult#getNumberGuesses()
     * @see javax.speech.recognition.FinalRuleResult#getAlternativeTokens(int)
     * @see javax.speech.recognition.FinalRuleResult#getRuleName(int)
     */
    RuleGrammar getRuleGrammar(int nBest) throws ResultStateError;

    /**
     * Return the RuleName matched by the nth guess.
     * Return null if nBest is out-of-range.
     * Typically used in combination with getAlternativeTokens
     * and getRuleGrammar which return the corresponding
     * tokens and grammar.
     * <p>
     * The documentation for getAlternativeTokens shows
     * and example result with alternatives.
     *
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.RuleGrammar#parse(java.lang.String, java.lang.String)
     * @see javax.speech.recognition.FinalRuleResult#getAlternativeTokens(int)
     * @see javax.speech.recognition.FinalRuleResult#getRuleGrammar(int)
     */
    String getRuleName(int nBest) throws ResultStateError;

    /**
     * Return the list of tags matched by the best-guess token sequence.
     * The tags in the array are ordered strictly in the left to right
     * order in which they would appear in JSGF.
     * <p>
     * For example, if the following
     * simple Java Speech Grammar Format (JSGF) rule is active:
     * <p>
     * public  = (open {ACT_OPEN} | close {ACT_CLOSE}) [(it{WHAT} now) {NOW}];
     * <p>
     * and the user says "close it now", then getTags returns
     * an array containing {"ACT_CLOSE", "WHAT", "NOW"}.
     * Note how both the {WHAT} and {NOW} tags
     * are attached starting from the word "it" but that {TAG}
     * appears first in the array.  In effect, when tags start at the
     * same token, they are listed "bottom-up".
     * <p>
     * getTags does not indicate which tokens are matched by
     * which tags.  To obtain this information use the parse
     * method of the RuleGrammar.  Also, getTags
     * only provides tags for the best guess.  To get tags for the
     * alternative guesses using parsing through the RuleGrammar.
     * <p>
     * The string array returned by the getTags method of the
     * RuleParse object returned by parsing the best-guess
     * token sequence will be the same as returned by this method.
     *
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.RuleGrammar#parse(java.lang.String, java.lang.String)
     * @see javax.speech.recognition.RuleParse#getTags()
     */
    String[] getTags() throws ResultStateError;
}
