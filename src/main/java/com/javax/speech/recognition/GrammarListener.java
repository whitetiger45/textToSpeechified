package javax.speech.recognition;

import java.util.EventListener;


/**
 * A GrammarListener receives notifications
 * of status change events for a Grammar.
 * <p>
 * A GrammarListener is attached to and removed
 * from a Grammar with its addGrammarListener
 * and removeGrammarListener methods.
 * Multiple grammars can share a GrammarListener object
 * and one grammar can have multiple
 * GrammarListener objects attached.
 * <p>
 * The GrammarAdapter provides a trivial implementation
 * of this class.
 *
 * @see javax.speech.recognition.Grammar
 * @see javax.speech.recognition.DictationGrammar
 * @see javax.speech.recognition.RuleGrammar
 * @see javax.speech.recognition.Grammar#addGrammarListener(javax.speech.recognition.GrammarListener)
 * @see javax.speech.recognition.Grammar#removeGrammarListener(javax.speech.recognition.GrammarListener)
 * @see javax.speech.recognition.GrammarAdapter
 */
public interface GrammarListener extends EventListener {

    /**
     * A GRAMMAR_ACTIVATED event is issued when a
     * grammar changes from deactivated to activate.
     *
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_ACTIVATED
     * @see javax.speech.recognition.Grammar
     */
    void grammarActivated(GrammarEvent e);

    /**
     * A GRAMMAR_CHANGES_COMMITTED event is issued when a
     * Recognizer has
     * <A href="Grammar.html#commit">
     * committed changes</A>
     * to a Grammar.
     * <p>
     * The GRAMMAR_CHANGES_COMMITTED immediately follows
     * the CHANGES_COMMITTED event issued to
     * RecognizerListeners after changes to all
     * grammars have been committed.  The circumstances in which
     * <p>
     * <A href="Grammar.html#commit">changes are committed</A>
     * are described in the documentation for Grammar.
     *
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_CHANGES_COMMITTED
     * @see javax.speech.recognition.RecognizerEvent#CHANGES_COMMITTED
     * @see javax.speech.recognition.Grammar
     */
    void grammarChangesCommitted(GrammarEvent e);

    /**
     * A GRAMMAR_DEACTIVATED event is issued when a
     * grammar changes from activated to deactivate.
     *
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_DEACTIVATED
     * @see javax.speech.recognition.Grammar
     */
    void grammarDeactivated(GrammarEvent e);
}
