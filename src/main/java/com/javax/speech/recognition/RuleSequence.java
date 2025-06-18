package javax.speech.recognition;

/**
 * RuleSequence is a Rule composed of a sequence of
 * sub-rules that must each be spoken in order.  If there are zero rules in the
 * sequence, the sequence is equivalent to
 * <p>
 * NULL
 * .
 */
public class RuleSequence extends Rule {

    /**
     * Set of rules to be spoken in sequence.
     *
     * @see javax.speech.recognition.RuleSequence#getRules()
     */
    protected Rule[] rules;

    /**
     * Empty constructor creates a sequence with zero rules.
     * A sequence with zero rules is equivalent to
     * <p>
     * NULL
     * .
     *
     * @see javax.speech.recognition.RuleName#NULL
     */
    public RuleSequence() {
        this.setRules(null);
    }

    public RuleSequence(Rule rule) {
        Rule[] rules = new Rule[] {rule};
        this.setRules(rules);
    }

    /**
     * Constructor for RuleSequence that is a sequence of
     * strings that are converted to RuleTokens.
     * <p>
     * A string containing multiple words (e.g. "san francisco") is treated as
     * a single token. If appropriate, an application should parse such strings
     * to produce separate tokens.
     * <p>
     * The token list may be zero-length or null. This will produce a zero-length
     * sequence which is equivalent to
     * NULL
     * .
     *
     * @see javax.speech.recognition.RuleToken
     */
    public RuleSequence(String[] tokens) {
        if (tokens == null) {
            tokens = new String[0];
        }

        this.rules = new Rule[tokens.length];

        for (int i = 0; i < tokens.length; ++i) {
            this.rules[i] = new RuleToken(tokens[i]);
        }
    }

    /**
     * Construct a RuleSequence object containing a single Rule.
     */
    public RuleSequence(Rule[] rules) {
        this.setRules(rules);
    }

    /**
     * Append a single rule to the end of the sequence.
     */
    public void append(Rule rule) {
        if (rule == null) {
            throw new NullPointerException("null rule to append");
        } else {
            int length = this.rules.length;
            Rule[] nextRule = new Rule[length + 1];
            System.arraycopy(this.rules, 0, nextRule, 0, length);
            nextRule[length] = rule;
            this.rules = nextRule;
        }
    }

    /**
     * Return a deep copy of this rule.
     * See the
     * <A href="Rule.html#copy()">Rule.copy</A>
     * documentation for an explanation of deep copy.
     */
    @Override
    public Rule copy() {
        Rule[] rule = null;
        if (this.rules != null) {
            rule = new Rule[this.rules.length];

            for (int i = 0; i < this.rules.length; ++i) {
                rule[i] = this.rules[i].copy();
            }
        }

        return new RuleSequence(rule);
    }

    /**
     * Return the array of rules in the sequence.
     */
    public Rule[] getRules() {
        return this.rules;
    }

    /**
     * Set the array of rules in the sequence.
     * The array may be zero-length or null. This will produce a zero-length
     * sequence which is equivalent to
     * NULL
     * .
     */
    public void setRules(Rule[] rules) {
        if (rules == null) {
            rules = new Rule[0];
        }

        this.rules = rules;
    }

    /**
     * Return a String representing this RuleSequence object as partial
     * Java Speech Grammar Format.
     */
    public String toString() {
        if (this.rules.length == 0) {
            return "<NULL>";
        } else {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < this.rules.length; ++i) {
                if (i > 0) {
                    sb.append(' ');
                }

                if (!(this.rules[i] instanceof RuleAlternatives) && !(this.rules[i] instanceof RuleSequence)) {
                    sb.append(this.rules[i].toString());
                } else {
                    sb.append("( ").append(this.rules[i].toString()).append(" )");
                }
            }

            return sb.toString();
        }
    }
}
