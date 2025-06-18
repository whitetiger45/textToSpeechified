package javax.speech.recognition;

/**
 * RuleTag attaches a tag to a contained Rule object.
 * A tag is a string attached to any Rule entity.  The tag
 * does not affect the recognition of a RuleGrammar in which
 * it is used.  Instead tags are used to embed information into a grammar
 * that helps with processing of recognition results.
 * Tags are
 * <p>
 * Used in the definition of a RuleGrammar,
 * Included in parse output (RuleParse objects).
 * <p>
 * The tag string in the Java Speech Grammar Format allows the
 * backslash character to escape the curly brace character '}' or backslash.
 * The RuleTag class assumes that all such string
 * handling is handled separately.  The exception is toString
 * which is required to produce a JSGF-compliant string, and so escapes
 * special characters as required.
 * <p>
 * An empty tag in JSGF is "{}".
 * This tag is defined to be the zero-length string,  "".
 * A null tag is converted to a zero-length string.
 */
public class RuleTag extends Rule {

    /**
     * The tagged rule.
     *
     * @see javax.speech.recognition.RuleTag#getRule()
     */
    protected Rule rule;

    /**
     * The tag string for the rule.
     *
     * @see javax.speech.recognition.RuleTag#getTag()
     */
    protected String tag;

    /**
     * Empty constructor sets the rule and tag to null.
     */
    public RuleTag() {
        this.setRule(null);
        this.setTag(null);
    }

    /**
     * Construct a RuleTag with for Rule object with a tag string.
     * The method assumes that pre-processing of JSGF tags is complete (the leading
     * and trailing curly braces are removed, escape characters are removed).
     *
     * @param rule the rule being tagged
     * @param tag  the tag string
     */
    public RuleTag(Rule rule, String tag) {
        this.setRule(rule);
        this.setTag(tag);
    }

    /**
     * Return a deep copy of this rule.
     * See the
     * <A href="Rule.html#copy()">Rule.copy</A>
     * documentation for an explanation of deep copy.
     */
    @Override
    public Rule copy() {
        return new RuleTag(this.rule.copy(), this.tag);
    }

    private String escapeTag(String tag) {
        StringBuilder sb = new StringBuilder(tag);
        if (tag.indexOf('}') >= 0 || tag.indexOf('\\') >= 0) {
            for (int i = sb.length() - 1; i >= 0; --i) {
                char c = sb.charAt(i);
                if (c == '}' || c == '\\') {
                    sb.insert(i, '\\');
                }
            }
        }

        return sb.toString();
    }

    /**
     * Returns the Rule object being tagged.
     */
    public Rule getRule() {
        return this.rule;
    }

    /**
     * Returns the tag string.
     */
    public String getTag() {
        return this.tag;
    }

    /**
     * Set the Rule object to be tagged.
     */
    public void setRule(Rule rule) {
        this.rule = rule;
    }

    /**
     * Set the tag string for the Rule.
     * A zero-length string is legal.
     * A null tag is converted to "".
     */
    public void setTag(String tag) {
        if (tag == null) {
            this.tag = "";
        } else {
            this.tag = tag;
        }
    }

    /**
     * Return a String representing the RuleTag object in
     * partial Java Speech Grammar Format.
     * <p>
     * Any backslash or closing angle brackets within the tag will be
     * properly escaped by a backslash.  If required, the rule contained
     * within the RuleTag will enclosed by parentheses.
     */
    public String toString() {
        String s = " {" + this.escapeTag(this.tag) + "}";
        return !(this.rule instanceof RuleToken) && !(this.rule instanceof RuleName) ? "(" + this.rule.toString() + ")" + s : this.rule + s;
    }
}
