package javax.speech.synthesis;

import java.beans.PropertyVetoException;
import javax.speech.EngineProperties;


/**
 * Provides control of the run-time properties of a Synthesizer.
 * The SynthesizerProperties object is obtained by
 * calling the getEngineProperties method of the
 * Synthesizer (inherited from the Engine interface).
 * <p>
 * Because SynthesizerProperties extends the
 * EngineProperties interface to provide synthesizer-specific
 * properties.  It also inherits the following properties and
 * conventions from the EngineProperties interface.
 * <p>
 * Each property has a get and set method.  (JavaBeans property method patterns)
 * Engines may ignore calls to change properties, for example
 * by applying maximum and minimum settings.
 * Calls to set methods may be asynchronous (they may return
 * before the property change takes effect).  The Engine
 * will apply a change as soon as possible.  A PropertyChangeEvent
 * is issued when the change takes effect.  For example, a change
 * in the speaking rate might take place immediately, or at the
 * end of the current word, sentence or paragraph.
 * The get methods return the current setting - not a pending value.
 * A PropertyChangeListener may be attached to
 * receive property change events.
 * Where appropriate, property settings are persistent across sessions.
 * <p>
 * The properties of a synthesizer are:
 * <p>
 * Speaking voice,
 * Baseline pitch,
 * Pitch range,
 * Speaking rate,
 * Volume.
 * <p>
 * Setting these properties should be considered as a hint
 * to the synthesizer.  A synthesizer may choose to ignore out-of-range
 * values.  A synthesizer may have some properties that are unchangeable
 * (e.g. a single voice synthesizer).
 * Reasonable values for baseline pitch, pitch range and speaking rate
 * may vary between synthesizers, between languages and or between voices.
 * <p>
 * A change in voice may lead to change in other properties.
 * For example, female and young voices typically have higher
 * pitches than male voices.  When a change in voice leads to
 * changes in other properties, a separate PropertyChangeEvent
 * is issued for each property changed.
 * <p>
 * Whenever possible, property changes should be persistent for a voice.
 * For instance, after changing from voice A to voice B and back, the previous
 * property settings for voice A should return.
 * <p>
 * Changes in pitch, speaking rate and so on in the Java Speech Markup Language
 * text provided to the synthesizer affect the get values but
 * do not lead to a property change event.  Applications needing an
 * event at the time these changes should include a MARK
 * property with the appropriate JSML element.
 *
 * @see javax.speech.synthesis.Synthesizer
 * @see javax.speech.Engine#getEngineProperties()
 */
public interface SynthesizerProperties extends EngineProperties {

    /**
     * Get the baseline pitch for synthesis.
     *
     * @see javax.speech.synthesis.SynthesizerProperties#setPitch(float)
     */
    float getPitch();

    /**
     * Get the pitch range for synthesis.
     *
     * @see javax.speech.synthesis.SynthesizerProperties#setPitchRange(float)
     */
    float getPitchRange();

    /**
     * Get the current target speaking rate.
     *
     * @see javax.speech.synthesis.SynthesizerProperties#setSpeakingRate(float)
     */
    float getSpeakingRate();

    /**
     * Get the current synthesizer voice.  Modifications to the returned
     * voice do not affect the Synthesizer voice - a call
     * to setVoice is required for a change to take effect.
     *
     * @see javax.speech.synthesis.SynthesizerProperties#setVoice(javax.speech.synthesis.Voice)
     */
    Voice getVoice();

    /**
     * Get the current volume.
     *
     * @see javax.speech.synthesis.SynthesizerProperties#setVolume(float)
     */
    float getVolume();

    /**
     * Set the baseline pitch for the current synthesis voice.  Out-of-range
     * values may be ignored or restricted to engine-defined limits.
     * Different voices have different natural sounding ranges of pitch.
     * Typical male voices are between 80 and 180 Hertz.  Female pitches
     * typically vary from 150 to 300 Hertz.
     *
     * @throws java.beans.PropertyVetoException if the synthesizer rejects or limits the new value
     * @see javax.speech.synthesis.SynthesizerProperties#getPitch()
     */
    void setPitch(float hertz) throws PropertyVetoException;

    /**
     * Set the pitch range for the current synthesis voice.  A narrow pitch range provides
     * monotonous output while wide range provide a more lively voice.
     * This setting is a hint to the synthesis engine.
     * Engines may choose to ignore unreasonable requests.
     * Some synthesizers may not support pitch variability.
     * <p>
     * The pitch range is typically between 20% and 80% of the baseline pitch.
     *
     * @throws java.beans.PropertyVetoException if the synthesizer rejects or limits the new value
     * @see javax.speech.synthesis.SynthesizerProperties#getPitchRange()
     */
    void setPitchRange(float hertz) throws PropertyVetoException;

    /**
     * Set the target speaking rate for the synthesis voice
     * in words per minute.
     * <p>
     * Reasonable speaking rates depend upon the synthesizer and
     * the current voice (some voices sound better at higher or lower
     * speed than others).
     * <p>
     * Speaking rate is also dependent upon the language because
     * of different conventions for what is a "word".
     * A reasonable speaking rate for English is 200 words per minute.
     *
     * @throws java.beans.PropertyVetoException if the synthesizer rejects or limits the new value
     * @see javax.speech.synthesis.SynthesizerProperties#getSpeakingRate()
     */
    void setSpeakingRate(float wpm) throws PropertyVetoException;

    /**
     * Set the current synthesizer voice.
     * <p>
     * The list of available voices for a Synthesizer
     * is returned by the getVoices method of the synthesizer's
     * SynthesizerModeDesc.  Any one of the voices
     * returned by that method can be passed to setVoice
     * to set the current speaking voice.
     * <p>
     * Alternatively, the voice parameter may be an application-created
     * partially specified Voice object.
     * If there is no matching voice, the call is ignored.
     * For example, to select a young female voice:
     * <p>
     * Voice voice = new Voice(null
     * GENDER_FEMALE,
     * AGE_CHILD | AGE_TEENAGER,
     * null);
     * synthesizerProperties.setVoice(voice);
     *
     * @throws java.beans.PropertyVetoException if the synthesizer rejects or limits the new value
     * @see javax.speech.synthesis.SynthesizerProperties#getVoice()
     * @see javax.speech.synthesis.SynthesizerModeDesc
     * @see javax.speech.Engine#getEngineModeDesc()
     */
    void setVoice(Voice voice) throws PropertyVetoException;

    /**
     * Set the volume for the synthesizer's speech output as
     * a value between 0.0 and 1.0.  A value of 0.0 indicates
     * silence.  A value of 1.0 is maximum volume and is usually
     * the synthesizer default.
     * <p>
     * A synthesizer may change the voice's style with volume.
     * For example, a quiet volume might produce whispered output
     * and loud might produce shouting.  Most synthesizer do not
     * make this type of change.
     *
     * @throws java.beans.PropertyVetoException if the synthesizer rejects or limits the new value
     * @see javax.speech.synthesis.SynthesizerProperties#getVolume()
     */
    void setVolume(float volume) throws PropertyVetoException;
}
