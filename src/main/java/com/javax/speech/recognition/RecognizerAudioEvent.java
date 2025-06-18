package javax.speech.recognition;

import javax.speech.AudioEvent;


/**
 * Event issued to indicate detection of speech in the incoming
 * audio stream or to periodically indicate the audio input level.
 * RecognizerAudioEvents are issued to each
 * RecognizerAudioListener attached to the
 * AudioManager for a Recognizer.
 * <p>
 * RecognizerAudioListener events are timed
 * with the input audio.  In most architectures this
 * indicates that the events occur in near to real time.
 * <p>
 * <A/>
 * SPEECH_STARTED and SPEECH_STOPPED
 * <p>
 * The SPEECH_STARTED and SPEECH_STOPPED
 * events are used to indicate possible detection of speech
 * in the incoming audio.  Applications may use this event to
 * display visual feedback to a user indicating that the recognizer
 * is listening - for example, by maintaining a icon indicating
 * microphone status.
 * <p>
 * It is sometimes difficult to quickly distinguish between
 * speech and other noises (e.g. coughs, microphone bumps),
 * so the SPEECH_STARTED event is not always
 * followed by a Result.
 * <p>
 * If a RESULT_CREATED is issued for the detected speech,
 * it will usually occur soon after the SPEECH_STARTED
 * event but may be delayed for a number of reasons.
 * <p>
 * The recognizer may be slower than real time and lag audio input.
 * <p>
 * The recognizer may defer issuing a RESULT_CREATED
 * until it is confident that it has detected speech that matches
 * one of the active grammars - in some cases the RESULT_CREATED
 * may be issued at the end of the spoken sentence.
 * <p>
 * The recognizer may be delayed because it is in the
 * SUSPENDED state causing it to buffer audio
 * and "catch up" once it returns to the LISTENING state.
 * <p>
 * Many other reasons.
 * <p>
 * Some recognizers will allow a user to speak more than one commands
 * without a break.  In these cases, a single SPEECH_STARTED
 * event may be followed by more than one RESULT_CREATED event
 * and result finalization before the SPEECH_STOPPED event
 * is issued.
 * <p>
 * In longer speech (e.g. dictating a paragraph), short pauses in
 * the user's speech may lead to a SPEECH_STOPPED event
 * followed by a SPEECH_STARTED event as the user resumes
 * speaking.  These events do not always indicate that the current
 * result will be finalized.
 * <p>
 * In short, applications should treat the SPEECH_STARTED
 * and SPEECH_STOPPED events as operating entirely
 * independently of the result system of a Recognizer.
 * <p>
 * <A/>
 * Audio Level Events
 * <p>
 * An AUDIO_LEVEL event indicates a change in the volume of the
 * audio input to a recognizer.  The level is defined on a scale from
 * 0 to 1.  0.0 represents silence.  0.25 represents quiet input.
 * 0.75 represents loud input. 1.0 indicates the maximum level.
 * <p>
 * Audio level events are suitable for display of a visual "VU meter"
 * (like the bar on stereo systems which goes up and down with the volume).
 * Different colors are often used to indicate the different levels:
 * for example, red for loud, green for normal, and blue for background.
 * <p>
 * Maintaining audio quality is important for reliable recognition.
 * A common problem is a user speaking too loudly or too quietly.
 * The color on a VU meter is one way to provide feedback to the user.
 * Note that a quiet level (below 0.25) does not necessarily indicate
 * that the user is speaking too quietly.  The input is also quiet
 * when the user is not speaking.
 */
public class RecognizerAudioEvent extends AudioEvent {

    /**
     * The recognizer has detected the possible start of speech
     * in the incoming audio.  Applications may use this event to
     * display visual feedback to a user indicating that the recognizer
     * is listening.
     * <p>
     * A
     * <A href="#startStop">detailed description of SPEECH_STARTED and
     * SPEECH_STOPPED events</A>
     * is provided above.
     *
     * @see javax.speech.recognition.RecognizerAudioListener#speechStarted(javax.speech.recognition.RecognizerAudioEvent)
     * @see javax.speech.SpeechEvent#getId()
     * @see javax.speech.recognition.RecognizerAudioEvent#SPEECH_STOPPED
     */
    public static final int SPEECH_STARTED = 1100;

    /**
     * The recognizer has detected the end of speech or noise
     * in the incoming audio that it previously indicated by a
     * SPEECH_STARTED event.
     * This event always follows a SPEECH_STARTED event.
     * <p>
     * A
     * <A href="#startStop">detailed description of SPEECH_STARTED and
     * SPEECH_STOPPED events</A>
     * is provided above.
     *
     * @see javax.speech.recognition.RecognizerAudioListener#speechStopped(javax.speech.recognition.RecognizerAudioEvent)
     * @see javax.speech.recognition.RecognizerAudioEvent#SPEECH_STARTED
     * @see javax.speech.SpeechEvent#getId()
     */
    public static final int SPEECH_STOPPED = 1101;

    /**
     * AUDIO_LEVEL event indicates a change in the
     * volume level of the incoming audio.
     * A
     * <A href="#level">detailed description of the AUDIO_LEVEL
     * event</A>
     * is provided above.
     *
     * @see javax.speech.recognition.RecognizerAudioListener#audioLevel(javax.speech.recognition.RecognizerAudioEvent)
     */
    public static final int AUDIO_LEVEL = 1102;

    /**
     * Audio level defined on a scale from 0 to 1.
     *
     * @see javax.speech.recognition.RecognizerAudioEvent#getAudioLevel()
     */
    protected float audioLevel;

    /**
     * Constructs an RecognizerAudioEvent with a specified
     * event identifier.
     * The audioLevel is set to 0.0.
     *
     * @param source the Recognizer that issued the event
     * @param id     the identifier for the event type
     */
    public RecognizerAudioEvent(Recognizer source, int id) {
        super(source, id);
        this.audioLevel = 0.0F;
    }

    /**
     * Constructs an RecognizerAudioEvent with a specified
     * event identifier and audio level.
     * The audioLevel should be 0.0
     * for SPEECH_STARTED and SPEECH_STOPPED
     * events.
     *
     * @param source     the Recognizer that issued the event
     * @param id         the identifier for the event type
     * @param audioLevel the audio level for this event
     */
    public RecognizerAudioEvent(Recognizer source, int id, float audioLevel) {
        super(source, id);
        this.audioLevel = audioLevel;
    }

    /**
     * Get the audio input level in the range 0 to 1.
     * A value below 0.25 indicates quiet input with 0.0 being silence.
     * A value above 0.75 indicates loud input with 1.0 indicating the maximum level.
     * <p>
     * The level is provided only for the AUDIO_LEVEL event type.
     * The level should be ignored for SPEECH_STARTED
     * and SPEECH_STOPPED events.
     *
     * @see javax.speech.recognition.RecognizerAudioEvent#AUDIO_LEVEL
     */
    public float getAudioLevel() {
        return this.audioLevel;
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
        case 1100:
            return "SPEECH_STARTED";
        case 1101:
            return "SPEECH_STOPPED";
        case 1102:
            return "AUDIO_LEVEL: " + this.audioLevel;
        default:
            return super.paramString();
        }
    }
}
