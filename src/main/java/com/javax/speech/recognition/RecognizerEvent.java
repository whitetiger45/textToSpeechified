package javax.speech.recognition;

import javax.speech.EngineEvent;


/**
 * Event issued by Recognizer through RecognizerListener.
 * Inherits the following event types from EngineEvent:
 * ENGINE_ALLOCATED,
 * ENGINE_DEALLOCATED,
 * ENGINE_ALLOCATING_RESOURCES,
 * ENGINE_DEALLOCATING_RESOURCES,
 * ENGINE_PAUSED,
 * ENGINE_RESUMED.
 * <p>
 * The source object for any RecognizerEvent is the
 * Recognizer.
 */
public class RecognizerEvent extends EngineEvent {

    /**
     * RECOGNIZER_PROCESSING event is issued when a
     * Recognizer changes from the LISTENING
     * state to the PROCESSING state to indicate that
     * it is actively processing a recognition Result.
     * The transition is triggered when the recognizer detects
     * speech in the incoming audio stream that may match
     * and active grammar.  The transition occurs immediately
     * before the RESULT_CREATED is issued to
     * ResultListeners.
     *
     * @see javax.speech.recognition.RecognizerListener#recognizerProcessing(javax.speech.recognition.RecognizerEvent)
     * @see javax.speech.recognition.Recognizer#LISTENING
     * @see javax.speech.recognition.Recognizer#PROCESSING
     * @see javax.speech.recognition.ResultEvent#RESULT_CREATED
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.SpeechEvent#getId()
     */
    public static final int RECOGNIZER_PROCESSING = 1200;

    /**
     * RECOGNIZER_SUSPENDED event is issued when a
     * Recognizer changes from either the LISTENING
     * state or the PROCESSING state to the
     * SUSPENDED state.
     * <p>
     * A transition from the LISTENING state to the
     * SUSPENDED state is triggered by a call to either the
     * suspend method or the commitChanges method.
     * <p>
     * A transition from the PROCESSING state to the
     * SUSPENDED state is triggered by the finalization
     * of the result currently being recognized.
     * In this instance, the RECOGNIZER_SUSPENDED event is
     * followed immediately by either the RESULT_ACCEPTED
     * or RESULT_REJECTED event that finalizes the result.
     *
     * @see javax.speech.recognition.RecognizerListener#recognizerSuspended(javax.speech.recognition.RecognizerEvent)
     * @see javax.speech.recognition.Recognizer#LISTENING
     * @see javax.speech.recognition.Recognizer#PROCESSING
     * @see javax.speech.recognition.Recognizer#SUSPENDED
     * @see javax.speech.recognition.Recognizer#suspend()
     * @see javax.speech.recognition.Recognizer#commitChanges()
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.SpeechEvent#getId()
     * @see javax.speech.recognition.ResultEvent#RESULT_ACCEPTED
     * @see javax.speech.recognition.ResultEvent#RESULT_REJECTED
     */
    public static final int RECOGNIZER_SUSPENDED = 1202;

    /**
     * CHANGES_COMMITTED event is issued when a
     * Recognizer changes from the SUSPENDED
     * state to the LISTENING state.
     * This state transition takes place when changes to the definition
     * and enabled state of all a recognizer's grammars have been applied.
     * The new grammar definitions are used as incoming speech is
     * recognized in the LISTENING and PROCESSING
     * states of the Recognizer.
     * <p>
     * Immediately following the CHANGES_COMMITTED event,
     * GRAMMAR_CHANGES_COMMITTED events are issued to
     * the GrammarListeners of each changed Grammar.
     * <p>
     * If any errors are detected in any grammar's definition during
     * the commit, a GrammarException is provided with this
     * event.  The GrammarException is also included with
     * the GRAMMAR_CHANGES_COMMITTED event to Grammar
     * with the error.  The GrammarException has the same
     * function as the GrammarException thrown on the
     * commitChanges method.
     * <p>
     * The causes and timing of the CHANGES_COMMITTED event
     * are described with the
     * <A href="Recognizer.html#normalEvents">
     * state transition documentation</A>
     * for a Recognizer
     * with the
     * <A href="Grammar.html#commit">committing changes
     * documentation</A>
     * for a Grammar.
     *
     * @see javax.speech.recognition.RecognizerListener#changesCommitted(javax.speech.recognition.RecognizerEvent)
     * @see javax.speech.recognition.Recognizer#commitChanges()
     * @see javax.speech.recognition.Recognizer#LISTENING
     * @see javax.speech.recognition.Recognizer#SUSPENDED
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_CHANGES_COMMITTED
     * @see javax.speech.Engine#getEngineState()
     * @see javax.speech.SpeechEvent#getId()
     */
    public static final int CHANGES_COMMITTED = 1203;

    /**
     * FOCUS_GAINED event is issued when a
     * Recognizer changes from the FOCUS_OFF
     * state to the FOCUS_ON state.  This event
     * typically occurs as a result of a call the
     * requestFocus method of the Recognizer.
     * <p>
     * The event indicates that the FOCUS_ON bit
     * of the engine state is set.
     * <p>
     * Since recognizer focus is a key factor in the activation
     * policy for grammars, a FOCUS_GAINED event is followed
     * by a GRAMMAR_ACTIVATED event to the
     * GrammarListeners of each Grammar
     * that is activated.  Activation conditions and the role
     * of recognizer focus are detailed in the documentation for
     * the Grammar interface.
     *
     * @see javax.speech.recognition.RecognizerListener#focusGained(javax.speech.recognition.RecognizerEvent)
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_LOST
     * @see javax.speech.recognition.Recognizer#FOCUS_ON
     * @see javax.speech.recognition.Recognizer#requestFocus()
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_ACTIVATED
     * @see javax.speech.recognition.Grammar
     */
    public static final int FOCUS_GAINED = 1204;

    /**
     * FOCUS_LOST event is issued when a
     * Recognizer changes from the FOCUS_ON
     * state to the FOCUS_OFF state.  This event
     * may occur as a result of a call to the releaseFocus
     * method of the Recognizer or because another application
     * has requested recognizer focus.
     * <p>
     * The event indicates that the FOCUS_OFF bit
     * of the engine state is set.
     * <p>
     * Since recognizer focus is a key factor in the activation
     * policy for grammars, a FOCUS_LOST event is followed
     * by a GRAMMAR_DEACTIVATED event to the
     * GrammarListeners of each Grammar
     * that loses activation.  Activation conditions and the role
     * of recognizer focus are detailed in the documentation for
     * the Grammar interface.
     *
     * @see javax.speech.recognition.RecognizerListener#focusLost(javax.speech.recognition.RecognizerEvent)
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_GAINED
     * @see javax.speech.recognition.Recognizer#FOCUS_OFF
     * @see javax.speech.recognition.Recognizer#releaseFocus()
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_DEACTIVATED
     * @see javax.speech.recognition.Grammar
     */
    public static final int FOCUS_LOST = 1205;

    /**
     * Non-null if any error is detected in a grammar's definition while
     * producing a CHANGES_COMMITTED event.
     * null for other event types.
     * The exception serves the same functional role as the
     * GrammarException thrown on the commitChanges
     * method.
     *
     * @see javax.speech.recognition.RecognizerEvent#getGrammarException()
     * @see javax.speech.recognition.Recognizer#commitChanges()
     */
    protected GrammarException grammarException;

    /**
     * Construct a RecognizerEvent with a specified event source,
     * event identifier, old and new states, and optionally a
     * GrammarException for a CHANGES_COMMITTED event.
     *
     * @param source           the Recognizer that issued the event
     * @param id               the identifier for the event type
     * @param oldEngineState   engine state prior to this event
     * @param newEngineState   engine state following this event
     * @param grammarException non-null if an error is detected during CHANGES_COMMITTED
     */
    public RecognizerEvent(Recognizer source, int id, long oldEngineState, long newEngineState, GrammarException grammarException) {
        super(source, id, oldEngineState, newEngineState);
        this.grammarException = grammarException;
    }

    /**
     * Returns non-null for a CHANGES_COMMITTED
     * event if an error is found in the grammar definition.
     * The exception serves the same functional role as the
     * GrammarException thrown on the commitChanges
     * method.
     *
     * @see javax.speech.recognition.Recognizer#commitChanges()
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
        case 1200:
            return "RECOGNIZER_PROCESSING";
        case 1201:
        default:
            return super.paramString();
        case 1202:
            return "RECOGNIZER_SUSPENDED";
        case 1203:
            if (this.grammarException != null) {
                return "CHANGES_COMMITTED: " + this.grammarException.getMessage();
            }

            return "CHANGES_COMMITTED";
        case 1204:
            return "FOCUS_GAINED";
        case 1205:
            return "FOCUS_LOST";
        }
    }
}
