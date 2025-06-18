package javax.speech.synthesis;

import java.util.Locale;
import javax.speech.EngineModeDesc;


/**
 * SynthesizerModeDesc extends the EngineModeDesc
 * with properties that are specific to speech synthesizers.
 * A SynthesizerModeDesc inherits engine name,
 * mode name, locale and running properties from EngineModeDesc.
 * SynthesizerModeDesc adds two properties:
 * <p>
 * List of voices provided by the synthesizer
 * Voice to be loaded when the synthesizer is started
 * (not used in selection)
 * <p>
 * Like EngineModeDesc, there are two types of
 * SynthesizerModeDesc: those created by an application
 * which are used in engine selection, and those created by an
 * engine which describe a particular mode of operation of the engine.
 * Descriptor provided engines are obtained through the
 * availableSynthesizers method of the Central
 * class and must have all their features
 * defined.  A descriptor created by an application may make
 * any or all of the features null which means "don't care"
 * (null features are ignored in engine selection).
 * <p>
 * Applications can modify application-created descriptors in
 * any way.  Applications should never modify a SynthesizerModeDesc
 * provided by an engine (i.e. returned by the availableSynthesizers
 * method.
 * <p>
 * Engine creation is described in the documentation for the
 * Central class.
 *
 * @see javax.speech.Central
 * @see javax.speech.Central#createSynthesizer(javax.speech.EngineModeDesc)
 * @see javax.speech.synthesis.Voice
 */
public class SynthesizerModeDesc extends EngineModeDesc {

    private Voice[] voices;

    /**
     * Construct a descriptor with all features set to null.
     */
    public SynthesizerModeDesc() {
        this.voices = null;
    }

    /**
     * Create a fully-specified descriptor.
     * Any of the features may be null.
     */
    public SynthesizerModeDesc(String engineName, String modeName, Locale locale, Boolean running, Voice[] voices) {
        super(engineName, modeName, locale, running);
        this.voices = voices;
    }

    /**
     * Create a SynthesizerModeDesc with a given Locale
     * and other features set to null.
     */
    public SynthesizerModeDesc(Locale locale) {
        super(locale);
        this.voices = null;
    }

    /**
     * Append a voice to the list of voices.
     */
    public void addVoice(Voice v) {
        int index = 0;
        if (this.voices != null) {
            index = this.voices.length;
        }

        Voice[] voices = new Voice[index + 1];
        if (index > 0) {
            System.arraycopy(this.voices, 0, voices, 0, index);
        }

        voices[index] = v;
        this.voices = voices;
    }

    /**
     * Returns true if and only if the parameter is not null
     * and is a SynthesizerModeDesc with equal values of
     * engine name, mode name, locale, running, and all voices.
     */
    public boolean equals(Object anObject) {
        if (anObject != null && anObject instanceof SynthesizerModeDesc) {
            SynthesizerModeDesc synthesizerModeDesc = (SynthesizerModeDesc) anObject;
            if (!super.equals(anObject)) {
                return false;
            } else if (this.voices == null != (synthesizerModeDesc.voices == null)) {
                return false;
            } else {
                if (this.voices != null) {
                    if (this.voices.length != synthesizerModeDesc.voices.length) {
                        return false;
                    }

                    for (int i = 0; i < this.voices.length; ++i) {
                        if (this.voices[i] == null) {
                            if (synthesizerModeDesc.voices[i] != null) {
                                return false;
                            }
                        } else if (!this.voices[i].equals(synthesizerModeDesc.voices[i])) {
                            return false;
                        }
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns the list of voices available in this synthesizer mode.
     */
    public Voice[] getVoices() {
        return this.voices;
    }

    /**
     * Determine whether a SynthesizerModeDesc has all the features
     * specified by the require object.  Features in
     * require which are either null or
     * zero-length strings ("") are not tested (including those contained by
     * Locale).  All string comparisons are exact (case-sensitive).
     * <p>
     * The parameters are used as follows:
     * <p>
     * First, all features of the EngineModeDesc class
     * are compared.  If any test fails, the method returns false.
     * If the require parameter is a SynthesizerModeDesc
     * (or sub-class) then the required voice list is tested as follows.
     * <p>
     * Each voice defined in the required set must match one of the voices
     * in the tested object.  (See Voice.match() for details.)
     * <p>
     * Note: if is possible to compare an EngineModeDesc
     * against a SynthesizerModeDesc and vice versa.
     *
     * @see javax.speech.EngineModeDesc#match(javax.speech.EngineModeDesc)
     * @see javax.speech.synthesis.Voice#match(javax.speech.synthesis.Voice)
     */
    @Override
    public boolean match(EngineModeDesc require) {
        if (!super.match(require)) {
            return false;
        } else if (!(require instanceof SynthesizerModeDesc)) {
            return true;
        } else {
            SynthesizerModeDesc synthesizerModeDesc = (SynthesizerModeDesc) require;
            if (!super.match(synthesizerModeDesc)) {
                return false;
            } else {
                if (synthesizerModeDesc.voices != null) {
                    if (this.voices == null) {
                        return false;
                    }

                    for (int i = 0; i < synthesizerModeDesc.voices.length; ++i) {
                        boolean matched = false;
                        if (synthesizerModeDesc.voices[i] != null) {
                            for (int j = 0; !matched && j < this.voices.length; ++j) {
                                if (this.voices[j].match(synthesizerModeDesc.voices[i])) {
                                    matched = true;
                                }
                            }

                            if (!matched) {
                                return false;
                            }
                        }
                    }
                }

                return true;
            }
        }
    }

    /**
     * Set the list of synthesizer voices.
     */
    public void setVoices(Voice[] v) {
        this.voices = v;
    }
}
