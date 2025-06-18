package javax.speech.recognition;

import javax.speech.SpeechEvent;


/**
 * A GrammarEvent is issued to each
 * GrammarListener attached to a Grammar
 * when major events associated with that Grammar occur.
 * <p>
 * The source for a GrammarEvent is always a
 * Grammar object.
 */
public class GrammarEvent extends SpeechEvent {

    /**
     * A GRAMMAR_CHANGES_COMMITTED event is issued when a
     * Recognizer completes
     * <A href="Grammar.html#commit">
     * committing changes</A>
     * to a Grammar.  The event
     * is issued immediately following the CHANGES_COMMITTED
     * event that is issued to RecognizerListeners.  That
     * event indicates that changes have been applied to all grammars
     * of a Recognizer.  The GRAMMAR_CHANGES_COMMITTED
     * event is specific to each individual grammar.
     * <p>
     * The event is issued when the definition of the grammar is
     * changed, when its enabled property is changed, or both.
     * The enabledChanged and definitionChanged
     * flags are set accordingly.
     * <p>
     * A GRAMMAR_CHANGES_COMMITTED event can trigger without
     * an explicit call to commitChanges - there is usually an
     * implicit commitChanges at the completion of
     * result finalization event processing.  If any syntactic or
     * logical errors are detected for a Grammar during the
     * commit, the generated GrammarException is included with
     * this event.  If no problem is found the value is null.
     *
     * @see javax.speech.recognition.GrammarListener#grammarChangesCommitted(javax.speech.recognition.GrammarEvent)
     * @see javax.speech.recognition.RecognizerEvent#CHANGES_COMMITTED
     * @see javax.speech.recognition.Recognizer#commitChanges()
     * @see javax.speech.recognition.GrammarEvent#getDefinitionChanged()
     * @see javax.speech.recognition.GrammarEvent#getEnabledChanged()
     * @see javax.speech.recognition.GrammarEvent#getGrammarException()
     */
    public static final int GRAMMAR_CHANGES_COMMITTED = 200;

    /**
     * A GRAMMAR_ACTIVATED event is issued when a
     * grammar changes state from deactivated to activate.  The
     * isActive method of the Grammar
     * will now return true.
     * <p>
     * Grammar activation changes follow one of two RecognizerEvents:
     * (1) a CHANGES_COMMITTED event in which a grammar's
     * enabled flag is set true or (2) a
     * FOCUS_GAINED event.  The full details of the
     * <p>
     * <A href="Grammar.html#activation">activation conditions</A>
     * under which a
     * Grammar is activated are described in the documentation
     * for the Grammar interface.
     *
     * @see javax.speech.recognition.GrammarListener#grammarActivated(javax.speech.recognition.GrammarEvent)
     * @see javax.speech.recognition.RecognizerEvent#CHANGES_COMMITTED
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_GAINED
     */
    public static final int GRAMMAR_ACTIVATED = 201;

    /**
     * A GRAMMAR_DEACTIVATED event is issued when a
     * grammar changes state from activated to deactivated.
     * The isActive method of the Grammar
     * will now return false.
     * <p>
     * Grammar deactivation changes follow one of two RecognizerEvents:
     * (1) a CHANGES_COMMITTED event in which a grammar's
     * enabled flag is set false or (2) a
     * FOCUS_LOST event.  The full details of the
     * <p>
     * <A href="Grammar.html#activation">activation conditions</A>
     * under which a
     * Grammar is deactivated are described in the documentation
     * for the Grammar interface.
     *
     * @see javax.speech.recognition.GrammarListener#grammarDeactivated(javax.speech.recognition.GrammarEvent)
     * @see javax.speech.recognition.RecognizerEvent#CHANGES_COMMITTED
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_LOST
     */
    public static final int GRAMMAR_DEACTIVATED = 202;

    /**
     * True if the grammar's enabled property has changed with a
     * GRAMMAR_CHANGES_COMMITTED event.  False for other
     * event types.
     *
     * @see javax.speech.recognition.GrammarEvent#getEnabledChanged()
     */
    protected boolean enabledChanged;

    /**
     * True if the grammar's definition has changed with a
     * GRAMMAR_CHANGES_COMMITTED event.  False for other
     * event types.
     *
     * @see javax.speech.recognition.GrammarEvent#getDefinitionChanged()
     */
    protected boolean definitionChanged;

    /**
     * Non-null if any error is detected in a grammar's definition while
     * producing a GRAMMAR_CHANGES_COMMITTED event.
     * null for other event types.
     *
     * @see javax.speech.recognition.GrammarEvent#getGrammarException()
     */
    protected GrammarException grammarException;

    /**
     * Constructs a GrammarEvent event with a specified
     * event identifier.  The enabledChanged and
     * definitionChanged fields are set to false.
     * The grammarException field is set to null.
     *
     * @param source the object that issued the event
     * @param id     the identifier for the event type
     */
    public GrammarEvent(Grammar source, int id) {
        super(source, id);
        this.enabledChanged = false;
        this.definitionChanged = false;
        this.grammarException = null;
    }

    /**
     * Constructs a GrammarEvent event with a specified
     * event identifier plus state change and exception values.
     * For a GRAMMAR_CHANGES_COMMITTED event, the
     * enabledChanged and definitionChanged
     * parameters should indicate what properties of the Grammar
     * has changed, otherwise they should be false.
     * For a GRAMMAR_CHANGES_COMMITTED event, the
     * grammarException parameter should be non-null
     * only if an error is encountered in the grammar definition.
     *
     * @param source            the object that issued the event
     * @param id                the identifier for the event type
     * @param enabledChanged    true if the grammar's enabled property changed
     * @param definitionChanged true if the grammar's definition has changed
     * @param grammarException  non-null if an error is detected in a grammar's definition
     */
    public GrammarEvent(Grammar source, int id, boolean enabledChanged, boolean definitionChanged, GrammarException grammarException) {
        super(source, id);
        this.enabledChanged = enabledChanged;
        this.definitionChanged = definitionChanged;
        this.grammarException = grammarException;
    }

    /**
     * Returns true for a GRAMMAR_CHANGES_COMMITTED
     * event if the definition of the source Grammar
     * has changed.
     */
    public boolean getDefinitionChanged() {
        return this.definitionChanged;
    }

    /**
     * Returns true for a GRAMMAR_CHANGES_COMMITTED
     * event if the enabled property of the Grammar changed.
     */
    public boolean getEnabledChanged() {
        return this.enabledChanged;
    }

    /**
     * Returns non-null for a GRAMMAR_CHANGES_COMMITTED
     * event if an error is found in the grammar definition.
     */
    public GrammarException getGrammarException() {
        return this.grammarException;
    }

    /**
     * Returns a parameter string identifying this  event.
     * This method is useful for event-logging and for debugging.
     *
     * @return a string identifying the event
     */
    @Override
    public String paramString() {
        switch (super.id) {
        case 200:
            StringBuilder sb = new StringBuilder();
            sb.append("GRAMMAR_CHANGES_COMMITTED");
            if (this.enabledChanged) {
                sb.append(": enabledChanged");
            }

            if (this.definitionChanged) {
                sb.append(": definitionChanged");
            }

            if (this.grammarException != null) {
                sb.append(": ").append(this.grammarException.getMessage());
            }

            return sb.toString();
        case 201:
            return "GRAMMAR_ACTIVATED";
        case 202:
            return "GRAMMAR_DEACTIVATED";
        default:
            return super.paramString();
        }
    }
}
