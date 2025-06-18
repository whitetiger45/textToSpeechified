package javax.speech.recognition;

import java.beans.PropertyVetoException;
import javax.speech.EngineProperties;


/**
 * Enables control of the properties of a Recognizer.
 * The RecognizerProperties object is obtained by
 * calling the getEngineProperties method of the
 * Recognizer (inherited from the Engine interface).
 * <p>
 * Because RecognizerProperties extends the
 * EngineProperties interface, it inherits the
 * following behavior (described in detail in the EngineProperties
 * documentation):
 * <p>
 * Each property has a get and set method.  (JavaBeans property method patterns)
 * Engines may ignore changes to properties or apply maximum
 * and minimum limits.  If an engine does not apply a property change
 * request, the set method throws a PropertyVetoException.
 * Calls to set methods may be asynchronous (they may return
 * before the property change takes effect).  The Engine
 * will apply a change as soon as possible.
 * The get methods return the current setting - not a pending value.
 * A PropertyChangeListener may be attached to
 * receive property change events.
 * Where appropriate, property settings are persistent across sessions.
 * <p>
 * Per-Speaker Properties
 * <p>
 * For recognizers that maintain speaker data (recognizers that implement the
 * SpeakerManager interface) the RecognizerProperties
 * should be stored and loaded as part of the speaker data.
 * Thus, when the current speaker is changed through the
 * SpeakerManager interface, the properties of the
 * new speaker should be loaded.
 *
 * @see javax.speech.EngineProperties
 * @see javax.speech.recognition.SpeakerManager
 */
public interface RecognizerProperties extends EngineProperties {

    /**
     * Get the "CompleteTimeout" property.
     *
     * @see javax.speech.recognition.RecognizerProperties#setCompleteTimeout(float)
     */
    float getCompleteTimeout();

    /**
     * Get the recognizer's "ConfidenceLevel" property.
     *
     * @see javax.speech.recognition.RecognizerProperties#setConfidenceLevel(float)
     */
    float getConfidenceLevel();

    /**
     * Get the "IncompleteTimeout" property.
     *
     * @see javax.speech.recognition.RecognizerProperties#setIncompleteTimeout(float)
     */
    float getIncompleteTimeout();

    /**
     * Get the "NumResultAlternatives" property.
     *
     * @see javax.speech.recognition.RecognizerProperties#setNumResultAlternatives(int)
     */
    int getNumResultAlternatives();

    /**
     * Get the "Sensitivity" property.
     *
     * @see javax.speech.recognition.RecognizerProperties#setSensitivity(float)
     */
    float getSensitivity();

    /**
     * Get the "SpeedVsAccuracy" property.
     *
     * @see javax.speech.recognition.RecognizerProperties#setSpeedVsAccuracy(float)
     */
    float getSpeedVsAccuracy();

    /**
     * Get the "ResultAudioProvided" property.
     *
     * @see javax.speech.recognition.RecognizerProperties#setResultAudioProvided(boolean)
     */
    boolean isResultAudioProvided();

    /**
     * Get the TrainingProvided property.
     *
     * @see javax.speech.recognition.RecognizerProperties#setTrainingProvided(boolean)
     */
    boolean isTrainingProvided();

    /**
     * Set the CompleteTimeout property in seconds.
     * This timeout value determines the length of silence required
     * following user speech before the recognizer finalizes a result
     * (with an RESULT_ACCEPTED or RESULT_REJECTED
     * event).  The complete timeout is used when the speech is a complete
     * match an active RuleGrammar.  By contrast, the incomplete
     * timeout is used when the speech is an incomplete match to an active grammar.
     * <p>
     * A long complete timeout value delays the result completion
     * and therefore makes the computer's response slow.  A short
     * complete timeout may lead to an utterance being broken up
     * inappropriately.  Reasonable complete timeout values are
     * typically in the range of 0.3 seconds to 1.0 seconds.
     *
     * @throws java.beans.PropertyVetoException if the recognizer rejects or limits the new value
     * @see javax.speech.recognition.RecognizerProperties#getCompleteTimeout()
     */
    void setCompleteTimeout(float timeout) throws PropertyVetoException;

    /**
     * Set the recognizer's "ConfidenceLevel" property.
     * The confidence level value can very between 0.0 and 1.0.
     * A value of 0.5 is the default for the recognizer.
     * A value of 1.0 requires the recognizer to have maximum confidence
     * in its results or otherwise reject them.  A value of 0.0
     * requires only low confidence so fewer results are rejected.
     *
     * @throws java.beans.PropertyVetoException if the recognizer rejects or limits the new value
     * @see javax.speech.recognition.RecognizerProperties#getConfidenceLevel()
     */
    void setConfidenceLevel(float confidenceLevel) throws PropertyVetoException;

    /**
     * Set the IncompleteTimeout property in seconds.
     * The timeout value determines the required length of silence
     * following user speech after which a recognizer finalizes
     * a result.
     * <p>
     * The incomplete timeout applies when the speech prior to the
     * silence is an incomplete match of the active RuleGrammars.
     * In this case, once the timeout is triggered, the partial
     * result is rejected (with a RESULT_REJECTED event).
     * <p>
     * The incomplete timeout also applies when the speech prior to
     * the silence is a complete match of an active grammar, but
     * where it is possible to speak further and still match the
     * grammar.  For example, in a grammar for digit sequences for
     * telephone numbers it might be legal to speak either 7 or 10 digits.
     * If the user pauses briefly after speaking 7 digits then the
     * incomplete timeout applies because the user may then continue
     * with a further 3 digits.
     * <p>
     * By contrast, the complete timeout is used when the speech
     * is a complete match to an active RuleGrammar
     * and no further words can be spoken.
     * <p>
     * A long complete timeout value delays the result completion
     * and therefore makes the computer's response slow.  A short
     * incomplete timeout may lead to an utterance being broken up
     * inappropriately.
     * <p>
     * The incomplete timeout is usually longer than the complete timeout
     * to allow users to pause mid-utterance (for example, to breathe).
     *
     * @throws java.beans.PropertyVetoException if the recognizer rejects or limits the new value
     * @see javax.speech.recognition.RecognizerProperties#getIncompleteTimeout()
     */
    void setIncompleteTimeout(float timeout) throws PropertyVetoException;

    /**
     * Set the "NumResultAlternatives" property.
     * This property indicates the preferred maximum number of
     * N-best alternatives in FinalDictationResult and
     * FinalRuleResult objects.
     * A value of 0 or 1 indicates that the application does not want
     * alternatives; that is, it only wants the best guess.
     * <p>
     * Recognizers are not required to provide this number of
     * alternatives for every result and the number of alternatives
     * may vary from result to result.  Recognizers should only provide
     * alternative tokens which are considered reasonable guesses: that is,
     * the alternatives should be above the ConfidenceLevel
     * set through this interface (unless the Result is rejected).
     * <p>
     * Providing alternatives requires additional computing power.
     * Applications should only request the number of alternatives
     * that they are likely to use.
     *
     * @throws java.beans.PropertyVetoException if the recognizer rejects or limits the new value
     * @see javax.speech.recognition.RecognizerProperties#getNumResultAlternatives()
     * @see FinalDictationResult#getAlternativeTokens(ResultToken, ResultToken, int)
     * @see javax.speech.recognition.FinalRuleResult#getAlternativeTokens(int)
     */
    void setNumResultAlternatives(int num) throws PropertyVetoException;

    /**
     * Set the "ResultAudioProvided" property.
     * If set to true, the recognizer is requested
     * to provide audio with FinalResult objects.
     * If available, the audio is provided through the
     * getAudio methods of the FinalResult interface.
     * <p>
     * Some recognizers that can provide audio for a FinalResult
     * cannot provide audio for all results.  Applications need
     * test audio available individually for results.  (For example,
     * a recognizer might only provide audio for dictation results.)
     * <p>
     * A Recognizer that does not provide audio
     * for any results throws a PropertyVetoException
     * when an app attempts to set the value to true.
     *
     * @throws java.beans.PropertyVetoException if the recognizer rejects or limits the new value
     * @see javax.speech.recognition.RecognizerProperties#isResultAudioProvided()
     * @see javax.speech.recognition.FinalResult#getAudio()
     * @see javax.speech.recognition.FinalResult#getAudio(ResultToken, ResultToken)
     */
    void setResultAudioProvided(boolean audio) throws PropertyVetoException;

    /**
     * Set the "Sensitivity" property.
     * The sensitivity level can vary between 0.0 and 1.0.
     * A value of 0.5 is the default for the recognizer.
     * A value of 1.0 gives maximum sensitivity and makes the
     * recognizer sensitive to quiet input but more sensitive
     * to noise.  A value of 0.0 gives minimum sensitivity requiring
     * the user to speak loudly and making the recognizer less
     * sensitive to background noise.
     * <p>
     * Note: some recognizers set the gain automatically during use,
     * or through a setup "Wizard".  On these recognizers the sensitivity
     * adjustment should be used only in extreme cases where the automatic
     * settings are not adequate.
     *
     * @throws java.beans.PropertyVetoException if the recognizer rejects or limits the new value
     * @see javax.speech.recognition.RecognizerProperties#getSensitivity()
     */
    void setSensitivity(float sensitivity) throws PropertyVetoException;

    /**
     * Get the "SpeedVsAccuracy" property.
     * The default value is 0.5 and is the factory-defined
     * compromise of speed and accuracy.
     * A value of 0.0 minimizes response time.
     * A value of 1.0 maximizes recognition accuracy.
     * <p>
     * Why are speed and accuracy a trade-off?  A recognizer determines
     * what a user says by testing different possible sequence of words
     * (with legal sequences being defined by the active grammars).  If the
     * recognizer tests more sequences it is more likely to
     * find the correct sequence, but there is additional processing so
     * it is slower.  Increasing grammar complexity and decreasing the
     * computer power both make this trade-off more important.  Conversely,
     * a simpler grammar or more powerful computer make the trade-off
     * less important.
     *
     * @throws java.beans.PropertyVetoException if the recognizer rejects or limits the new value
     * @see javax.speech.recognition.RecognizerProperties#getSpeedVsAccuracy()
     */
    void setSpeedVsAccuracy(float speedVsAccuracy) throws PropertyVetoException;

    /**
     * Set the TrainingProvided property.
     * If true, request a recognizer to provide
     * training information for FinalResult objects
     * through the tokenCorrection method.
     * <p>
     * Not all recognizers support training.  Also, recognizers
     * that do support training are not required to support
     * training data for all results.  For example, a recognizer
     * might only produce training data with dictation results.
     * A Recognizer that does not support training
     * throws a PropertyVetoException when an app
     * attempts to set the value to true.
     *
     * @throws java.beans.PropertyVetoException if the recognizer rejects the new value
     * @see javax.speech.recognition.RecognizerProperties#isTrainingProvided()
     * @see javax.speech.recognition.FinalResult#tokenCorrection(java.lang.String[], ResultToken, ResultToken, int)
     */
    void setTrainingProvided(boolean trainingProvided) throws PropertyVetoException;
}
