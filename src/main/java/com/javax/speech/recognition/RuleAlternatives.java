package javax.speech.recognition;

/**
 * RuleAlternatives represents a Rule composed of a
 * set of alternative sub-rules.  RuleAlternatives are used to
 * construct RuleGrammar objects.  A RuleAlternatives
 * object is spoken by saying one, and only one, of its sub-rules.
 * <p>
 * A RuleAlternatives object contains a set of zero or more
 * Rule objects.  A set of zero alternatives is equivalent to
 * <p>
 * VOID
 * (it is unspeakable).
 * <p>
 * Weights may be (optionally) assigned to each alternative rule.
 * The weights indicate the chance of each Rule being spoken.
 * The setWeights method defines the constraints upon weights.
 * If no weights are defined, then all alternatives are considered
 * equally likely.
 */
public class RuleAlternatives extends Rule {

    /**
     * Set of alternative Rule objects.
     */
    protected Rule[] rules;

    /**
     * Array of weights for each alternative Rule
     * or null if the rules are equally likely.
     * If non-null, the weights array must have an
     * identical length to the rules array.
     */
    protected float[] weights;

    /**
     * Empty constructor creates zero-length list of alternatives.
     * Use the setRules method or append method to
     * add alternatives.
     * <p>
     * A zero-length set of alternatives is equivalent to
     * <p>
     * VOID
     * (i.e. unspeakable).
     */
    public RuleAlternatives() {
        this.setRules(null);
        this.weights = null;
    }

    public RuleAlternatives(Rule rule) {
        Rule[] rules = new Rule[] {rule};
        this.setRules(rules);
        this.weights = null;
    }

    /**
     * Constructor for RuleAlternatives that produces a
     * phrase list from an array of String objects.  Each
     * string is used to create a single RuleToken object.
     * <p>
     * A string containing multiple words (e.g. "san francisco") is treated
     * as a single token.  If appropriate, an application should parse such
     * strings to produce separate tokens.
     * <p>
     * The phrase list may be zero-length or null.  This will produce an
     * empty set of alternatives which is equivalent to
     * VOID
     * (i.e. unspeakable).
     *
     * @param tokens a set of alternative tokens
     * @see javax.speech.recognition.RuleName#VOID
     */
    public RuleAlternatives(String[] tokens) {
        if (tokens == null) {
            this.weights = null;
        } else {
            this.rules = new Rule[tokens.length];

            for (int i = 0; i < tokens.length; ++i) {
                this.rules[i] = new RuleToken(tokens[i]);
            }

            this.weights = null;
        }
    }

    /**
     * Construct a RuleAlternatives object containing a single sub-rule.
     * The weights array is set to null.
     */
    public RuleAlternatives(Rule[] rules) {
        this.setRules(rules);
        this.weights = null;
    }

    /**
     * Construct a RuleAlternatives object with an array of
     * sub-rules and an array of weights.  The rules array and weights
     * array may be null.  If the weights array is non-null,
     * it must have identical length to the rules array.
     *
     * @param rules the set of alternative sub-rules
     * @param set   of weights for each rule or null
     * @throws java.lang.IllegalArgumentException Error in length of array, or the weight values (see setWeights).
     * @see javax.speech.recognition.RuleAlternatives#setWeights(float[])
     */
    public RuleAlternatives(Rule[] rules, float[] set) throws IllegalArgumentException {
        this.setRules(rules);
        this.setWeights(set);
    }

    /**
     * Append a single rule to the set of alternatives.
     * The weights are set to null.
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
            this.weights = null;
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
        float[] weights = null;
        if (this.weights != null) {
            weights = new float[this.weights.length];
            System.arraycopy(this.weights, 0, weights, 0, this.weights.length);
        }

        Rule[] rules = null;
        if (this.rules != null) {
            rules = new Rule[this.rules.length];

            for (int i = 0; i < this.rules.length; ++i) {
                rules[i] = this.rules[i].copy();
            }
        }

        return new RuleAlternatives(rules, weights);
    }

    /**
     * Return the array of alternative sub-rules.
     */
    public Rule[] getRules() {
        return this.rules;
    }

    /**
     * Return the array of weights.  May return null.
     * If non-null, the length of the weights array is guaranteed to be
     * the same length as the array of rules.
     */
    public float[] getWeights() {
        return this.weights;
    }

    /**
     * Set the array of alternative sub-rules.
     * <p>
     * If the weights are non-null and the number of rules is
     * not equal to the number of weights, the weights are set to null.
     * To change the number of rules and weights, call setRules before
     * setWeights.
     *
     * @see javax.speech.recognition.RuleAlternatives#setWeights(float[])
     */
    public void setRules(Rule[] rules) {
        if (rules == null) {
            rules = new Rule[0];
        }

        if (this.weights != null && rules.length != this.weights.length) {
            this.weights = null;
        }

        this.rules = rules;
    }

    /**
     * Set the array of weights for the rules.
     * The weights array may be null.
     * If the weights are null, then all alternatives are
     * assumed to be equally likely.
     * <p>
     * The length of the weights array must be the same length as
     * the array of rules.  The weights must all be greater than
     * or equal to 0.0 and at least one must be non-zero.
     * <p>
     * To change the number of rules and weights, first call
     * setRules.
     *
     * @throws java.lang.IllegalArgumentException Error in length of array or value of weights
     * @see javax.speech.recognition.RuleAlternatives#setRules(javax.speech.recognition.Rule[])
     */
    public void setWeights(float[] weights) throws IllegalArgumentException {
        if (weights != null && weights.length != 0) {
            if (weights.length != this.rules.length) {
                throw new IllegalArgumentException("weights/rules array length mismatch");
            } else {
                float weight = 0.0F;

                for (float v : weights) {
                    if (Float.isNaN(v)) {
                        throw new IllegalArgumentException("illegal weight value: NaN");
                    }

                    if (Float.isInfinite(v)) {
                        throw new IllegalArgumentException("illegal weight value: infinite");
                    }

                    if ((double) v < 0.0) {
                        throw new IllegalArgumentException("illegal weight value: negative");
                    }

                    weight += v;
                }

                if ((double) weight <= 0.0) {
                    throw new IllegalArgumentException("illegal weight values: all zero");
                } else {
                    this.weights = weights;
                }
            }
        } else {
            this.weights = null;
        }
    }

    /**
     * Return a String representing this object as partial
     * Java Speech Grammar Format.  The string is a legal right hand side
     * of a rule definition.
     */
    public String toString() {
        if (this.rules != null && this.rules.length != 0) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < this.rules.length; ++i) {
                if (i > 0) {
                    sb.append(" | ");
                }

                if (this.weights != null) {
                    sb.append("/").append(this.weights[i]).append("/ ");
                }

                if (this.rules[i] instanceof RuleAlternatives) {
                    sb.append("( ").append(this.rules[i].toString()).append(" )");
                } else {
                    sb.append(this.rules[i].toString());
                }
            }

            return sb.toString();
        } else {
            return "<VOID>";
        }
    }
}
