package javax.speech;

/**
 * The Word class provides a standard representation of
 * speakable words for speech engines.  A Word object
 * provides the following information:
 * <p>
 * "Written form" string: text that can be used to present the
 * Word visually.
 * "Spoken form" text: printable string that indicates how the
 * word is spoken.
 * Pronunciation: a string of phonetic characters indicating
 * how the word is spoken.
 * Grammatical categories: flags indicating grammatical
 * "part-of-speech" information.
 * <p>
 * The written form string is required.  The other properties are
 * optional.  Typically, one or more of the optional properties are
 * specified.  The Word class allows the specification
 * of multiple pronunciations and multiple grammatical categories.
 * Each pronunciation must be appropriate to each category.  If not,
 * separate Word objects should be created.
 * <p>
 * All the optional properties of a word are hints to the speech engine.
 * Speech engines will use the information as appropriate for their
 * internal design.
 *
 * @see javax.speech.VocabManager
 * @see javax.speech.Engine#getVocabManager()
 */
public class Word {

    private long categories;

    private String writtenForm;

    private String spokenForm;

    private String[] pronunciations;

    /**
     * Grammatical category of word is unknown.
     * The value is zero - 0 - and implies
     * that no other category flag is set.
     */
    public static final long UNKNOWN = 0L;

    /**
     * Grammatical category of word doesn't matter.
     */
    public static final long DONT_CARE = 1L;

    /**
     * Other grammatical category of word not specified elsewhere in this class.
     */
    public static final long OTHER = 2L;

    /**
     * Grammatical category of word is noun.
     * English examples: "car", "house", "elephant".
     */
    public static final long NOUN = 4L;

    /**
     * Grammatical category of word is proper noun.
     * English examples: "Yellowstone", "Singapore".
     */
    public static final long PROPER_NOUN = 8L;

    /**
     * Grammatical category of word is pronoun.
     * English examples: "me", "I", "they".
     */
    public static final long PRONOUN = 16L;

    /**
     * Grammatical category of word is verb.
     * English examples: "run", "debug", "integrate".
     */
    public static final long VERB = 32L;

    /**
     * Grammatical category of word is adverb.
     * English examples: "slowly", "loudly", "barely", "very", "never".
     */
    public static final long ADVERB = 64L;

    /**
     * Grammatical category of word is adjective.
     * English examples: "red", "mighty", "very", "first", "eighteenth".
     */
    public static final long ADJECTIVE = 128L;

    /**
     * Grammatical category of word is proper adjective.
     * English examples: "British", "Brazilian".
     */
    public static final long PROPER_ADJECTIVE = 256L;

    /**
     * Grammatical category of word is auxiliary.
     * English examples: "have", "do", "is", "shall", "must", "cannot".
     */
    public static final long AUXILIARY = 512L;

    /**
     * Grammatical category of word is determiner.
     * English examples: "the", "a", "some", "many", "his", "her".
     */
    public static final long DETERMINER = 1024L;

    /**
     * Grammatical category of word is cardinal.
     * English examples: "one", "two", "million".
     */
    public static final long CARDINAL = 2048L;

    /**
     * Grammatical category of word is conjunction.
     * English examples: "and", "or", "since", "if".
     */
    public static final long CONJUNCTION = 4096L;

    /**
     * Grammatical category of word is preposition.
     * English examples: "of", "for".
     */
    public static final long PREPOSITION = 8192L;

    /**
     * Grammatical category is contraction.
     * English examples: "don't", "can't".
     */
    public static final long CONTRACTION = 16384L;

    /**
     * Word is an abbreviation or acronym.
     * English examples: "Mr.", "USA".
     */
    public static final long ABBREVIATION = 32768L;

    /**
     * Get the categories of the Word.
     * Value may be UNKNOWN or an OR'ed set
     * of the categories defined by this class.
     */
    public long getCategories() {
        return this.categories;
    }

    /**
     * Get the pronunciations of the Word.
     * The pronunciation string uses the Unicode IPA subset.
     * Returns null if no pronunciations are specified.
     */
    public String[] getPronunciations() {
        return this.pronunciations;
    }

    /**
     * Get the "spoken form" of the Word.
     * Returns null if the spoken form is not defined.
     */
    public String getSpokenForm() {
        return this.spokenForm;
    }

    /**
     * Get the written form of the Word.
     */
    public String getWrittenForm() {
        return this.writtenForm;
    }

    /**
     * Set the categories of the Word.
     * The categories may be UNKNOWN or may be an
     * OR'ed set of the defined categories such as NOUN,
     * VERB, PREPOSITION.  For example:
     * <pre>
     * Word w = new Word("running");
     * w.setCategories(Word.NOUN | Word.VERB);
     * </pre>
     * The category information is a guide to the word's grammatical role.
     * Speech synthesizers can use
     * this information to improve phrasing and accenting.
     */
    public void setCategories(long cat) {
        this.categories = cat;
    }

    /**
     * Set the pronunciation of the Word as
     * an array containing a phonetic character String
     * for each pronunciation of the word.
     * <p>
     * The pronunciation string uses the IPA subset of Unicode.
     * <p>
     * The string should be null if no pronunciation is
     * available.  Speech engines should be expected to handle
     * most words of the language they support.
     * <p>
     * Recognizers can use pronunciation information to improve
     * recognition accuracy.  Synthesizers use the information to
     * accurately speak unusual words (e.g., foreign words).
     */
    public void setPronunciations(String[] pron) {
        this.pronunciations = pron;
    }

    /**
     * Set the "spoken form" of the Word.
     * May be null.
     * <p>
     * The spoken form of a word is useful for mapping the written form
     * to words that are likely to be handled by a speech recognizer or
     * synthesizer.  For example, "JavaSoft" to "java soft",
     * "toString" -> "to string", "IEEE" -> "I triple E".
     */
    public void setSpokenForm(String text) {
        this.spokenForm = text;
    }

    /**
     * Set the "written form" of the Word.
     * The written form text should be a string that could be
     * used to present the Word visually.
     */
    public void setWrittenForm(String text) {
        this.writtenForm = text;
    }
}
