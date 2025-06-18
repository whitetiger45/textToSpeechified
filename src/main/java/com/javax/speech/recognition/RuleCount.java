package javax.speech.recognition;

/**
 * Attaches a count to a contained Rule object to
 * indicate the number of times it may occur.  The contained
 * rule may occur optionally (zero or one times), one or more times,
 * or zero or more times.  The three count are equivalent to
 * the "[]", "+" and "*" operators of the Java Speech Grammar Format.
 * <p>
 * Any Rule not contained by a RuleCount object
 * occurs once only.
 */
public class RuleCount extends Rule {

    /**
     * The rule to which the count applies.
     */
    protected Rule rule;

    /**
     * Identifier for the rule count.
     *
     * @see javax.speech.recognition.RuleCount#OPTIONAL
     * @see javax.speech.recognition.RuleCount#ONCE_OR_MORE
     * @see javax.speech.recognition.RuleCount#ZERO_OR_MORE
     */
    protected int count;

    /**
     * OPTIONAL indicates that the Rule is optional:
     * zero or one occurrences.  An optional Rule is surrounded
     * by "[]" in Java Speech Grammar Format.
     *
     * @see javax.speech.recognition.RuleCount#getCount()
     */
    public static int OPTIONAL = 2;

    /**
     * ONCE_OR_MORE indicates that the Rule may be
     * spoken one or more times.  This is indicated by the "+" operator in
     * Java Speech Grammar Format.
     *
     * @see javax.speech.recognition.RuleCount#getCount()
     */
    public static int ONCE_OR_MORE = 3;

    /**
     * ZERO_OR_MORE indicates that the Rule may be
     * spoken zero or more times.  This is indicated by the "*" operator in
     * Java Speech Grammar Format.
     *
     * @see javax.speech.recognition.RuleCount#getCount()
     */
    public static int ZERO_OR_MORE = 4;

    /**
     * Empty constructor sets rule to null and count to OPTIONAL.
     */
    public RuleCount() {
        this.setRule(null);
        this.setCount(OPTIONAL);
    }

    /**
     * RuleCount constructor with contained rule and count.
     */
    public RuleCount(Rule rule, int count) {
        this.setRule(rule);
        this.setCount(count);
    }

    /**
     * Return a deep copy of this rule.
     * See the
     * <A href="Rule.html#copy()">Rule.copy</A>
     * documentation for an explanation of deep copy.
     */
    @Override
    public Rule copy() {
        return new RuleCount(this.rule.copy(), this.count);
    }

    /**
     * Returns the count: OPTIONAL, ZERO_OR_MORE,
     * ONCE_OR_MORE.
     *
     * @see javax.speech.recognition.RuleCount#OPTIONAL
     * @see javax.speech.recognition.RuleCount#ZERO_OR_MORE
     * @see javax.speech.recognition.RuleCount#ONCE_OR_MORE
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Returns the contained Rule object.
     */
    public Rule getRule() {
        return this.rule;
    }

    /**
     * Set the count.  If count is not one of the defined values
     * OPTIONAL, ZERO_OR_MORE, ONCE_OR_MORE)
     * the call is ignored.
     *
     * @see javax.speech.recognition.RuleCount#OPTIONAL
     * @see javax.speech.recognition.RuleCount#ZERO_OR_MORE
     * @see javax.speech.recognition.RuleCount#ONCE_OR_MORE
     */
    public void setCount(int count) {
        if (count == OPTIONAL || count == ZERO_OR_MORE || count == ONCE_OR_MORE) {
            this.count = count;
        }
    }

    /**
     * Set the contained Rule object.
     */
    public void setRule(Rule rule) {
        this.rule = rule;
    }

    /**
     * Return a string representing the RuleCount object in
     * partial Java Speech Grammar Format.  The String represents the portion
     * of Java Speech Grammar Format that could appear on the right hand side
     * of a rule definition.  Parenthesis will be placed
     * around the contained Rule object if required.
     * The output appears as one of:
     * <p>
     * [ruleString]   // OPTIONAL
     * ruleString *   // ZERO_OR_MORE
     * ruleString +   // ONCE_OR_MORE
     */
    public String toString() {
        if (this.count == OPTIONAL) {
            return '[' + this.rule.toString() + ']';
        } else {
            String ruleString;
            if (!(this.rule instanceof RuleToken) && !(this.rule instanceof RuleName)) {
                ruleString = '(' + this.rule.toString() + ')';
            } else {
                ruleString = this.rule.toString();
            }

            if (this.count == ZERO_OR_MORE) {
                return ruleString + " *";
            } else {
                return this.count == ONCE_OR_MORE ? ruleString + " +" : ruleString + "???";
            }
        }
    }
}
