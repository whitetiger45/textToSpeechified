package javax.speech;

/**
 * Interface for management of words used by a speech Engine.
 * The VocabManager for an Engine is
 * returned by the getVocabManager method of
 * the Engine interface.  Engines are not required
 * support the VocabManager - the getVocabManager
 * manager may return null.
 * <p>
 * Words, technically known as tokens, are provided to the vocabulary
 * manager with optional information about their pronunciation,
 * grammatical role and spoken form.
 * <p>
 * The VocabManager is typically used to provide a
 * speech engine with information on problematic words - usually
 * words for which the engine is unable to guess a pronunciation.
 * For debugging purposes, an Engine may provide a
 * list of words it finds difficult through the
 * listProblemWords method.
 * <p>
 * Words in the vocabulary manager can be used as tokens in
 * rule grammars for recognizers.
 *
 * @see javax.speech.Engine#getVocabManager()
 * @see javax.speech.Word
 */
public interface VocabManager {

    /**
     * Add a word to the vocabulary.
     */
    void addWord(Word w);

    /**
     * Add an array of words to the vocabulary.
     */
    void addWords(Word[] w);

    /**
     * Get all words from the vocabulary manager matching text.
     * Returns null if there are no matches.
     * If text is null all words are returned.
     * This method only returns words that have been added
     * by the addWord methods - it does not provide
     * access to the engine's internal word lists.
     *
     * @param text word requested from VocabManager
     * @return list of Words matching text
     */
    Word[] getWords(String text);

    /**
     * Returns a list of problematic words encountered during
     * a session of using a speech recognizer or synthesizer.
     * This return information is intended for development use
     * (so the application can be enhanced to provide vocabulary
     * information).  An engine may return null.
     * <p>
     * If a pronunciation for a problem word is provided through the
     * addWord methods, the engine may remove
     * the word from the problem list.
     * <p>
     * An engine may (optionally) include its best-guess pronunciations
     * for problem words in the return array allowing the developer
     * to fix (rather than create) the pronunciation.
     */
    Word[] listProblemWords();

    /**
     * Remove a word from the vocabulary.
     *
     * @throws java.lang.IllegalArgumentException Word is not known to the VocabManager.
     */
    void removeWord(Word w) throws IllegalArgumentException;

    /**
     * Remove an array of words from the vocabulary.
     * To remove a set of words it is often useful to
     * <p>
     * removeWords(getWords("matching"));
     *
     * @throws java.lang.IllegalArgumentException Word is not known to the VocabManager.
     */
    void removeWords(Word[] w) throws IllegalArgumentException;
}
