package javax.speech.recognition;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the output of a parse of a Result
 * or a string against a RuleGrammar.  The RuleParse
 * object indicates how the result or text matches to the rules of the
 * RuleGrammar and the rules imported by that grammar.
 * <p>
 * The RuleParse structure returned by parsing closely matches
 * the structure of the grammar it is parsed against: if the grammar
 * contains RuleTag, RuleSequence, RuleToken
 * objects and so on, the returned RuleParse will contain paired objects.
 * <p>
 * The RuleParse object itself represents the match of text to
 * a named rule or to any rule referenced within a rule.  The rulename field
 * of the RuleParse is the fully-qualified name of the
 * rule being matched.  The Rule field of the RuleParse
 * represents the parse structure (how that rulename is matched).
 * <p>
 * The expansion (or logical structure) of a RuleParse matches
 * the structure of the definition of the rule being parsed.  The following
 * indicates the mapping of an entity in the rule being parsed to the
 * paired object in the RuleParse.
 * <p>
 * RuleAlternatives:
 * maps to a RuleAlternatives object containing a
 * single Rule object for the one entity in the
 * set of alternatives that is matched.
 * RuleCount: maps to a RuleSequence
 * containing a Rule for each match of the rule
 * contained by RuleCount.  The sequence may contain
 * zero, one or multiple rule matches (for optional, zero-or-more
 * or one-or-more operators).
 * RuleName: maps to a RuleParse
 * indicating how the referenced rule was matched.  The rulename
 * field of the RuleParse is the matched RuleName.
 * The exception for NULL is described below.
 * RuleSequence: maps to a RuleSequence with
 * a matching rule for each rule in the original sequence.
 * RuleTag: maps to a matching RuleTag
 * (same tag) with a match of the contained rule.
 * RuleToken: maps to an identical RuleToken object.
 * <p>
 * [Note: the RuleParse object is never used in defining
 * a RuleGrammar so it doesn't need to be matched.]
 * <p>
 * If a RuleName object in a grammar is
 * NULL
 * ,
 * then the RuleParse contains the
 * NULL
 * object too.
 * <p>
 * Example
 * <p>
 * Consider a simple grammar:
 * <p>
 * public
 * command
 * =
 * action
 * <p>
 * object
 * [
 * polite
 * ]
 * <p>
 * action
 * = open {OP} | close {CL} | move {MV};
 * <p>
 * object
 * = [
 * this_that_etc
 * ] (window | door);
 * <p>
 * this_that_etc
 * = a | the | this | that | the current;
 * <p>
 * polite
 * = please | kindly;
 * <p>
 * We will analyze the parse of "close that door please" against
 * <p>
 * command
 * rule which is returned by the parse
 * method of the RuleGrammar against the
 * command
 * rule:
 * <p>
 * ruleParse = ruleGrammar.parse("close that door please", "command");
 * <p>
 * The call returns a RuleParse that is the match of
 * "close that door please" against
 * command
 * .
 * <p>
 * Because
 * command
 * is defined as a sequence of
 * 3 entities (action, object and optional polite), the RuleParse
 * will contain a RuleSequence with length 3.
 * <p>
 * The first two entities in
 * command
 * are
 * RuleNames, so the first two entities in the parse's
 * RuleSequence will be RuleParse objects with
 * rulenames of "action" and "object".
 * <p>
 * The third entity in
 * command
 * is an optional
 * RuleName (a RuleCount containing
 * a RuleName), so the third entity in the sequence is a
 * RuleSequence containing a single RuleParse
 * indicating how the
 * polite
 * rule is matched.
 * (Recall that a RuleCount object maps to a RuleSequence).
 * <p>
 * The RuleParse for
 * polite
 * will contain a
 * RuleAlternatives object with the single entry which is a
 * RuleToken set to "please".  Skipping the rest of the structure,
 * the entire RuleParse object has the following structure.
 * <p>
 * RuleParse(
 * command
 * =                    // Match
 * command
 * RuleSequence(                          //  by a sequence of 3 entities
 * RuleParse(
 * action
 * =                 // First match
 * action
 * RuleAlternatives(                  // One of a set of alternatives
 * RuleTag(                         // matching the tagged
 * RuleToken("close"), "CL")))    //   token "close"
 * RuleParse(
 * object
 * =                 // Now match
 * object
 * RuleSequence(                      //   by a sequence of 2 entities
 * RuleSequence(                    // RuleCount becomes RuleSequence
 * RuleParse(
 * this_that_etc
 * =    // Match
 * this_that_etc
 * RuleAlternatives(            // One of a set of alternatives
 * RuleToken("that"))))       //   is the token "that"
 * RuleAlternatives(                // Match "window | door"
 * RuleToken("door"))))           //   as the token "door"
 * RuleSequence(                        // RuleCount becomes RuleSequence
 * RuleParse(
 * polite
 * =               // Now match
 * polite
 * RuleAlternatives(                //   by 1 of 2 alternatives
 * RuleToken("please"))))         // The token "please"
 * )
 * )
 * <p>
 * (Parse structures are hard to read and understand but can be easily
 * processed by recursive method calls.)
 */
public class RuleParse extends Rule {

    /**
     * The RuleName matched by the parse structure.
     *
     * @see javax.speech.recognition.RuleParse#getRuleName()
     */
    protected RuleName ruleName;

    /**
     * The Rule structure matching the RuleName.
     *
     * @see javax.speech.recognition.RuleParse#getRule()
     */
    protected Rule rule;

    /**
     * Empty constructor for RuleParse object with
     * rulename and rule set to null.
     */
    public RuleParse() {
        this.setRuleName(null);
        this.setRule(null);
    }

    /**
     * Construct a RuleParse object for a named rule and
     * a Rule object that represents the parse structure.
     * The structure of the rule object is described above.
     * The rulename should be a fully-qualified name.
     */
    public RuleParse(RuleName ruleName, Rule rule) {
        this.setRuleName(ruleName);
        this.setRule(rule);
    }

    /**
     * Return a deep copy of this rule.
     * See the
     * <A href="Rule.html#copy()">Rule.copy</A>
     * documentation for an explanation of deep copy.
     */
    @Override
    public Rule copy() {
        RuleName name = null;
        if (this.ruleName != null) {
            name = (RuleName) this.ruleName.copy();
        }

        return new RuleParse(name, this.rule.copy());
    }

    /**
     * Return the Rule matched by the RuleName.
     */
    public Rule getRule() {
        return this.rule;
    }

    /**
     * Return the matched RuleName.
     * Should be a fully-qualified rulename.
     */
    public RuleName getRuleName() {
        return this.ruleName;
    }

    /**
     * List the tags matched in this parse structure.
     * Tags are listed in the order of tokens (from start
     * to end) and from the lowest to the highest attachment.
     * (See the FinalRuleResult.getTags method for an example.)
     *
     * @see javax.speech.recognition.FinalRuleResult#getTags()
     */
    public String[] getTags() {
        List<String> tags = new ArrayList<>();
        this.getTags(this.rule, tags);
        return tags.toArray(new String[0]);
    }

    private void getTags(Rule rule, List<String> tags) {
        if (!(rule instanceof RuleToken)) {
            if (rule instanceof RuleParse) {
                this.getTags(((RuleParse) rule).getRule(), tags);
            } else if (rule instanceof RuleTag) {
                RuleTag tag = (RuleTag) rule;
                this.getTags(tag.getRule(), tags);
                tags.add(tag.tag);
            } else {
                int i;
                if (rule instanceof RuleSequence) {
                    RuleSequence sequence = (RuleSequence) rule;

                    for (i = 0; i < sequence.rules.length; ++i) {
                        this.getTags(sequence.rules[i], tags);
                    }

                } else if (!(rule instanceof RuleAlternatives)) {
                    if (!(rule instanceof RuleName)) {
                        throw new IllegalArgumentException(rule.getClass().getName() + " is not a legal object in a RuleParse");
                    }
                } else {
                    RuleAlternatives alternatives = (RuleAlternatives) rule;

                    for (i = 0; i < alternatives.rules.length; ++i) {
                        this.getTags(alternatives.rules[i], tags);
                    }

                }
            }
        }
    }

    /**
     * Set the Rule object matched to the RuleName.
     */
    public void setRule(Rule rule) {
        this.rule = rule;
    }

    /**
     * Set the matched RuleName.
     * Should be a fully-qualified rulename.
     */
    public void setRuleName(RuleName ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * Convert a RuleParse to a string with a similar style
     * to the Java Speech Grammar Format.
     * For example,
     * <p>
     * "(
     * command
     * = (
     * verb
     * = open) this)"
     * <p>
     * Notes:
     * <p>
     * The Java Speech Grammar Format does not define a representation
     * of parse structures.  A similar style is used for familiarity.
     * <p>
     * A sequence of zero entries can be produced by a parse of a
     * RuleCount object.  This is printed as
     * NULL
     * .
     * <p>
     * A RuleAlternatives is parsed to a RuleAlternatives
     * containing only one entry.  There is no explicit representation of this
     * form in JSGF so RuleAlternatives structure is lost when printed.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        if (this.ruleName == null) {
            sb.append("<???>");
        } else {
            sb.append(this.ruleName);
        }

        sb.append(" = ").append(this.rule.toString()).append(')');
        return sb.toString();
    }
}
