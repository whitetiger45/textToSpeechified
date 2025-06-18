/*
 * JavaTM Speech API
 *
 * Copyright 1997-1998 Sun Microsystems, Inc. All rights reserved
 */

package javax.speech.recognition;

/**
 * Provides access to the dictation capabilities of a
 * Recognizer.  If a recognizer supports dictation,
 * it provides a DictationGrammar which is obtained
 * through the getDictationGrammar method of the
 * Recognizer.
 * <p>
 * A DictationGrammar is named with the same
 * convention as a RuleGrammar and will typically
 * reflect the language and domain it supports.  The grammar
 * name is obtained through the Grammar.getName
 * method.  For example, the general dictation for
 * US English from Acme speech company might be called:
 * <p>
 * com.acme.dictation.english.us.general
 * <p>
 * A DictationGrammar is characterized by:
 * <p>
 * Typically large vocabulary.
 * Grammar is built-into the recognizer,
 * General purpose or domain-specific (e.g. legal, radiology, medical),
 * May support continuous or discrete speech,
 * <p>
 * A dictation grammar is built into a recognizer (if supported).
 * Moreover, a recognizer provides a single dictation grammar.
 * Applications cannot create or directly modify the grammar beyond
 * the relatively simple methods provided by this interface.  By comparison,
 * an application can change any part of a RuleGrammar.
 * (Some vendors provide tools for constructing dictation grammars,
 * but these tools operate separate from the Java Speech API.)
 * <p>
 * A recognizer that supports dictation:
 * <p>
 * Returns true for the
 * RecognizerModeDesc.isDictationGrammarSupported method
 * This value can be used to select a dictation recognizer.
 * Typically resource intensive (CPU, disk, memory),
 * Often requires training by user
 * (supports the SpeakerManager interface).
 * <p>
 * DictationGrammar Extends Grammar
 * <p>
 * The DictationGrammar interface extends the
 * Grammar interface.  Thus, a DictationGrammar
 * provides all the Grammar functionality:
 * <p>
 * The dictation grammar name is returned by the
 * Grammar.getName method.
 * The dictation grammar is enabled and disabled for recognition
 * by the setEnabled method inherited from Grammar.
 * The activation mode is set through the
 * setActivationMode method of Grammar.
 * Note: a DictationGrammar should never
 * have GLOBAL activation mode.
 * The current activation state of the Grammar
 * is tested by the isActive method and the
 * GRAMMAR_ACTIVATED and
 * GRAMMAR_DEACTIVATED events.
 * Grammar listeners are attached and removed by the
 * Grammar.addGrammarListener and
 * Grammar.removeGrammarListener methods.
 * <p>
 * <A>Context</A>
 * <p>
 * Dictation recognizers can use the current textual context to improve
 * recognition accuracy.  Applications should use either of the
 * setContext methods to inform the recognizer each time
 * the context changes.  For example, when editing this
 * sentence with the cursor after the word "sentence", the preceding context is
 * "When editing this sentence" and the following context is "with the cursor after...".
 * Any time the text context changes, the application should inform the recognizer.
 * <p>
 * Applications should provide a minimum of 3 or 4 words of context (when it's
 * available).  Different recognizers process context differently.
 * Some recognizers can take advantage of several paragraphs of context
 * others look only at a few words.
 * <p>
 * One form of setContext takes one string each for preceding
 * and following context (as in the example above).  The other form of
 * setContext takes an array each of strings for preceding and
 * following context.  Arrays should be provided if the surrounding context
 * is made of tokens from previous recognition results.
 * When possible, providing tokens is the preferred method
 * because recognizers are able to use token information more
 * reliably and more efficiently.
 * <p>
 * Word Lists
 * <p>
 * Words can be added to and removed from the DictationGrammar
 * using the addWord and removeWord methods.
 * Lists of the added and removed words are available through the
 * listAddedWords and listRemovedWords methods.
 * In a speaker-adaptive system (SpeakerManager supported)
 * word data is stored as part of the speaker's data.
 * <p>
 * Adding a word allows a recognizer to learn new words.  Removing
 * a word is useful when a recognizer consistently misrecognizes
 * similar sounding words.  For example, if each time a user says
 * "grammar", the recognizer hears "grandma", the user can remove
 * grandma (assuming they don't want to use the word "grandma").
 *
 * @see javax.speech.recognition.SpeakerManager
 * @see javax.speech.recognition.Recognizer
 * @see javax.speech.recognition.Grammar
 */
public interface DictationGrammar extends Grammar {

    /**
     * Add a word to the DictationGrammar.
     * The addWord method can undo
     * the effects of an removeWord call.
     * <p>
     * A change in a DictationGrammar is applied
     * to the recognition process only after
     * <p>
     * <A href="Grammar.html#commit">changes have been committed</A>.
     *
     * @see javax.speech.recognition.Recognizer#commitChanges()
     */
    void addWord(String word);

    /**
     * List the words that have been added to the DictationGrammar.
     */
    String[] listAddedWords();

    /**
     * List the words that have been removed from the DictationGrammar.
     */
    String[] listRemovedWords();

    /**
     * Remove a word from the DictationGrammar.
     * The removeWord method can undo
     * the effects of an addWord call.
     * <p>
     * A change in a DictationGrammar is applied
     * to the recognition process only after
     * <p>
     * <A href="Grammar.html#commit">changes have been committed</A>.
     *
     * @see javax.speech.recognition.Recognizer#commitChanges()
     */
    void removeWord(String word);

    /**
     * Provide the recognition engine with the current textual context
     * with arrays of the previous and following tokens.  Dictation
     * recognizers use the context information to improve recognition
     * accuracy.  (Context is <A href="#context">discussed above.</A>)
     * <p>
     * When dictating a sequence of words, the recognizer updates its
     * context.  The app does not need to inform the recognizer when
     * results arrive.  Instead it should call setContext for
     * events such as cursor movement, cut, paste etc.
     * <p>
     * The preceding or following context may be null
     * if there is no preceding or following context.  This is appropriate
     * for a new document or in situations where context is not clear.
     *
     * @see javax.speech.recognition.DictationGrammar#setContext(java.lang.String, java.lang.String)
     */
    void setContext(String preceding, String following);

    void setContext(String[] preceding, String[] following);
}
