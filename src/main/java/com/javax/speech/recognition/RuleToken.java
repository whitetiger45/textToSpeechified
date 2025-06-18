package javax.speech.recognition;

/**
 * RuleToken represents speakable text in a RuleGrammar.
 * It is the primitive type of a Rule (eventually any rule must break
 * down into a sequence of RuleTokens that may be spoken).  It is also the
 * primitive type of a RuleParse.
 */
public class RuleToken extends Rule {

    /**
     * The token text.
     *
     * @see javax.speech.recognition.RuleToken#getText()
     */
    protected String text;

    /**
     * Empty constructor sets token text to null.
     */
    public RuleToken() {
        this.setText(null);
    }

    /**
     * Construct a RuleToken with the speakable string.
     * The string should not include the surrounding quotes
     * or escapes of JSGF tokens (except as necessary to
     * properly format a Java string).
     */
    public RuleToken(String text) {
        this.setText(text);
    }

    private boolean containsWhiteSpace(String text) {
        for (int i = 0; i < text.length(); ++i) {
            if (Character.isWhitespace(text.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return a deep copy of this rule.
     */
    @Override
    public Rule copy() {
        return new RuleToken(this.text);
    }

    /**
     * Get the text of the token.
     * The returned string is not in JSGF format (backslash and quote
     * characters are not escaped and surrounding quote characters
     * are not included).
     * Use toString to obtain a JSGF-compliant string.
     *
     * @see javax.speech.recognition.RuleToken#toString()
     */
    public String getText() {
        return this.text;
    }

    /**
     * Set the text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Return a string representing the RuleToken in partial
     * Java Speech Grammar Format.  (It returns a String that could appear
     * as the right hand side of a rule definition.)  The string is quoted
     * if necessary (contains whitespace or escaped characters)
     * and the quote and backslash characters are escaped.
     */
    public String toString() {
        if (!this.containsWhiteSpace(this.text) && this.text.indexOf(92) < 0 && this.text.indexOf(34) < 0) {
            return this.text;
        } else {
            StringBuilder sb = new StringBuilder(this.text);

            for (int i = sb.length() - 1; i >= 0; --i) {
                char c;
                if ((c = sb.charAt(i)) == '"' || c == '\\') {
                    sb.insert(i, '\\');
                }
            }

            sb.insert(0, '"');
            sb.append('"');
            return sb.toString();
        }
    }
}
