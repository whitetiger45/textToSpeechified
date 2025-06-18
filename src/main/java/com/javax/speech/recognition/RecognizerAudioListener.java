package javax.speech.recognition;

import javax.speech.AudioListener;


/**
 * Extends the set of audio event of an engine for a recognizer
 * by adding an audio level event.
 * <p>
 * The RecognizerAudioAdapter class provided a
 * trivial implementation of this listener.
 *
 * @see javax.speech.recognition.RecognizerAudioAdapter
 */
public interface RecognizerAudioListener extends AudioListener {

    /**
     * AUDIO_LEVEL event has occurred indicating a change in the
     * current volume of the audio input to a recognizer.
     *
     * @see javax.speech.recognition.RecognizerAudioEvent#AUDIO_LEVEL
     */
    void audioLevel(RecognizerAudioEvent e);

    /**
     * The recognizer has detected a possible start of speech.
     *
     * @see javax.speech.recognition.RecognizerAudioEvent#SPEECH_STARTED
     */
    void speechStarted(RecognizerAudioEvent e);

    /**
     * The recognizer has detected a possible end of speech.
     *
     * @see javax.speech.recognition.RecognizerAudioEvent#SPEECH_STOPPED
     */
    void speechStopped(RecognizerAudioEvent e);
}
