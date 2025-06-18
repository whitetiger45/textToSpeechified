package javax.speech;

import java.util.EventListener;


/**
 * The listener interface for receiving events associated
 * with the audio input or output of an Engine.
 * An AudioListener is attached to an Engine
 * by the addAudioListener method of the engine's
 * AudioManager.
 * <p>
 * RecognizerAudioListener extends this interface to support
 * RecognizerAudioEvents provided by a Recognizer.
 * <p>
 * Note: until the Java Sound API is finalized,
 * the AudioManager and other audio classes and
 * interfaces will remain as placeholders for future expansion.
 * Only the Recognizer audio events are functional in this release.
 *
 * @see javax.speech.AudioManager#addAudioListener(javax.speech.AudioListener)
 * @see javax.speech.recognition.RecognizerAudioEvent
 * @see javax.speech.recognition.RecognizerAudioListener
 */
public interface AudioListener extends EventListener {
}
