package javax.speech.recognition;

import java.io.Serializable;


/**
 * A Rule object is the basic component of a
 * RuleGrammar and represents anything that may appear
 * on the right-hand side of a rule definition in
 * Java Speech Grammar Format.  Technically a Rule
 * represents a JSGF "expansion".
 * <p>
 * Rule is an abstract class that is sub-classed by:
 * <p>
 * RuleAlternatives: set of alternatives Rule objects
 * RuleCount: contains a Rule that may occur optionally,
 * zero or more times, or one or more times.
 * RuleName: reference to a Rule
 * RuleSequence: set of rules that occur in sequence
 * RuleTag: contains a Rule tagged by a String
 * RuleToken: reference to a token that may be spoken.
 * <p>
 * Another sub-class of Rule is RuleParse which
 * is returned by the parse method of RuleGrammar to
 * represent the structure of parsed text.
 * <p>
 * Any Rule object can be converted to a partial
 * Java Speech Grammar Format String using its toString method.
 */
public abstract class Rule implements Serializable {

    /**
     * Return a deep copy of a Rule.
     * A deep copy implies that for a rule that contains
     * other rules (i.e. RuleAlternatives,
     * RuleCount, RuleParse,
     * RuleSequence, RuleTag)
     * the sub-rules are also copied.
     * Note: copy differs from the typical use of clone
     * because a clone is not normally a deep copy.
     */
    public abstract Rule copy();

    /**
     * Return a string representing the Rule in
     * partial Java Speech Grammar Format.
     * The String represents a portion of
     * Java Speech Grammar Format that could appear
     * on the right hand side of a rule definition.
     *
     * @return printable Java Speech Grammar Format string
     */
    public abstract String toString();
}
