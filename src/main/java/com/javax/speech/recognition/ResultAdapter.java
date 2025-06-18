package javax.speech.recognition;

/**
 * The adapter which receives events for a Result object.
 * The methods in this class are empty;  this class is provided as a
 * convenience for easily creating listeners by extending this class
 * and overriding only the methods of interest.
 */
public class ResultAdapter implements ResultListener {

    /**
     * A AUDIO_RELEASED event has occurred.
     *
     * @see javax.speech.recognition.ResultEvent#AUDIO_RELEASED
     */
    @Override
    public void audioReleased(ResultEvent e) {
    }

    /**
     * A GRAMMAR_FINALIZED event has occurred.
     *
     * @see javax.speech.recognition.ResultEvent#GRAMMAR_FINALIZED
     */
    @Override
    public void grammarFinalized(ResultEvent e) {
    }

    /**
     * A RESULT_ACCEPTED event has occurred.
     *
     * @see javax.speech.recognition.ResultEvent#RESULT_ACCEPTED
     */
    @Override
    public void resultAccepted(ResultEvent e) {
    }

    /**
     * A RESULT_CREATED event has occurred.
     *
     * @see javax.speech.recognition.ResultEvent#RESULT_CREATED
     */
    @Override
    public void resultCreated(ResultEvent e) {
    }

    /**
     * A RESULT_REJECTED event has occurred.
     *
     * @see javax.speech.recognition.ResultEvent#RESULT_REJECTED
     */
    @Override
    public void resultRejected(ResultEvent e) {
    }

    /**
     * A RESULT_UPDATED event has occurred.
     *
     * @see javax.speech.recognition.ResultEvent#RESULT_UPDATED
     */
    @Override
    public void resultUpdated(ResultEvent e) {
    }

    /**
     * A TRAINING_INFO_RELEASED event has occurred.
     *
     * @see javax.speech.recognition.ResultEvent#TRAINING_INFO_RELEASED
     */
    @Override
    public void trainingInfoReleased(ResultEvent e) {
    }
}
