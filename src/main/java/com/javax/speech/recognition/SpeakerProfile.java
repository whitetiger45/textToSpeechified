package javax.speech.recognition;

/**
 * A SpeakerProfile object is used to identify each enrollment by a user
 * to a Recognizer.  SpeakerProfile objects are used in
 * management of speaker data through the SpeakerManager interface
 * for a Recognizer and in selection of recognizers through the
 * RecognizerModeDesc class.
 * <p>
 * A user may have a single or multiple profiles stored in recognizer.  Examples of
 * multiple profiles include a user who enrolls and trains the recognizer
 * separately for different microphones or for different application domains
 * (e.g. romance novels and business email).
 * <p>
 * Each SpeakerProfile object has a unique identifier
 * (unique to the Recognizer), plus a user name
 * and optionally a variant name that identifies each separate profile
 * for a user (per-user unique).  All three identifying properties should be
 * human-readable strings.  (The identifier is often the concatenation of the
 * user name and variant.)
 * <p>
 * The user name may be the same as the "user.name" property
 * stored in the java.lang.System properties. (Note: access
 * to system properties is restricted by Java's SecurityManager.)
 * Appropriate naming of profiles is the joint responsibility of users and
 * recognizers.
 * <p>
 * Calls to the setXXX methods of a SpeakerProfile
 * make persistent changes to the speaker data stored by the recognizer.  These
 * changes are persistent across sessions with the recognizer.
 * <p>
 * SpeakerProfiles are created and managed by the
 * SpeakerManager for a Recognizer.
 * <p>
 * Speaker Data
 * <p>
 * A SpeakerProfile object identifies all the stored data
 * the recognizer has about a speaker in a particular enrollment.
 * The contents of the profile are controlled by the recognizer.  Except
 * for the properties of the SpeakerProfile, this data is
 * not accessible to an application.  The profile may include:
 * <p>
 * Speaker data: full name, age, gender etc.
 * Speaker preferences: including settings of the RecognizerProperties
 * Language models: data about the words and word patterns of the speaker
 * Word models: data about the pronunciation of words by the speaker
 * Acoustic models: data about the speaker's voice
 * Training information and usage history
 * <p>
 * The key role of stored profiles maintaining information that
 * enables a recognition to adapt to characteristics of the speaker.
 * The goal of this adaptation is to improve the performance and
 * accuracy of speech recognition.
 * <p>
 * Speakers in Recognizer Selection
 * <p>
 * When selecting a Recognizer (through javax.speech.Central) a user
 * will generally prefer to select an engine which they have already trained.
 * The getSpeakerProfiles method of a RecognizerModeDesc
 * should return the list of speaker profiles known to a recognizer (or
 * null for speaker-independent recognizers and new recognizers).
 * For that selection process to be effective, a recognizer is responsible for
 * ensuring that the getSpeakerProfiles method of its
 * RecognizerModeDesc includes its complete list of known speakers.
 *
 * @see javax.speech.recognition.SpeakerManager
 * @see javax.speech.recognition.SpeakerManager#newSpeakerProfile(javax.speech.recognition.SpeakerProfile)
 * @see javax.speech.recognition.Recognizer#getSpeakerManager()
 * @see javax.speech.recognition.RecognizerModeDesc#getSpeakerProfiles()
 */
public class SpeakerProfile {

    /**
     * Unique identifier for a SpeakerProfile.
     * (Unique for a Recognizer.)
     *
     * @see javax.speech.recognition.SpeakerProfile#getId()
     */
    protected String id;

    /**
     * Name of user identified by the profile.
     *
     * @see javax.speech.recognition.SpeakerProfile#getName()
     */
    protected String name;

    /**
     * Name of variant enrollment of a user.
     * Should be unique for a user.
     *
     * @see javax.speech.recognition.SpeakerProfile#getName()
     */
    protected String variant;

    /**
     * Null constructor sets all properties too null.
     * <p>
     * Applications can create SpeakerProfile objects
     * for use in selection of engines and when requesting an engine
     * to build a new profile.  A SpeakerProfile
     * created by an application using a SpeakerProfile
     * constructor does not reference a recognizer's profile.
     */
    public SpeakerProfile() {
        this.id = null;
        this.name = null;
        this.variant = null;
    }

    /**
     * Constructor a profile object with all properties specified.
     */
    public SpeakerProfile(String id, String name, String variant) {
        this.id = id;
        this.name = name;
        this.variant = variant;
    }

    /**
     * True if and only if the input parameter is not null
     * and is a SpeakerProfile with equal values of
     * all properties.
     */
    public boolean equals(Object anObject) {
        if (anObject != null && anObject instanceof SpeakerProfile) {
            SpeakerProfile speakerProfile = (SpeakerProfile) anObject;
            if (this.id == null) {
                if (speakerProfile.id != null) {
                    return false;
                }
            } else if (!this.id.equals(speakerProfile.id)) {
                return false;
            }

            if (this.name == null) {
                if (speakerProfile.name != null) {
                    return false;
                }
            } else if (!this.name.equals(speakerProfile.name)) {
                return false;
            }

            if (this.variant == null) {
                if (speakerProfile.variant != null) {
                    return false;
                }
            } else if (!this.variant.equals(speakerProfile.variant)) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the SpeakerProfile identifier.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Return the speaker name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the variant description.
     */
    public String getVariant() {
        return this.variant;
    }

    /**
     * Returns true if this object matches the
     * require object.  A match requires that each non-null
     * or non-zero-length string property of the required object
     * be an exact string match to the properties of this object.
     */
    public boolean match(SpeakerProfile require) {
        if (require.id != null && !require.id.equals(this.id)) {
            return false;
        } else if (require.name != null && !require.name.equals(this.name)) {
            return false;
        } else {
            return require.variant == null || require.variant.equals(this.variant);
        }
    }

    /**
     * Set the SpeakerProfile identifier.
     * The identifier should be a human-readable string.
     * The identifier string must be unique for a recognizer.
     * The identifier is sometimes the concatenation of the
     * user name and variants strings.
     * <p>
     * If the SpeakerProfile object is one returned
     * from a recognizer's SpeakerManager, setting
     * the identifier changes the persistent speaker data of
     * the recognizer.
     *
     * @throws java.lang.IllegalArgumentException if the speaker id is already being used by this recognizer
     */
    public void setId(String identifier) throws IllegalArgumentException {
        this.id = identifier;
    }

    /**
     * Set the speaker name.  The speaker name should be a human-readable string.
     * The speaker name does not need to be unique for
     * a recognizer.  (A speaker with more than
     * one profile must have a separate variant for each).
     * <p>
     * If the SpeakerProfile object is one returned
     * from a recognizer's SpeakerManager, setting
     * the name changes the persistent speaker data of
     * the recognizer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the variant description.
     * The variant should be a human-readable string.
     * A speaker with more than one SpeakerProfile
     * should have a different variant description for each profile.
     * If a speaker has only one profile, the variant description
     * may be null.
     * <p>
     * If the SpeakerProfile object is one returned
     * from a recognizer's SpeakerManager, setting
     * the variant changes the persistent speaker data of
     * the recognizer.
     */
    public void setVariant(String variant) {
        this.variant = variant;
    }
}
