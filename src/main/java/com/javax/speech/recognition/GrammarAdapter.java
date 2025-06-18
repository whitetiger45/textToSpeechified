package javax.speech.recognition;

/**
 * The adapter which receives grammar events.
 * The methods in this class are empty;  this class is provided as a
 * convenience for easily creating listeners by extending this class
 * and overriding only the methods of interest.
 */
public class GrammarAdapter implements GrammarListener {

    /**
     * A GRAMMAR_ACTIVATED event occurred.
     *
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_ACTIVATED
     */
    @Override
    public void grammarActivated(GrammarEvent e) {
    }

    /**
     * Event issued when a Recognizer has committed
     * changes to a Grammar.  The Grammar
     * interface documents how and when
     * <p>
     * <A href="Grammar.html#commit">changes are committed</A>
     * .
     *
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_CHANGES_COMMITTED
     */
    @Override
    public void grammarChangesCommitted(GrammarEvent e) {
    }

    /**
     * A GRAMMAR_DEACTIVATED event occurred.
     *
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_DEACTIVATED
     */
    @Override
    public void grammarDeactivated(GrammarEvent e) {
    }
}
