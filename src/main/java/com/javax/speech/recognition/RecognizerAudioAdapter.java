package javax.speech.recognition;

import javax.speech.AudioAdapter;


/**
 * Adaptor for an audio events of a recognizer.
 * The methods in this class are empty;  this class is provided as a
 * convenience for easily creating listeners by extending this class
 * and overriding only the methods of interest.
 */
public class RecognizerAudioAdapter extends AudioAdapter implements RecognizerAudioListener {

    /**
     * AUDIO_LEVEL event has occurred indicating a change in the
     * current volume of the audio input to a recognizer.
     *
     * @see javax.speech.recognition.RecognizerAudioEvent#AUDIO_LEVEL
     */
    @Override
    public void audioLevel(RecognizerAudioEvent e) {
    }

    /**
     * The recognizer has detected a possible start of speech.
     *
     * @see javax.speech.recognition.RecognizerAudioEvent#SPEECH_STARTED
     */
    @Override
    public void speechStarted(RecognizerAudioEvent e) {
    }

    /**
     * The recognizer has detected a possible end of speech.
     *
     * @see javax.speech.recognition.RecognizerAudioEvent#SPEECH_STOPPED
     */
    @Override
    public void speechStopped(RecognizerAudioEvent e) {
    }
}
