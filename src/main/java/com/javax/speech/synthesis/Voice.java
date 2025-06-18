package javax.speech.synthesis;

/**
 * A description of one output voice of a speech synthesizer.
 * Voice objects can be used in selection of
 * synthesis engines (through the SynthesizerModeDesc).
 * The current speaking voice of a Synthesizer
 * can be changed during operation with the setVoice method
 * of the SynthesizerProperties object.
 *
 * @see javax.speech.synthesis.SynthesizerModeDesc
 * @see javax.speech.synthesis.SynthesizerProperties#setVoice(javax.speech.synthesis.Voice)
 * @see javax.speech.synthesis.Synthesizer
 */
public class Voice implements Cloneable {

    /**
     * Ignore gender when performing a match of voices.
     * Synthesizers never provide a voice with GENDER_DONT_CARE.
     *
     * @see javax.speech.synthesis.Voice#getGender()
     * @see javax.speech.synthesis.Voice#setGender(int)
     */
    public static final int GENDER_DONT_CARE = 65535;

    /**
     * Female voice.
     *
     * @see javax.speech.synthesis.Voice#getGender()
     * @see javax.speech.synthesis.Voice#setGender(int)
     */
    public static final int GENDER_FEMALE = 1;

    /**
     * Male voice.
     *
     * @see javax.speech.synthesis.Voice#getGender()
     * @see javax.speech.synthesis.Voice#setGender(int)
     */
    public static final int GENDER_MALE = 2;

    /**
     * Neutral voice that is neither male nor female
     * (for example, artificial voices, robotic voices).
     *
     * @see javax.speech.synthesis.Voice#getGender()
     * @see javax.speech.synthesis.Voice#setGender(int)
     */
    public static final int GENDER_NEUTRAL = 4;

    /**
     * Ignore age when performing a match.
     * Synthesizers never provide a voice with AGE_DONT_CARE.
     *
     * @see javax.speech.synthesis.Voice#getAge()
     * @see javax.speech.synthesis.Voice#setAge(int)
     */
    public static final int AGE_DONT_CARE = 65535;

    /**
     * Age roughly up to 12 years.
     *
     * @see javax.speech.synthesis.Voice#getAge()
     * @see javax.speech.synthesis.Voice#setAge(int)
     */
    public static final int AGE_CHILD = 1;

    /**
     * Age roughly 13 to 19 years.
     *
     * @see javax.speech.synthesis.Voice#getAge()
     * @see javax.speech.synthesis.Voice#setAge(int)
     */
    public static final int AGE_TEENAGER = 2;

    /**
     * Age roughly 20 to 40 years.
     *
     * @see javax.speech.synthesis.Voice#getAge()
     * @see javax.speech.synthesis.Voice#setAge(int)
     */
    public static final int AGE_YOUNGER_ADULT = 4;

    /**
     * Age roughly 40 to 60 years.
     *
     * @see javax.speech.synthesis.Voice#getAge()
     * @see javax.speech.synthesis.Voice#setAge(int)
     */
    public static final int AGE_MIDDLE_ADULT = 8;

    /**
     * Age roughly 60 years and up.
     *
     * @see javax.speech.synthesis.Voice#getAge()
     * @see javax.speech.synthesis.Voice#setAge(int)
     */
    public static final int AGE_OLDER_ADULT = 16;

    /**
     * Voice with age that is indeterminate.
     * For example, artificial voices, robotic voices.
     *
     * @see javax.speech.synthesis.Voice#getAge()
     * @see javax.speech.synthesis.Voice#setAge(int)
     */
    public static final int AGE_NEUTRAL = 32;

    private String name;

    private int gender;

    private int age;

    private String style;

    /**
     * Empty constructor sets voice name and style to null, and
     * age and gender to "don't care" values.
     */
    public Voice() {
        this.name = null;
        this.gender = 65535;
        this.age = 65535;
        this.style = null;
    }

    /**
     * Constructor provided with voice name, gender, age and style.
     */
    public Voice(String name, int gender, int age, String style) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.style = style;
    }

    /**
     * Create a copy of this Voice.
     *
     * @return A copy of this
     */
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    /**
     * Returns true if and only if the parameter is not null
     * and is a Voice with equal values of name, age, gender,
     * and style.
     */
    public boolean equals(Object anObject) {
        if (anObject != null && anObject instanceof Voice) {
            Voice voice = (Voice) anObject;
            if (this.name == null) {
                if (voice.name != null) {
                    return false;
                }
            } else if (!this.name.equals(voice.name)) {
                return false;
            }

            if (this.style == null) {
                if (voice.style != null) {
                    return false;
                }
            } else if (!this.style.equals(voice.style)) {
                return false;
            }

            return this.gender == voice.gender && this.age == voice.age;
        } else {
            return false;
        }
    }

    /**
     * Get the voice age.  Age values are OR'able.  For example,
     * to test whether a voice is child or teenager (less than 20):
     * <pre>
     * Voice voice = ...;
     * Voice test = new Voice();
     * test.setAge(Voice.AGE_CHILD | Voice.AGE_TEENAGER);
     * if (voice.match(test)) ...
     * </pre
     *
     * @see javax.speech.synthesis.Voice#AGE_CHILD
     * @see javax.speech.synthesis.Voice#AGE_TEENAGER
     * @see javax.speech.synthesis.Voice#AGE_YOUNGER_ADULT
     * @see javax.speech.synthesis.Voice#AGE_MIDDLE_ADULT
     * @see javax.speech.synthesis.Voice#AGE_OLDER_ADULT
     * @see javax.speech.synthesis.Voice#AGE_NEUTRAL
     * @see javax.speech.synthesis.Voice#AGE_DONT_CARE
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Get the voice gender.  Gender values are OR'able.  For example,
     * to test whether a voice is male and/or neutral:
     * <pre>
     * Voice voice = ...;
     * Voice test = new Voice();
     * test.setGender(Voice.GENDER_MALE | Voice.GENDER_NEUTRAL);
     * if (voice.match(test)) ...
     * </pre>
     *
     * @see javax.speech.synthesis.Voice#GENDER_FEMALE
     * @see javax.speech.synthesis.Voice#GENDER_MALE
     * @see javax.speech.synthesis.Voice#GENDER_NEUTRAL
     * @see javax.speech.synthesis.Voice#GENDER_DONT_CARE
     */
    public int getGender() {
        return this.gender;
    }

    /**
     * Get the voice name.  May return null.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the voice style.  This parameter is designed for human
     * interpretation.  Values might include "business", "casual",
     * "robotic", "breathy".
     */
    public String getStyle() {
        return this.style;
    }

    /**
     * Determine whether a Voice has all the features
     * defined in the <code>require</code> object.  Strings in
     * require which are either null or
     * zero-length ("") are ignored.
     * All string comparisons are exact matches (case-sensitive).
     * <p>
     * GENDER_DONT_CARE and <code>AGE_DONT_CARE</code>
     * values in the <code>require</code> object are ignored.
     * The age and gender parameters are OR'ed: e.g. the required age
     * can be AGE_TEENAGER | AGE_CHILD.
     */
    public boolean match(Voice require) {
        if (require.name != null && !require.name.equals("")) {
            if (this.name == null) {
                return false;
            }

            if (!this.name.equals(require.name)) {
                return false;
            }
        }

        if (require.style != null && !require.style.equals("")) {
            if (this.style == null) {
                return false;
            }

            if (!this.style.equals(require.style)) {
                return false;
            }
        }

        if ((require.gender & this.gender) == 0) {
            return false;
        } else {
            return (require.age & this.age) != 0;
        }
    }

    /**
     * Set the voice age.
     *
     * @see javax.speech.synthesis.Voice#AGE_CHILD
     * @see javax.speech.synthesis.Voice#AGE_TEENAGER
     * @see javax.speech.synthesis.Voice#AGE_YOUNGER_ADULT
     * @see javax.speech.synthesis.Voice#AGE_MIDDLE_ADULT
     * @see javax.speech.synthesis.Voice#AGE_OLDER_ADULT
     * @see javax.speech.synthesis.Voice#AGE_NEUTRAL
     * @see javax.speech.synthesis.Voice#AGE_DONT_CARE
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Set the voice gender.
     *
     * @see javax.speech.synthesis.Voice#GENDER_FEMALE
     * @see javax.speech.synthesis.Voice#GENDER_MALE
     * @see javax.speech.synthesis.Voice#GENDER_NEUTRAL
     * @see javax.speech.synthesis.Voice#GENDER_DONT_CARE
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    /**
     * Set the voice name.
     * A null or "" string in voice match means don't care.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the voice style.
     */
    public void setStyle(String style) {
        this.style = style;
    }
}
