package javax.speech;

/**
 * A trivial implementation of the AudioListener interface
 * that receives an engine's audio events.
 * The methods in this class are empty;  this class is provided as a
 * convenience for easily creating listeners by extending this class
 * and overriding only the methods of interest.
 * <p>
 * Extended by the RecognizerAudioAdapter that adds
 * specialized audio events for a Recognizer.
 * <p>
 * Note: until the Java Sound API is finalized,
 * the AudioManager and other audio classes and
 * interfaces will remain as placeholders for future expansion.
 * Only the Recognizer audio events are functional in this release.
 *
 * @see javax.speech.recognition.RecognizerAudioAdapter
 */
public class AudioAdapter implements AudioListener {
}
