package javax.speech.recognition;

import java.applet.AudioClip;


/**
 * FinalResult is an extension to the Result
 * interface that provides information about a result that has been
 * finalized - that is, recognition is complete.  A finalized result
 * is a Result that has received either a
 * RESULT_ACCEPTED or RESULT_REJECTED
 * ResultEvent that puts it in either the ACCEPTED
 * or REJECTED state (indicated by the getResultState
 * method of the Result interface).
 * <p>
 * The FinalResult interface provides information for
 * finalized results that match either a DictationGrammar
 * or a RuleGrammar.
 * <p>
 * Any result object provided by a recognizer implements both the
 * FinalRuleResult and FinalDictationResult
 * interfaces.  Because both these interfaces extend the FinalResult
 * interface, which in turn extends the Result interface,
 * all results implement FinalResult.
 * <p>
 * The methods of the FinalResult interface provide information
 * about a finalized result (ACCEPTED or
 * REJECTED state).  If any method of the FinalResult
 * interface is called on a result in the UNFINALIZED state, a
 * ResultStateError is thrown.
 * <p>
 * Three capabilities can be provided by a finalized result:
 * training/correction, access to audio data, and access to alternative guesses.
 * All three capabilities are optional because they are not all relevant
 * to all results or all recognition environments, and they are
 * not universally supported by speech recognizers.
 * Training and access to audio data are provided by the
 * FinalResult interface.  Access to alternative guesses
 * is provided by the FinalDictationResult and
 * FinalRuleResult interfaces (depending upon the type
 * of grammar matched).
 * <p>
 * Training / Correction
 * <p>
 * Because speech recognizers are not always correct, applications need
 * to consider the possibility that a recognition error has occurred.
 * When an application detects an error (e.g. a user updates a result),
 * the application should inform the recognizer so that it can learn
 * from the mistake and try to improve future performance.
 * The tokenCorrection is provided for an application to provide
 * feedback from user correction to the recognizer.
 * <p>
 * Sometimes, but certainly not always, the correct result is
 * selected by a user from amongst the N-best alternatives for a
 * result obtained through either the FinalRuleResult
 * or FinalDictationResult interfaces.  In other cases,
 * a user may type the correct result or the application may infer
 * a correction from following user input.
 * <p>
 * Recognizers must store considerable information to support
 * training from results.  Applications need to be involved in
 * the management of that information so that it is not stored
 * unnecessarily.  The isTrainingInfoAvailable method
 * tests whether training information is available for a finalized result.
 * When an application/user has finished correction/training for a result
 * it should call releaseTrainingInfo to free up
 * system resources.  Also, a recognizer may choose at any time to free
 * up training information.  In both cases, the application is
 * notified of the release with a TRAINING_INFO_RELEASED
 * event to ResultListeners.
 * <p>
 * Audio Data
 * <p>
 * Audio data for a finalized result is optionally provided by recognizers.
 * In dictation systems, audio feedback to users can remind them of
 * what they said and is useful in correcting and proof-reading documents.
 * Audio data can be stored for future use by an application or user and
 * in certain circumstances can be provided by one recognizer to another.
 * <p>
 * Since storing audio requires substantial system resources,
 * audio data requires special treatment.  If an application wants to
 * use audio data, it should set the setResultAudioProvided
 * property of the RecognizerProperties to true.
 * <p>
 * Not all recognizers provide access to audio data.  For those
 * recognizers, setResultAudioProvided has no effect,
 * the FinalResult.isAudioAvailable always returns
 * false, and the getAudio
 * methods always return null.
 * <p>
 * Recognizers that provide access to audio data cannot always provide
 * audio for every result.  Applications should test audio availability
 * for every FinalResult and should always test for
 * null on the getAudio methods.
 *
 * @see javax.speech.recognition.Result
 * @see javax.speech.recognition.Result#getResultState()
 * @see javax.speech.recognition.Result#ACCEPTED
 * @see javax.speech.recognition.Result#REJECTED
 * @see javax.speech.recognition.FinalDictationResult
 * @see javax.speech.recognition.FinalRuleResult
 * @see javax.speech.recognition.DictationGrammar
 * @see javax.speech.recognition.RuleGrammar
 * @see javax.speech.recognition.RecognizerProperties#setResultAudioProvided(boolean)
 */
public interface FinalResult extends Result {

    /**
     * The MISRECOGNITION flag is used in a call to
     * tokenCorrection to indicate that the change is
     * a correction of an error made by the recognizer.
     *
     * @see FinalResult#tokenCorrection(java.lang.String[], ResultToken, ResultToken, int)
     */
    int MISRECOGNITION = 400;

    /**
     * The USER_CHANGE flag is used in a call to
     * tokenCorrection to indicate that the user has
     * modified the text that was returned by the recognizer to
     * something different from what they actually said.
     *
     * @see FinalResult#tokenCorrection(java.lang.String[], ResultToken, ResultToken, int)
     */
    int USER_CHANGE = 401;

    /**
     * The DONT_KNOW flag is used in a call to tokenCorrection
     * to indicate that the application does not know whether a
     * change to a result is because of MISRECOGNITION
     * or USER_CHANGE.
     *
     * @see FinalResult#tokenCorrection(java.lang.String[], ResultToken, ResultToken, int)
     */
    int DONT_KNOW = 402;

    /**
     * Get the result audio for the complete utterance of a FinalResult.
     * Returns null if result audio is not available or if it has been released.
     *
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.FinalResult#isAudioAvailable()
     * @see javax.speech.recognition.FinalResult#getAudio(ResultToken, ResultToken)
     */
    AudioClip getAudio() throws ResultStateError;

    /**
     * Get the audio for a token or sequence of tokens.  Recognizers
     * make a best effort at determining the start and end of tokens,
     * however, it is not unusual for chunks of surrounding audio
     * to be included or for the start or end token to be chopped.
     * <p>
     * Returns null if result audio is not available or
     * if it cannot be obtained for the specified sequence of tokens.
     * <p>
     * If toToken is null or if
     * fromToken and toToken are the same,
     * the method returns audio for fromToken.
     * If both fromToken and
     * toToken are null, it returns the audio
     * for the entire result (same as getAudio()).
     * <p>
     * Not all recognizers can provide per-token audio, even if they
     * can provide audio for a complete utterance.
     *
     * @throws java.lang.IllegalArgumentException        one of the token parameters is not from this result
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.FinalResult#getAudio()
     */
    AudioClip getAudio(ResultToken fromToken, ResultToken toToken) throws IllegalArgumentException, ResultStateError;

    /**
     * Test whether result audio data is available for this result.
     * Result audio is only available if:
     * <p>
     * The ResultAudioProvided property of
     * RecognizerProperties was set to true
     * when the result was recognized.
     * The Recognizer was able to collect result audio for
     * the current type of FinalResult
     * (FinalRuleResult or FinalDictationResult).
     * The result audio has not yet been released.
     * <p>
     * The availability of audio for a result does not mean that all
     * getAudio calls will return an AudioClip.
     * For example, some recognizers might provide audio data only for
     * the entire result or only for individual tokens, or not for
     * sequences of more than one token.
     *
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.FinalResult#getAudio()
     * @see javax.speech.recognition.RecognizerProperties#setResultAudioProvided(boolean)
     */
    boolean isAudioAvailable() throws ResultStateError;

    /**
     * Returns true if the Recognizer
     * has training information available for this result.
     * Training is available if the following conditions are met:
     * <p>
     * The isTrainingProvided property of the
     * RecognizerProperties is set to true.
     * And, the training information for this result has not been released
     * by the application or by the recognizer.
     * (The TRAINING_INFO_RELEASED event has not been issued.)
     * <p>
     * Calls to tokenCorrection have no effect if the training
     * information is not available.
     *
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.RecognizerProperties#setTrainingProvided(boolean)
     * @see javax.speech.recognition.FinalResult#releaseTrainingInfo()
     */
    boolean isTrainingInfoAvailable() throws ResultStateError;

    /**
     * Release the result audio for the result.  After audio is
     * released, isAudioAvailable will return false.
     * This call is ignored if result audio is not available or
     * has already been released.
     * <p>
     * This method is asynchronous - audio data is not necessarily
     * released immediately.  A AUDIO_RELEASED event
     * is issued to the ResultListener when the audio is released
     * by a call to this method.  A AUDIO_RELEASED event is also
     * issued if the recognizer releases the audio for some other reason
     * (e.g. to reclaim memory).
     *
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.ResultEvent#AUDIO_RELEASED
     * @see javax.speech.recognition.ResultListener#audioReleased(javax.speech.recognition.ResultEvent)
     */
    void releaseAudio() throws ResultStateError;

    /**
     * Release training information for this FinalResult.
     * The release frees memory used for the training information --
     * this information can be substantial.
     * <p>
     * It is not an error to call the method when training information
     * is not available or has already been released.
     * <p>
     * This method is asynchronous - the training info is not
     * necessarily released when the call returns.
     * A TRAINING_INFO_RELEASED event is issued to
     * the ResultListener once the information is released.
     * The TRAINING_INFO_RELEASED event is also issued if the
     * recognizer releases the training information for any other reason
     * (e.g. to reclaim memory).
     *
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.ResultEvent#TRAINING_INFO_RELEASED
     */
    void releaseTrainingInfo() throws ResultStateError;

    /**
     * Inform the recognizer of a correction to one of more tokens in
     * a finalized result so that the recognizer can re-train itself.
     * Training the recognizer from its mistakes allows it to improve
     * its performance and accuracy in future recognition.
     * <p>
     * The fromToken and toToken parameters
     * indicate the inclusive sequence of best-guess or alternative
     * tokens that are being trained or corrected.  If toToken is
     * null or if fromToken and toToken
     * are the same, the training applies to a single recognized token.
     * <p>
     * The correctTokens token sequence may have the
     * same of a different length than the token sequence being corrected.
     * Setting correctTokens to null indicates
     * the deletion of tokens.
     * <p>
     * The correctionType parameter must be one of MISRECOGNITION,
     * USER_CHANGE, DONT_KNOW.
     * <p>
     * Note: tokenCorrection does not change the result object.
     * So, future calls to the getBestToken, getBestTokens
     * and getAlternativeTokens method return exactly the same values as
     * before the call to tokenCorrection.
     *
     * @param correctTokens  sequence of correct tokens to replace fromToken to toToken
     * @param fromToken      first token in the sequence being corrected
     * @param toToken        last token in the sequence being corrected
     * @param correctionType type of correction: MISRECOGNITION, USER_CHANGE,
     *                       DONT_KNOW
     * @throws java.lang.IllegalArgumentException        either token is not from this FinalResult
     * @throws javax.speech.recognition.ResultStateError if called before a result is finalized
     * @see javax.speech.recognition.FinalResult#MISRECOGNITION
     * @see javax.speech.recognition.FinalResult#USER_CHANGE
     * @see javax.speech.recognition.FinalResult#DONT_KNOW
     */
    void tokenCorrection(String[] correctTokens, ResultToken fromToken, ResultToken toToken, int correctionType)
            throws ResultStateError, IllegalArgumentException;
}
