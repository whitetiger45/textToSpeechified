package javax.speech.recognition;

/**
 * RuleGrammar interface describes a Grammar
 * that defines what users may say by a set rules.  The rules may be
 * defined as Rule objects that represent the rule in a
 * data structure or defined in the
 * <p>
 * .
 * <p>
 * All RuleGrammars are created and managed through the
 * Recognizer interface.  A RuleGrammar
 * may be created with the newRuleGrammar method.
 * A RuleGrammar is also created when JSGF text is
 * loaded with the loadJSGF methods either from
 * a Reader or from a URL.
 * <p>
 * A RuleGrammar contains the same information as a
 * grammar definition in the
 * <p>
 * <A href="http://java.sun.com/products/java-media/speech/">Java Speech Grammar Format</A>
 * .
 * That information includes:
 * <p>
 * The name of the grammar (inherited from Grammar interface),
 * A set of imports: each import references a single public rule of another
 * RuleGrammar or all public rules of another RuleGrammar.
 * A set of defined rules: each definition is identified by a unique
 * rulename (unique within the RuleGrammar), a boolean
 * flag indicating whether the rule is public, and a Rule
 * object that provides the logical expansion of the rule (how it has spoken).
 * <p>
 * The set of imports and the rule definitions can be changed by
 * applications.  For any change to take effect the application must
 * call the commitChanges method of the Recognizer.
 * <p>
 * A RuleGrammar can be printed in Java Speech Grammar Format
 * using the toString method.  Individual Rule
 * objects can be converted to JSGF with their toString methods.
 * <p>
 * In JSGF a rulename is surrounded by angle brackets (e.g.
 * <p>
 * rulename
 * ).  The angle brackets are ignored
 * in calls to the methods of a RuleGrammar - they
 * may be included but are stripped automatically and are not
 * included in rulenames returned by RuleGrammar methods.
 * <p>
 * The rules defined in a RuleGrammar are either public
 * or private.  A public rule may be:
 * <p>
 * Imported into other RuleGrammars,
 * Enabled for recognition,
 * Or both.
 * <p>
 * When a RuleGrammar is enabled and when the
 * activation conditions are appropriate (as described in the
 * documentation for Grammar) then the Grammar
 * is activated and any of the public rules of the grammar may be spoken.
 * The RuleGrammar interface extends the enable methods
 * of the Grammar interface to allow individual rules
 * to be enabled and disabled independently.
 * <p>
 * A public rule may reference private rules and imported rules.  Only
 * the top public rule needs to be enabled for it to be spoken.  The
 * referenced rules (local private or imported public rules) do not
 * need to be enabled to be spoken as part of the enabled rule.
 *
 * @see javax.speech.recognition.Recognizer
 * @see javax.speech.recognition.Rule
 * @see javax.speech.recognition.Grammar
 * @see "http://java.sun.com/products/java-media/speech/"
 */
public interface RuleGrammar extends Grammar {

    /**
     * Import all rules or a specified rule from another grammar.
     * The RuleName should be in one these forms:
     * <p>
     * package.grammar.ruleName
     * <p>
     * package.grammar.*
     * <p>
     * grammar.ruleName
     * <p>
     * grammar.*
     * <p>
     * which are equivalent to the following declarations in the
     * Java Speech Grammar Format.
     * <p>
     * // import all public rules of a grammar
     * import
     * package.grammar.*
     * <p>
     * import
     * grammar.*
     * <p>
     * // import a specific public rule name of a grammar
     * import
     * package.grammar.ruleName
     * import
     * grammar.ruleName
     * <p>
     * The forms without a package are only legal when the
     * full grammar name does not include a package name.
     * <p>
     * The addImport takes effect when
     * <p>
     * <A href="Grammar.html#commit">grammar changes are committed</A>
     * .
     * When changes are committed, all imports must be resolvable.
     * Specifically, every RuleGrammar listed
     * in an import must exist, and every fully qualified rulename
     * listed in an import must exist.  If any ommissions are found,
     * the commitChanges method throws an exception and
     * the changes do not take effect.
     * <p>
     * It is not an exception to import a rule or set of rules and not
     * reference them.
     *
     * @see javax.speech.recognition.RuleGrammar#listImports()
     * @see javax.speech.recognition.RuleGrammar#removeImport(javax.speech.recognition.RuleName)
     * @see javax.speech.recognition.Recognizer#commitChanges()
     */
    void addImport(RuleName importName);

    /**
     * Delete a rule from the grammar.  The deletion only takes effect when
     * <p>
     * <A href="Grammar.html#commit">grammar changes are next committed</A>
     * .
     * <p>
     * If the rule name contains both start/end angle brackets
     * (e.g. "
     * ruleName
     * "), then the brackets are ignored.
     *
     * @param ruleName name of the defined rule to be deleted
     * @throws java.lang.IllegalArgumentException if ruleName is unknown
     * @see javax.speech.recognition.Recognizer#commitChanges()
     */
    void deleteRule(String ruleName) throws IllegalArgumentException;

    /**
     * Returns a Rule object for a specified rulename.
     * Returns null if the rule is unknown.
     * The returned object is a copy of the recognizer's internal object
     * so it can be modified in any way without affecting the recognizer.
     * The setRule method should be called when a change
     * to the returned rule object needs to be applied to the recognizer.
     * <p>
     * The Rule.toString method can be used to convert the return object
     * to a printable String in Java Speech Grammar Format.
     * <p>
     * If there is a rule structure currently pending for a commitChanges
     * that structure is returned.  Otherwise, the current structure being
     * used by the recognizer on incoming speech is returned.
     * <p>
     * If the rule name contains both start/end angle brackets
     * (e.g. "
     * ruleName
     * "), then the brackets are ignored.
     * <p>
     * If fast, read-only access to the rule object is required (e.g. in
     * parsing), then the application may use getRuleInternal.
     *
     * @param ruleName the rulename to be returned
     * @return the definition of ruleName or null
     * @see javax.speech.recognition.RuleGrammar#setRule(java.lang.String, Rule, boolean)
     * @see javax.speech.recognition.RuleGrammar#getRuleInternal(java.lang.String)
     * @see javax.speech.recognition.Recognizer#commitChanges()
     */
    Rule getRule(String ruleName);

    /**
     * Returns a reference to a recognizer's internal rule object identified
     * by a rule name.  The application should never modify the returned
     * object.  This method is intended for use by parsers and other
     * software that needs to quickly analyse a recognizer's grammars
     * without modifying them (without the overhead of making copies,
     * as required by getRule).  If the returned object is ever modified
     * in any way, getRule and setRule should be used.
     * <p>
     * Returns null if the rule is unknown.
     * <p>
     * If there is a Rule structure currently pending for a
     * commitChanges that structure is returned.  Otherwise,
     * the current structure being used by the recognizer on incoming speech
     * is returned.
     * <p>
     * If the rule name contains both start/end angle brackets
     * (e.g. "
     * ruleName
     * "), then the brackets are ignored.
     *
     * @see javax.speech.recognition.RuleGrammar#setRule(java.lang.String, Rule, boolean)
     * @see javax.speech.recognition.RuleGrammar#getRule(java.lang.String)
     * @see javax.speech.recognition.Recognizer#commitChanges()
     */
    Rule getRuleInternal(String ruleName);

    /**
     * Returns true if any public rule of a RuleGrammar is enabled.
     *
     * @return true if RuleGrammar is enabled, otherwise false
     */
    @Override
    boolean isEnabled();

    /**
     * Test whether recognition of a specified rule of this
     * RuleGrammar is enabled.
     * If ruleName is null, the method is equivalent to
     * isEnabled().
     * <p>
     * If the rule name contains both start/end angle brackets
     * (e.g. "
     * ruleName
     * "), then the brackets are ignored.
     *
     * @param ruleName name of the rule being tested
     * @return true if ruleName is enabled, otherwise false
     * @throws java.lang.IllegalArgumentException if ruleName is unknown or if it is a non-public rule
     */
    boolean isEnabled(String ruleName) throws IllegalArgumentException;

    /**
     * Test whether a rule is public.  Public rules may be enabled to be
     * activated for recognition and/or may be imported into other
     * RuleGrammars.
     * <p>
     * If the rule name contains both start/end angle brackets
     * (e.g. "
     * ruleName
     * "), then the brackets are ignored.
     *
     * @param ruleName the rulename being tested
     * @return true if ruleName is public
     * @throws java.lang.IllegalArgumentException if ruleName is unknown
     */
    boolean isRulePublic(String ruleName) throws IllegalArgumentException;

    /**
     * Return a list of the current imports.
     * Returns zero length array if no RuleGrammars are imported.
     *
     * @see javax.speech.recognition.RuleGrammar#addImport(javax.speech.recognition.RuleName)
     * @see javax.speech.recognition.RuleGrammar#removeImport(javax.speech.recognition.RuleName)
     */
    RuleName[] listImports();

    /**
     * List the names of all rules defined in this RuleGrammar.
     * If any rules are pending deletion they are not listed (between a
     * call to deleteRule and a commitChanges
     * taking effect).
     * <p>
     * The returned names do not include the
     * symbols.
     */
    String[] listRuleNames();

    /**
     * Parse a sequence of tokens against a RuleGrammar.
     * In all other respects this parse method is equivalent to the
     * parse(String text, String ruleName) method
     * except that the string is pre-tokenized.
     *
     * @throws javax.speech.recognition.GrammarException if an error is found in the definition of the RuleGrammar
     * @see javax.speech.recognition.RuleGrammar#parse(java.lang.String, java.lang.String)
     * @see javax.speech.recognition.RuleGrammar#parse(FinalRuleResult, int, java.lang.String)
     */
    RuleParse parse(String text, String ruleName) throws GrammarException;

    /**
     * Parse the nth best result of a FinalRuleResult against
     * a RuleGrammar.  In other respects this parse method is
     * equivalent to the parse(String text, String ruleName)
     * method described above.
     * <p>
     * A rejected result (REJECTED state) is not guaranteed to parse.
     * Also, if the RuleGrammar has been modified since the result
     * was issued, parsing is not guaranteed to succeed.
     *
     * @throws javax.speech.recognition.GrammarException if an error is found in the definition of the RuleGrammar
     * @see javax.speech.recognition.RuleGrammar#parse(java.lang.String, java.lang.String)
     * @see javax.speech.recognition.RuleGrammar#parse(java.lang.String[], java.lang.String)
     */
    RuleParse parse(FinalRuleResult finalRuleResult, int nBest, String ruleName) throws GrammarException;

    RuleParse parse(String[] tokens, String ruleName) throws GrammarException;

    /**
     * Remove an import.  The name follows the format of addImport.
     * <p>
     * The change in imports only takes effect when
     * <p>
     * <A href="Grammar.html#commit">grammar changes are committed</A>
     * .
     *
     * @throws java.lang.IllegalArgumentException if importName is not currently imported
     * @see javax.speech.recognition.RuleGrammar#addImport(javax.speech.recognition.RuleName)
     * @see javax.speech.recognition.Recognizer#commitChanges()
     */
    void removeImport(RuleName importName) throws IllegalArgumentException;

    /**
     * Resolve a rulename reference within a RuleGrammar to a
     * fully-qualified rulename.  The input rulename may be a simple rulename,
     * qualified or fully-qualified.  If the rulename cannot be resolved,
     * the method returns null.
     * <p>
     * If the rulename being resolved is a local reference, the return
     * value is a fully-qualified rulename with its grammar part being
     * the name of this RuleGrammar.
     * <p>
     * Example: in a grammar that imports the rule
     * number
     * from
     * the grammar "com.sun.examples", the following
     * would return the fully-qualified rulename
     * com.sun.examples.number.
     * <p>
     * gram.resolve(new RuleName("number"));
     * <p>
     * If the input rulename is a fully-qualified rulename, then the method
     * checks whether that rulename exists (and could therefore be successfully
     * referenced in this RuleGrammar).  If the rulename exists, then
     * the return value is the same as the input value, otherwise the
     * method returns null.
     *
     * @param ruleName reference to rulename to be resolved
     * @return fully-qualified reference to a rulename
     * @throws javax.speech.recognition.GrammarException if an error is found in the definition of the RuleGrammar
     *                                                   or if rulename is an ambiguous reference
     */
    RuleName resolve(RuleName ruleName) throws GrammarException;

    /**
     * Convert a String in partial Java Speech Grammar Format (JSGF)
     * to a Rule object.  The string can be any legal
     * expansion from JSGF: i.e. anything that can appear
     * on the right hand side of a  JSGF rule definition.
     * For example,
     * <p>
     * Rule r = ruleGrammar.ruleForJSGF("[please]
     * action
     * all files");
     *
     * @throws javax.speech.recognition.GrammarException if the JSGF text contains any errors
     * @see "http://java.sun.com/products/java-media/speech/"
     */
    Rule ruleForJSGF(String JSGFText) throws GrammarException;

    /**
     * Set the enabled property for a set of public rules of a
     * RuleGrammar.
     * This method behaves the same as the
     * <p>
     * <A href="#setEnabled(boolean)">setEnabled(boolean)</A>
     * method
     * except that it only affects a defined single public rule.
     * This call does not affect the enabled state of other
     * public rules of the RuleGrammar.
     * <p>
     * If any one or more rules are enabled, the
     * RuleGrammar is considered to be enabled.
     * If all rules are disabled, then the RuleGrammar
     * is considered disabled.
     * <p>
     * A change in the enabled property of Rules or
     * a RuleGrammar only takes effect when
     * <p>
     * <A href="Grammar.html#commit">grammar changes are next committed</A>
     * .
     * <p>
     * If any rule name contains both start/end angle brackets
     * (e.g. "
     * ruleName
     * "), then the brackets are ignored.
     *
     * @param ruleNames the set of rulenames to be enabled or disabled
     * @param enabled   true to enabled rulenames, false to disable
     * @throws java.lang.IllegalArgumentException if one or more ruleNames is unknown or if any is a non-public rule
     * @see javax.speech.recognition.Recognizer#commitChanges()
     */
    void setEnabled(String ruleNames, boolean enabled) throws IllegalArgumentException;

    /**
     * Set the enabled property for a RuleGrammar.
     * When a grammar is enabled and when the activation conditions
     * are appropriate, the RuleGrammar is activated for
     * recognition and users may speak the commands/words/phrases defined by
     * the grammar and results will be generated.  The enabled state of a grammar
     * can be tested by the isEnabled method.  The activation
     * state of a grammar can be tested by the isActive methods.
     * <p>
     * Recognizers can have multiple Grammars enabled and active
     * at any time.  A change in enabled status of the Grammar
     * only takes effect when
     * <p>
     * <A href="Grammar.html#commit">grammar changes are next committed</A>
     * .
     * and the CHANGES_COMMITTED event is issued.
     * The grammar is only activated once all the activation
     * conditions are met (see documentation for Grammar).
     * <p>
     * All enabled rules of a RuleGrammar share the same
     * ActivationMode.  Thus, when a RuleGrammar
     * is activated or deactivated for recognition, all the enabled
     * rules of the RuleGrammar are  activated or
     * deactivated together.
     * <p>
     * This method is inherited from the Grammar interface.  For
     * a RuleGrammar, setEnabled(true) enables
     * all public rules of the grammar.  setEnabled(false)
     * disables all public rules of the RuleGrammar.
     * A RuleGrammar also provides setEnabled
     * methods for enabling and disabling individual public rules or sets
     * of public rules.
     * <p>
     * It is not an error to enable an enabled grammar
     * or disable an disable grammar.
     *
     * @see javax.speech.recognition.RuleGrammar#setEnabled(java.lang.String, boolean)
     * @see javax.speech.recognition.RuleGrammar#setEnabled(java.lang.String[], boolean)
     * @see javax.speech.recognition.RuleGrammar#isEnabled()
     * @see javax.speech.recognition.Grammar#setActivationMode(int)
     */
    @Override
    void setEnabled(boolean enabled);

    void setEnabled(String[] ruleNames, boolean enabled) throws IllegalArgumentException;

    /**
     * Set a rule in the grammar either by creating a new rule or
     * updating an existing rule.  The rule being set is identified by its
     * "ruleName" and defined by the Rule object
     * and its isPublic flag.  The setRule
     * method is equivalent to a rule definition
     * in the Java Speech Grammar Format (JSGF).
     * <p>
     * The change in the RuleGrammar takes effect when
     * <p>
     * <A href="Grammar.html#commit">grammar changes are committed</A>
     * .
     * <p>
     * The rule object represents the expansion of a
     * JSGF definition (the right hand side).  It may be a
     * RuleToken,
     * RuleName,
     * RuleAlternatives,
     * RuleSequence,
     * RuleCount or
     * RuleTag.
     * Each of these 6 object types is an extension of the Rule object.
     * (The rule object cannot be an instance of RuleParse which
     * is also an extension of Rule.)
     * <p>
     * A rule is most easily created from Java Speech Grammar Format text
     * using the ruleForJSGF method.  e.g.
     * <p>
     * gram.setRule(ruleName, gram.ruleForJSGF("open the
     * object
     * "), true);
     * <p>
     * The isPublic flag defines whether this rule
     * may be enabled and active for recognition and/or imported into other
     * rule grammars.  It is equivalent to the public
     * declaration in JSGF.
     * <p>
     * If the Rule object contains a fully-qualified reference
     * to a rule of another RuleGrammar, an import is
     * automatically generated for that rule if it is not already imported.
     * A subsequent call to listImports will return that
     * import statement.
     * <p>
     * If the rule name contains both start/end angle brackets
     * (e.g. "
     * ruleName
     * "), then they are
     * automatically stripped.
     * <p>
     * The Rule object passed to the RuleGrammar
     * is copied before being applied to recognizer.  Thus, an application
     * can modify and re-use a rule object without
     * unexpected side-effects.  Also, a different object will be returned by the
     * getRule and getRuleInternal methods
     * (although it will contain the same information).
     *
     * @param ruleName unique name of rule being defined (unique for this RuleGrammar)
     * @param rule     logical expansion for the rulename
     * @param isPublic true if this rule can be imported into other RuleGrammars or enabled
     * @throws java.lang.IllegalArgumentException if rule is not a legal instance of Rule
     * @throws java.lang.NullPointerException     if ruleName or rule are null
     * @see javax.speech.recognition.RuleGrammar#ruleForJSGF(java.lang.String)
     * @see javax.speech.recognition.RuleGrammar#getRule(java.lang.String)
     * @see javax.speech.recognition.RuleGrammar#getRuleInternal(java.lang.String)
     * @see javax.speech.recognition.Recognizer#commitChanges()
     * @see javax.speech.recognition.RuleAlternatives
     * @see javax.speech.recognition.RuleCount
     * @see javax.speech.recognition.RuleName
     * @see javax.speech.recognition.RuleSequence
     * @see javax.speech.recognition.RuleTag
     * @see javax.speech.recognition.RuleToken
     */
    void setRule(String ruleName, Rule rule, boolean isPublic) throws NullPointerException, IllegalArgumentException;

    /**
     * Return a string containing a specification for this RuleGrammar
     * in Java Speech Grammar Format.  The string includes the grammar name
     * declaration, import statements and all the rule definitions.
     * When sending JSGF to a stream (e.g. a file) the application should prepend
     * the header line with the appropriate character encoding information:
     * <p>
     * #JSGF V1.0 encoding-format;
     *
     * @see "http://java.sun.com/products/java-media/speech/"
     */
    String toString();
}
