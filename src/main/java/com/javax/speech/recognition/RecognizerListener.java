package javax.speech.recognition;

import javax.speech.EngineListener;


/**
 * Defines an extension to the EngineListener interface
 * for specific events associated with a Recognizer.
 * A RecognizerListener object is attached to and
 * removed from a Recognizer by calls to the
 * addEngineListener and removeEngineListener
 * methods (which Recognizer inherits from the
 * Engine interface).
 * <p>
 * The RecognizerAdapter class provides a trivial
 * implementation of this interface.
 *
 * @see javax.speech.recognition.RecognizerAdapter
 * @see javax.speech.recognition.RecognizerEvent
 * @see javax.speech.recognition.Recognizer
 * @see javax.speech.Engine
 * @see javax.speech.Engine#addEngineListener(javax.speech.EngineListener)
 * @see javax.speech.Engine#removeEngineListener(javax.speech.EngineListener)
 */
public interface RecognizerListener extends EngineListener {

    /**
     * A CHANGES_COMMITTED event has been issued as a
     * Recognizer changes from the SUSPENDED
     * state to the LISTENING state and resumed recognition.
     * <p>
     * The GRAMMAR_CHANGES_COMMITTED event is issued
     * to the GrammarListeners of all changed grammars
     * immediately following the CHANGES_COMMITTED event.
     *
     * @see javax.speech.recognition.RecognizerEvent#CHANGES_COMMITTED
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_CHANGES_COMMITTED
     */
    void changesCommitted(RecognizerEvent e);

    /**
     * FOCUS_GAINED event has been issued as a
     * Recognizer changes from the FOCUS_OFF
     * state to the FOCUS_ON state.  A
     * FOCUS_GAINED event typically follows
     * a call to requestFocus on a Recognizer.
     * <p>
     * The GRAMMAR_ACTIVATED event is issued
     * to the GrammarListeners of all activated grammars
     * immediately following this RecognizerEvent.
     *
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_GAINED
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_ACTIVATED
     * @see javax.speech.recognition.Recognizer#requestFocus()
     */
    void focusGained(RecognizerEvent e);

    /**
     * FOCUS_LOST event has been issued as a
     * Recognizer changes from the FOCUS_ON
     * state to the FOCUS_OFF state.  A
     * FOCUS_LOST event may follow
     * a call to releaseFocus on a Recognizer
     * or follow a request for focus by another application.
     * <p>
     * The GRAMMAR_DEACTIVATED event is issued
     * to the GrammarListeners of all deactivated grammars
     * immediately following this RecognizerEvent.
     *
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_LOST
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_DEACTIVATED
     * @see javax.speech.recognition.Recognizer#releaseFocus()
     */
    void focusLost(RecognizerEvent e);

    /**
     * A RECOGNIZER_PROCESSING event has been issued
     * as a Recognizer changes from the LISTENING
     * state to the PROCESSING state.
     *
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_PROCESSING
     */
    void recognizerProcessing(RecognizerEvent e);

    /**
     * A RECOGNIZER_SUSPENDED event has been issued as a
     * Recognizer changes from either the LISTENING
     * state or the PROCESSING state to the
     * SUSPENDED state.
     * <p>
     * A Result finalization event (either a
     * RESULT_ACCEPTED or RESULT_REJECTED event)
     * is issued immediately following the RECOGNIZER_SUSPENDED event.
     *
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_SUSPENDED
     */
    void recognizerSuspended(RecognizerEvent e);
}
