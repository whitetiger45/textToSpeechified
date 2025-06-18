package javax.speech.recognition;

import java.util.Locale;
import javax.speech.EngineModeDesc;


/**
 * RecognizerModeDesc extends the EngineModeDesc
 * with properties that are specific to speech recognizers.
 * A RecognizerModeDesc inherits engine name,
 * mode name and Locale from EngineModeDesc.
 * RecognizerModeDesc adds three features:
 * <p>
 * A Boolean indicating whether dictation is supported
 * List of the recognizer's speaker profiles
 * Speaker profile that  will be loaded when
 * a recognizer is started (not used in selection)
 * <p>
 * Like EngineModeDesc, there are two types of
 * RecognizerModeDesc: those created by an application
 * for use in engine selection, and those created by an
 * engine which describe a particular mode of operation of the engine.
 * Descriptors provided by engines are obtained through the
 * availableRecognizers method of the Central
 * class.  These descriptors must have all their features
 * defined.  A descriptor created by an application may make
 * any or all of the features null to indicate a "don't care"
 * value (null features are ignored in engine selection).
 * <p>
 * [Note: the Boolean "is running" feature
 * is a full object rather than a boolean primitive
 * so that it can represent true, false and "don't care".]
 * <p>
 * Applications can modify application-created descriptors in
 * any way.  Applications should never modify the features of
 * a RecognizerModeDesc provided by an engine
 * (i.e. returned by the availableRecognizers
 * method).
 * <p>
 * Engine creation is described in the documentation for the
 * Central class.
 *
 * @see javax.speech.Central
 * @see javax.speech.recognition.SpeakerManager
 * @see javax.speech.recognition.SpeakerProfile
 */
public class RecognizerModeDesc extends EngineModeDesc {

    private Boolean dictationGrammarSupported;

    private SpeakerProfile[] profiles;

    /**
     * Construct a descriptor with null values ("don't care")
     * for all recognizer features.
     */
    public RecognizerModeDesc() {
        this.dictationGrammarSupported = null;
        this.profiles = null;
    }

    /**
     * Create a fully-specified descriptor.
     */
    public RecognizerModeDesc(String engineName, String modeName, Locale locale, Boolean running,
                              Boolean dictationGrammarSupported, SpeakerProfile[] profiles) {
        super(engineName, modeName, locale, running);
        this.dictationGrammarSupported = dictationGrammarSupported;
        this.profiles = profiles;
    }

    /**
     * Create a RecognizerModeDesc given a Locale
     * and the dictation flag.  The speaker profiles array
     * and other features are all null.
     */
    public RecognizerModeDesc(Locale locale, Boolean dictationGrammarSupported) {
        super(locale);
        this.dictationGrammarSupported = dictationGrammarSupported;
        this.profiles = null;
    }

    /**
     * Add a speaker profile to the SpeakerProfile array.
     */
    public synchronized void addSpeakerProfile(SpeakerProfile profile) {
        if (this.profiles == null) {
            this.profiles = new SpeakerProfile[1];
            this.profiles[0] = profile;
        } else {
            SpeakerProfile[] profiles = new SpeakerProfile[this.profiles.length + 1];
            System.arraycopy(this.profiles, 0, profiles, 0, this.profiles.length);
            profiles[profiles.length - 1] = profile;
            this.profiles = profiles;
        }
    }

    /**
     * True if and only if the input parameter is not null
     * and is a RecognizerModeDesc with equal values of
     * dictationGrammarSupported and all speaker profiles.
     */
    public boolean equals(Object anObject) {
        if (anObject != null && anObject instanceof RecognizerModeDesc) {
            RecognizerModeDesc recognizerModeDesc = (RecognizerModeDesc) anObject;
            if (!super.equals(anObject)) {
                return false;
            } else if (this.dictationGrammarSupported == null != (recognizerModeDesc.dictationGrammarSupported == null)) {
                return false;
            } else if (this.dictationGrammarSupported != null && !this.dictationGrammarSupported.equals(recognizerModeDesc.dictationGrammarSupported)) {
                return false;
            } else if (this.profiles == null != (recognizerModeDesc.profiles == null)) {
                return false;
            } else {
                if (this.profiles != null) {
                    if (this.profiles.length != recognizerModeDesc.profiles.length) {
                        return false;
                    }

                    for (int i = 0; i < this.profiles.length; ++i) {
                        if (this.profiles[i] == null) {
                            if (recognizerModeDesc.profiles[i] != null) {
                                return false;
                            }
                        } else if (!this.profiles[i].equals(recognizerModeDesc.profiles[i])) {
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
     * Returns the list of speaker profiles known to this mode
     * of this recognition engine.  Returns null if speaker training
     * is not supported (SpeakerManager not implemented).  Returns
     * zero-length array if speaker training is supported but no
     * speaker profiles have been constructed yet.
     * <p>
     * The list of speaker profiles is the same as returned by the
     * listKnownSpeakers method of SpeakerManager
     * if this engine is running.
     *
     * @throws java.lang.SecurityException if the application does not have accessSpeakerProfiles permission
     * @see javax.speech.recognition.SpeakerManager#listKnownSpeakers()
     */
    public final SpeakerProfile[] getSpeakerProfiles() throws SecurityException {
        return this.getSpeakerProfilesImpl();
    }

    /**
     * Version of getSpeakerProfiles that performs the
     * operation.  This method can be overridden in
     * sub-classes.  However, application can only call
     * the getSpeakerProfiles method which
     * does a security check.
     *
     * @see javax.speech.recognition.RecognizerModeDesc#getSpeakerProfiles()
     */
    protected SpeakerProfile[] getSpeakerProfilesImpl() {
        return this.profiles;
    }

    /**
     * Test whether this engine mode provides a DictationGrammar.
     * The value may be TRUE, FALSE or null.
     * A null value means "don't care".
     */
    public Boolean isDictationGrammarSupported() {
        return this.dictationGrammarSupported;
    }

    /**
     * Determine whether a RecognizerModeDesc has all the features
     * defined in the require object.  Strings in
     * require which are either null or
     * zero-length ("") are not tested, including those in the
     * Locale.  All string comparisons are exact (case-sensitive).
     * <p>
     * The parameters are used as follows:
     * <p>
     * If the require parameter is an EngineModeDesc then
     * only the EngineModeDesc features are tested
     * (engine name, mode name, locale).
     * If the require parameter is a RecognizerModeDesc (or sub-class) then
     * the grammar supported flags and required speakers are tested as follows.
     * <p>
     * Every speaker profile in the required set must be matched by
     * a profile in the tested object.  A null require speakers
     * value is ignored.
     * Match dictation supported Boolean value if
     * the required value is null or if exact boolean match.
     */
    @Override
    public boolean match(EngineModeDesc require) {
        if (!super.match(require)) {
            return false;
        } else if (!(require instanceof RecognizerModeDesc)) {
            return true;
        } else {
            RecognizerModeDesc recognizerModeDesc = (RecognizerModeDesc) require;
            if (recognizerModeDesc.dictationGrammarSupported != null && !recognizerModeDesc.dictationGrammarSupported.equals(this.dictationGrammarSupported)) {
                return false;
            } else {
                if (recognizerModeDesc.profiles != null && recognizerModeDesc.profiles.length > 0) {
                    if (this.profiles == null) {
                        return false;
                    }

                    for (int i = 0; i < recognizerModeDesc.profiles.length; ++i) {
                        boolean match = false;

                        for (int j = 0; !match && j < this.profiles.length; ++j) {
                            if (this.profiles[j].match(recognizerModeDesc.profiles[i])) {
                                match = true;
                            }
                        }

                        if (!match) {
                            return false;
                        }
                    }
                }

                return true;
            }
        }
    }

    /**
     * Set the dictationGrammarSupported parameter.
     * The value may be TRUE, FALSE or null.
     * A null value means "don't care".
     */
    public void setDictationGrammarSupported(Boolean dictationGrammarSupported) {
        this.dictationGrammarSupported = dictationGrammarSupported;
    }

    /**
     * Set the list of speaker profiles.
     * May be null.
     */
    public void setSpeakerProfiles(SpeakerProfile[] speakers) {
        this.profiles = speakers;
    }
}
