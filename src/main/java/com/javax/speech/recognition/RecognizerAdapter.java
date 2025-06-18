package javax.speech.recognition;

import javax.speech.EngineAdapter;


/**
 * The adapter which receives events for a Recognizer.
 * The methods in this class are empty;  this class is provided as a
 * convenience for easily creating listeners by extending this class
 * and overriding only the methods of interest.
 */
public class RecognizerAdapter extends EngineAdapter implements RecognizerListener {

    /**
     * CHANGES_COMMITTED event has been issued as a
     * Recognizer changes from the SUSPENDED
     * state to the LISTENING state.
     * <p>
     * Following the CHANGES_COMMITTED event, a
     * GRAMMAR_CHANGES_COMMITTED event is issued
     * to the GrammarListeners of each grammar for
     * which a change in its definition or its enabled flag has
     * been committed.
     *
     * @see javax.speech.recognition.RecognizerEvent#CHANGES_COMMITTED
     * @see javax.speech.recognition.GrammarEvent#GRAMMAR_CHANGES_COMMITTED
     */
    @Override
    public void changesCommitted(RecognizerEvent e) {
    }

    /**
     * FOCUS_GAINED event has been issued as a
     * Recognizer changes from the FOCUS_OFF
     * state to the FOCUS_ON state.
     *
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_GAINED
     */
    @Override
    public void focusGained(RecognizerEvent e) {
    }

    /**
     * FOCUS_LOST event has been issued as a
     * Recognizer changes from the FOCUS_ON
     * state to the FOCUS_OFF state.
     *
     * @see javax.speech.recognition.RecognizerEvent#FOCUS_LOST
     */
    @Override
    public void focusLost(RecognizerEvent e) {
    }

    /**
     * A RECOGNIZER_PROCESSING event has been issued
     * as a Recognizer changes from the LISTENING
     * state to the PROCESSING state.
     *
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_PROCESSING
     */
    @Override
    public void recognizerProcessing(RecognizerEvent e) {
    }

    /**
     * RECOGNIZER_SUSPENDED event has been issued as a
     * Recognizer changes from either the
     * LISTENING state or the PROCESSING
     * state to the SUSPENDED state.
     *
     * @see javax.speech.recognition.RecognizerEvent#RECOGNIZER_SUSPENDED
     */
    @Override
    public void recognizerSuspended(RecognizerEvent e) {
    }
}
