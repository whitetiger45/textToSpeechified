package javax.speech.recognition;

import java.net.URL;


/**
 * Description of a problem found in a grammar usually bundled
 * with a GrammarException.  The grammar
 * may have been created programmatically or by loading a
 * Java Speech Grammar Format document.  Multiple
 * GrammarSyntaxDetail objects may be encapsulated
 * by a single GrammarException.
 * <p>
 * Depending on the type of error and the context in
 * which the error is identified, some or all of the
 * following information may be provided:
 * <p>
 * Grammar name,
 * Grammar location (URL or system resource),
 * Rule name in which error is found,
 * Import name,
 * Line number of error in JSGF,
 * Character number (within line) in JSGF,
 * A printable description string.
 * <p>
 * The following problems may be encountered when loading JSGF
 * from a URL or Reader, or through the
 * ruleForJSGF method of the RuleGrammar interface.
 * <p>
 * Missing or illegal grammar name declaration, or
 * grammar name doesn't match URL or file name.
 * missing URL or URL does not contain JSGF file.
 * Illegal import declaration.
 * Illegal rule name or token.
 * Missing semi-colon.
 * Redefinition of a rule.
 * Definition of a reserved rule name
 * (
 * NULL
 * ,
 * VOID
 * ).
 * Unclosed quotes, tags, comment, or rule name.
 * Unclosed grouping "()" or "[]".
 * Empty rule definition or empty alternative.
 * Missing or illegal weight on alternatives.
 * Illegal attachment of unary operators (count and tag).
 * Some other error.
 * <p>
 * When the commitChanges method of a Recognizer
 * is called, it performs addition checks to ensure all loaded grammars are
 * legal.  The following problems may be encountered:
 * <p>
 * Unable to resolve import because grammar or rule is not defined.
 * Reference to an undefined rule name.
 * Illegal recursion: a rule refers to itself illegally.
 * Only right recursion is allowed (defined by JSGF).
 * Ambiguous reference to imported rule.
 *
 * @see javax.speech.recognition.GrammarException
 * @see javax.speech.recognition.Recognizer#loadJSGF(java.io.Reader)
 * @see javax.speech.recognition.Recognizer#loadJSGF(java.net.URL, java.lang.String)
 * @see javax.speech.recognition.RuleGrammar#ruleForJSGF(java.lang.String)
 * @see javax.speech.recognition.Recognizer#commitChanges()
 */
public class GrammarSyntaxDetail {

    /**
     * Name of grammar in which problem is encountered.
     * May be null.
     */
    public String grammarName = null;

    /**
     * URL location of grammar in which problem is encountered.
     * May be null.
     */
    public URL grammarLocation = null;

    /**
     * Name of rule within grammar in which problem is encountered.
     * May be null.
     */
    public String ruleName = null;

    /**
     * Name in grammar import declaration in which problem is encountered.
     * May be null.
     */
    public RuleName importName = null;

    /**
     * Line number in JSGF file for problem.
     * Negative values indicate that the line number unknown.
     */
    public int lineNumber = -1;

    /**
     * Character number in line in JSGF file for problem.
     * Negative values indicate that the line number unknown.
     */
    public int charNumber = -1;

    /**
     * Printable string describing problem.
     * May be null.
     */
    public String message = null;

    /**
     * Empty constructor.
     */
    public GrammarSyntaxDetail() {
    }

    /**
     * Complete constructor describing a syntax problem.
     */
    public GrammarSyntaxDetail(String grammarName, URL grammarLocation, String ruleName, RuleName importName,
                               int lineNumber, int charNumber, String message) {
        this.grammarName = grammarName;
        this.grammarLocation = grammarLocation;
        this.ruleName = ruleName;
        this.importName = importName;
        this.lineNumber = lineNumber;
        this.charNumber = charNumber;
        this.message = message;
    }
}
