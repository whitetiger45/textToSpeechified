package javax.speech.recognition;

import javax.speech.SpeechException;


/**
 * Thrown if a problem is found with a Java Speech Grammar Format (JSGF)
 * file or with a RuleGrammar object derived from JSGF.
 * <p>
 * Grammar problems are typically identified and fixed during
 * application development.  This class provides information that allows
 * a debugging environment to handle the error.
 * <p>
 * The exception message is a printable string.  Recognizers may
 * optionally provide details of each syntax problem.
 */
public class GrammarException extends SpeechException {

    private GrammarSyntaxDetail[] details;

    /**
     * Constructs a GrammarException with no detail message.
     */
    public GrammarException() {
        this.details = null;
    }

    /**
     * Constructs a GrammarException with the specified detail message.
     *
     * @param s a printable detail message
     */
    public GrammarException(String s) {
        super(s);
        this.details = null;
    }

    /**
     * Constructs a GrammarException with the specified detail message
     * and an optional programmatic description of each error.
     *
     * @param s      a printable detail message
     * @param detail detail of each error encountered or null
     */
    public GrammarException(String s, GrammarSyntaxDetail[] detail) {
        super(s);
        this.details = detail;
    }

    /**
     * Add a syntax error description (appended to the existing array of details).
     */
    public void addDetail(GrammarSyntaxDetail detail) {
        GrammarSyntaxDetail[] details = this.details;
        if (details == null) {
            details = new GrammarSyntaxDetail[0];
        }

        this.details = new GrammarSyntaxDetail[details.length + 1];
        System.arraycopy(details, 0, this.details, 0, details.length);
        this.details[details.length] = detail;
    }

    /**
     * Return the list of grammar syntax problem descriptions.
     */
    public GrammarSyntaxDetail[] getDetails() {
        return this.details;
    }

    /**
     * Set the grammar syntax problem descriptions.
     */
    public void setDetails(GrammarSyntaxDetail[] details) {
        this.details = details;
    }
}
