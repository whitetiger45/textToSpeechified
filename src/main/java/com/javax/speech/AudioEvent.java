package javax.speech;

/**
 * Describes events associated with audio input/output for an Engine.
 * The event source is an Engine object.
 * <p>
 * Extended by the RecognizerAudioEvent class
 * that provides specialized events for a Recognizer.
 * <p>
 * Note: until the Java Sound API is finalized,
 * the AudioManager and other audio classes and
 * interfaces will remain as placeholders for future expansion.
 * Only the Recognizer audio events are functional in this release.
 */
public class AudioEvent extends SpeechEvent {

    /**
     * Constructs an AudioEvent with a specified id.
     *
     * @param source Engine that produced the event
     * @param id     type of audio event
     */
    public AudioEvent(Engine source, int id) {
        super(source, id);
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
        default:
            return super.paramString();
        }
    }
}
