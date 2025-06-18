package javax.speech;

/**
 * The AudioManager is provided by a speech Engine
 * - a Recognizer or Synthesizer - to
 * allow an application to control audio input/output
 * and to monitor audio-related events.
 * The AudioManager for an engine is obtained by
 * calling its getAudioManager method.
 * <p>
 * Note: until the Java Sound API is finalized,
 * the AudioManager and other audio classes and
 * interfaces will remain as placeholders for future expansion.
 * Only the Recognizer audio events are functional in this release.
 *
 * @see javax.speech.Engine#getAudioManager()
 */
public interface AudioManager {

    /**
     * Request notifications of audio events to an AudioListener.
     * An application can attach multiple audio listeners to an
     * AudioManager.
     * If the engine is a Recognizer, a RecognizerAudioListener
     * may be attached since the RecognizerAudioListener
     * interface extends the AudioListener interface.
     *
     * @see javax.speech.AudioListener
     * @see javax.speech.recognition.RecognizerAudioListener
     */
    void addAudioListener(AudioListener listener);

    /**
     * Detach an audio listener from this AudioManager.
     */
    void removeAudioListener(AudioListener listener);
}
