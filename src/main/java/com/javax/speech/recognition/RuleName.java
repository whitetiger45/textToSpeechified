package javax.speech.recognition;

import java.util.StringTokenizer;


/**
 * A RuleName is a reference to a named rule.
 * A RuleName is equivalent to the various forms
 * of rulename syntax in the
 * <p>
 * .
 * <p>
 * A fully-qualified rulename consists of a full grammar name
 * plus the simple rulename.  A full grammar name consists of
 * a package name and a simple grammar name.  The three legal
 * forms of a rulename allowed by the Java Speech Grammar Format
 * and in the RuleName object are:
 * <p>
 * Simple rulename:
 * simpleRulename
 * e.g.
 * digits
 * ,
 * date
 * Qualified rulename:
 * simpleGrammarName.simpleRulename
 * e.g.
 * numbers.digits
 * ,
 * places.cities
 * Fully-qualified rulename:
 * packageName.simpleGrammarName.simpleRulename
 * e.g.
 * <p>
 * com.sun.numbers.digits
 * <p>
 * com.acme.places.zipCodes
 * <p>
 * The full grammar name is the following portion of the
 * fully-qualified rulename: packageName.simpleGrammarName.
 * For example,
 * <p>
 * com.sun.numbers
 * <p>
 * com.acme.places
 * .
 * <p>
 * There are two special rules are defined in JSGF,
 * <p>
 * NULL
 * and
 * VOID
 * .
 * Both have static instances in this class for convenience.
 * These rulenames can be referenced in any grammar without an import statement.
 * <p>
 * There is a special case of using a RuleName to declare
 * and manage imports of a RuleGrammar.  This form is used
 * with the addImport and removeImport methods
 * of a RuleGrammar.  It requires a full grammar name plus
 * the string "*" for the simple rulename.  For example:
 * <p>
 * com.acme.places.*
 * <p>
 * The angle brackets placed around rulenames are syntactic constructs in JSGF
 * but are not a part of the rulename.  For clarity of code, the angle brackets
 * may be included in calls to this class, but they are automatically stripped.
 * <p>
 * The following referencing and name resolution conditions of JSGF apply.
 * <p>
 * Any rule in a local grammar may be referenced with a simple rulename,
 * qualified rulename or fully-qualified rulename.
 * A public rule of another grammar may be referenced by its simple rulename
 * or qualified rulename if the rule is imported and the name is not
 * ambiguous with another imported rule.
 * A public rule of another grammar may be referenced by its
 * fully-qualified rulename with or without a corresponding import
 * statement.
 */
public class RuleName extends Rule {

    /**
     * The complete specified rulename.  Maybe a fully-qualified rulename,
     * qualified rulename, or simple rulename depending upon how the
     * object is constructed.
     */
    protected String fullRuleName;

    /**
     * The rule's package name or null if not specified.
     */
    protected String packageName;

    /**
     * The rule's simple grammar name or null if not specified.
     */
    protected String simpleGrammarName;

    /**
     * The simple rulename.
     */
    protected String simpleRuleName;

    /**
     * Special
     * NULL
     * rule of JSGF
     * defining a rule that is always matched.
     */
    public static RuleName NULL = new RuleName("NULL");

    /**
     * Special
     * VOID
     * rule of JSGF
     * defining a rule that can never be matched.
     */
    public static RuleName VOID = new RuleName("VOID");

    /**
     * Empty constructor which sets the rule to
     * NULL
     * .
     */
    public RuleName() {
        this.setRuleName("NULL");
    }

    /**
     * Construct a RuleName from a string.
     * Leading and trailing angle brackets are stripped if found.
     * The rulename may be a simple rulename, qualified rulename
     * or full-qualified rulename.
     */
    public RuleName(String ruleName) {
        this.setRuleName(ruleName);
    }

    /**
     * Construct a RuleName from its package-, grammar-
     * and simple-name components.  Leading and trailing angle brackets
     * are stripped from ruleName if found.
     * The package name may be null.  The grammar name may be null
     * only if packageName is null.
     *
     * @param packageName       the package name of a fully-qualified rulename or null
     * @param simpleGrammarName the grammar name of a fully-qualified or qualified rule or null
     * @param simpleRuleName    the simple rulename
     * @throws java.lang.IllegalArgumentException null simpleGrammarName with non-null packageName
     */
    public RuleName(String packageName, String simpleGrammarName, String simpleRuleName) throws IllegalArgumentException {
        this.setRuleName(packageName, simpleGrammarName, simpleRuleName);
    }

    /**
     * Return a deep copy of this rule.
     */
    @Override
    public Rule copy() {
        return new RuleName(this.packageName, this.simpleGrammarName, this.simpleRuleName);
    }

    /**
     * Get the full grammar name.
     * If the packageName is null,
     * the return value is the simple grammar name.
     * May return null.
     */
    public String getFullGrammarName() {
        return this.packageName != null ? this.packageName + "." + this.simpleGrammarName : this.simpleGrammarName;
    }

    /**
     * Get the rule's package name.
     */
    public String getPackageName() {
        return this.packageName;
    }

    /**
     * Get the rulename including the package and grammar name
     * if they are non-null.  The return value may be a fully-qualified
     * rulename, qualified rulename, or simple rulename.
     */
    public String getRuleName() {
        return this.fullRuleName;
    }

    /**
     * Get the simple grammar name.
     * May be null.
     */
    public String getSimpleGrammarName() {
        return this.simpleGrammarName;
    }

    /**
     * Get the simple rulename.
     */
    public String getSimpleRuleName() {
        return this.simpleRuleName;
    }

    /**
     * Tests whether this RuleName is a legal JSGF rulename.
     * The isLegalRuleName(java.lang.String) method defines
     * legal rulename forms.
     *
     * @see javax.speech.recognition.RuleName#isLegalRuleName(java.lang.String)
     * @see javax.speech.recognition.RuleName#isRuleNamePart(char)
     */
    public boolean isLegalRuleName() {
        return isLegalRuleName(this.fullRuleName);
    }

    /**
     * Tests whether a string is a legal JSGF rulename format.
     * The
     * <A href="#rules">legal patterns</A>
     * for rulenames are defined above.
     * The method does not test whether the rulename exists or
     * is resolvable (see the resolve method of
     * RuleGrammar).
     * <p>
     * An import string (e.g. "com.acme.*") is considered legal
     * even though the "*" character is not a legal rulename character.
     * If present, starting and ending angle brackets are ignored.
     *
     * @see javax.speech.recognition.RuleName#isLegalRuleName()
     * @see javax.speech.recognition.RuleName#isRuleNamePart(char)
     */
    public static boolean isLegalRuleName(String name) {
        if (name == null) {
            return false;
        } else {
            name = stripRuleName(name);
            if (name.endsWith(".*")) {
                name = name.substring(0, name.length() - 2);
            }

            if (name.length() == 0) {
                return false;
            } else if (!name.startsWith(".") && !name.endsWith(".") && !name.contains("..")) {
                StringTokenizer tokenizer = new StringTokenizer(name, ".");

                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    int length = token.length();
                    if (length == 0) {
                        return false;
                    }

                    for (int i = 0; i < length; ++i) {
                        if (!isRuleNamePart(token.charAt(i))) {
                            return false;
                        }
                    }
                }

                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Tests whether a character is a legal part of a
     * Java Speech Grammar Format rulename.
     *
     * @see javax.speech.recognition.RuleName#isLegalRuleName()
     * @see javax.speech.recognition.RuleName#isLegalRuleName(java.lang.String)
     */
    public static boolean isRuleNamePart(char c) {
        if (Character.isJavaIdentifierPart(c)) {
            return true;
        } else {
            return c == '!' || c == '#' || c == '%' || c == '&' || c == '(' || c == ')' || c == '+' || c == ',' ||
                    c == '-' || c == '/' || c == ':' || c == ';' || c == '=' || c == '@' || c == '[' || c == '\\' ||
                    c == ']' || c == '^' || c == '|' || c == '~';
        }
    }

    /**
     * Set the rulename.
     * The rulename may be a simple-, qualified- or fully-qualified rulename.
     * Leading and trailing angle brackets are stripped if found.
     */
    public void setRuleName(String ruleName) {
        String name = stripRuleName(ruleName);
        this.fullRuleName = name;
        int period1 = name.lastIndexOf('.');
        if (period1 < 0) {
            this.packageName = null;
            this.simpleGrammarName = null;
            this.simpleRuleName = name;
        } else {
            int period2 = name.lastIndexOf('.', period1 - 1);
            if (period2 < 0) {
                this.packageName = null;
                this.simpleGrammarName = name.substring(0, period1);
                this.simpleRuleName = name.substring(period1 + 1);
            } else {
                this.packageName = name.substring(0, period2);
                this.simpleGrammarName = name.substring(period2 + 1, period1);
                this.simpleRuleName = name.substring(period1 + 1);
            }
        }
    }

    /**
     * Set the rule's name with package name, simple grammar name
     * and simple rulename components.
     * Leading and trailing angle brackets are stripped from ruleName.
     * The package name may be null.
     * The simple grammar name may be null only if packageName is null.
     *
     * @throws java.lang.IllegalArgumentException null simpleGrammarName with non-null packageName
     */
    public void setRuleName(String packageName, String simpleGrammarName, String simpleRuleName)
            throws IllegalArgumentException {
        if (simpleGrammarName == null && packageName != null) {
            throw new IllegalArgumentException("null simpleGrammarName with non-null packageName");
        } else {
            this.packageName = packageName;
            this.simpleGrammarName = simpleGrammarName;
            this.simpleRuleName = stripRuleName(simpleRuleName);
            StringBuilder sb = new StringBuilder();
            if (packageName != null) {
                sb.append(packageName).append('.');
            }

            if (simpleGrammarName != null) {
                sb.append(simpleGrammarName).append('.');
            }

            sb.append(simpleRuleName);
            this.fullRuleName = sb.toString();
        }
    }

    private static String stripRuleName(String s) {
        return s.startsWith("<") && s.endsWith(">") ? s.substring(1, s.length() - 1) : s;
    }

    /**
     * Return a String representing the RuleName as
     * partial Java Speech Grammar Format text.
     * The return value is
     * RuleName
     * .
     */
    public String toString() {
        return "<" + this.fullRuleName + ">";
    }
}
