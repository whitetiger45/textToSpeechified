package javax.speech;

import java.util.Locale;


/**
 * EngineModeDesc provides information about a
 * specific operating mode of a speech engine.
 * The availableRecognizers and availableSynthesizers
 * methods of the Central class provide a list of mode
 * descriptors for all operating modes of registered engines.
 * Applications may also create EngineModeDescs
 * for use in selecting and creating engines.  Examples of uses
 * mode descriptors are provided in the documentation for the
 * <p>
 * class.
 * <p>
 * The properties defined in the EngineModeDesc class
 * apply to all speech engines including speech recognizers
 * and speech synthesizers.  The RecognizerModeDesc
 * and SynthesizerModeDesc classes extend the
 * EngineModeDesc class to define specialized
 * properties for recognizers and synthesizers.
 * <p>
 * The EngineModeDesc and its sub-classes follow the
 * Java Beans set/get property patterns.  The list of properties
 * is outlined below.
 * <p>
 * The properties of EngineModeDesc and its sub-classes
 * are all object references.  All properties are defined so that a
 * null value means "don't care" when selecting an engine
 * or matching EngineModeDesc and its sub-classes.
 * For example, a Boolean value for a property means
 * that its three values are true, false and don't care (null).
 * <p>
 * The basic properties of an engine defined by EngineModeDesc
 * are:
 *
 * @engine name
 * A string that uniquely identifies a speech engine.
 * e.g. "Acme Recognizer"
 * @mode name
 * A string that uniquely identifies a mode of operation of the speech engine.
 * e.g. "Spanish Dictator"
 * @Locale A java.util.Locale object representing the
 * language supported by the engine mode.  The country code
 * may be optionally defined for an engine.  The Locale
 * variant is typically ignored.
 * e.g. Locale("fr", "CA") represent French spoken in Canada.
 * ("fr" and "CA" are standard ISO codes).
 * @Running A Boolean value indicating whether a speech engine
 * is already running.  This allows for the selection of engines that
 * already running so that system resources are conserved.
 * <p>
 * Selection
 * <p>
 * There are two types of EngineModeDesc object (and its sub-classes):
 * those created by a speech engine and those created by an application.
 * Engine-created descriptors are obtained through the
 * availableRecognizers and availableSynthesizers
 * methods of the Central class and must have all features
 * set to non-null values.
 * <p>
 * Applications can create descriptors using the constructors of
 * the descriptor classes.  Applications may leave any or all of the
 * feature values null to indicate "don't care".
 * <p>
 * Typically, application-created descriptors are used to test the
 * engine-created descriptors to select an appropriate engine for creation.
 * For example, the following code tests whether an engine mode supports
 * Swiss German:
 * <pre>
 * EngineModeDesc fromEngine = ...;
 * // "de" is the ISO 639 language code for German
 * // "CH" is the ISO 3166 country code for Switzerland
 * // (see locale for details)
 * EngineModeDesc require = new EngineModeDesc(new Locale("de", "CH"));
 * // test whether the engine mode supports Swiss German.
 * if (fromEngine.match(require)) ...
 * </pre>
 * An application can create a descriptor and pass it to the
 * createRecognizer or createSynthesizer
 * methods of Central.  In this common approach,
 * the Central performs the engine selection.
 * <pre>
 * // Create a mode descriptor that requires French
 * EngineModeDesc desc = new EngineModeDesc(Locale.FRENCH);
 * // Create a synthesizer that supports French
 * Synthesizer synth = Central.createSynthesizer(desc);
 * </pre>
 * Applications that need advanced selection criterion will
 * <ol>
 * <li>Request a list of engine mode descriptors from
 * availableRecognizers or availableSynthesizers,</li>
 * <li>Select one of the descriptors using the methods of
 * EngineList and EngineModeDesc and its sub-classes,</li>
 * <li>Pass the selected descriptor to the createRecognizer or
 * createSynthesizer method of Central.</li>
 * </ol>
 * @see javax.speech.recognition.RecognizerModeDesc
 * @see javax.speech.synthesis.SynthesizerModeDesc
 * @see javax.speech.Central
 */
public class EngineModeDesc {

    private String engineName;

    private String modeName;

    private Locale locale;

    private Boolean running;

    /**
     * Empty constructor sets engine name, mode name, Locale and
     * running all to null.
     */
    public EngineModeDesc() {
        this.engineName = null;
        this.modeName = null;
        this.locale = null;
        this.running = null;
    }

    /**
     * Constructor provided with engine name, mode name, locale and running.
     * Any parameter may be null.
     */
    public EngineModeDesc(String engineName, String modeName, Locale locale, Boolean running) {
        this.engineName = engineName;
        this.modeName = modeName;
        this.locale = locale;
        this.running = running;
    }

    /**
     * Construct an EngineModeDesc for a locale.
     * The engine name, mode name and running are set to null.
     */
    public EngineModeDesc(Locale locale) {
        this.engineName = null;
        this.modeName = null;
        this.locale = locale;
        this.running = null;
    }

    /**
     * True if and only if the parameter is not null
     * and is a EngineModeDesc with equal values of
     * Locale, engineName and modeName.
     */
    public boolean equals(Object anObject) {
        if (anObject != null && anObject instanceof EngineModeDesc) {
            EngineModeDesc engineModeDesc = (EngineModeDesc) anObject;
            if (this.engineName == null) {
                if (engineModeDesc.engineName != null) {
                    return false;
                }
            } else if (!this.engineName.equals(engineModeDesc.engineName)) {
                return false;
            }

            if (this.modeName == null) {
                if (engineModeDesc.modeName != null) {
                    return false;
                }
            } else if (!this.modeName.equals(engineModeDesc.modeName)) {
                return false;
            }

            if (this.locale == null) {
                if (engineModeDesc.locale != null) {
                    return false;
                }
            } else if (!this.locale.equals(engineModeDesc.locale)) {
                return false;
            }

            if (this.running == null != (engineModeDesc.running == null)) {
                return false;
            } else {
                return this.running == null || this.running.equals(engineModeDesc.running);
            }
        } else {
            return false;
        }
    }

    /**
     * Get the engine name. The engine name should be a unique string
     * across the provider company and across companies.
     */
    public String getEngineName() {
        return this.engineName;
    }

    /**
     * Get the Locale.  The locale for an engine mode must have
     * the language defined but the country may be undefined.
     * (e.g. Locale.ENGLISH indicates the English
     * language spoken in any country).  The locale variant is
     * typically ignored.
     */
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * Get the mode name. The mode name that should uniquely identify a
     * single mode of operation of a speech engine (per-engine unique).
     */
    public String getModeName() {
        return this.modeName;
    }

    /**
     * Get the running feature.
     * Values may be TRUE, FALSE or
     * null (null means "don't care").
     */
    public Boolean getRunning() {
        return this.running;
    }

    /**
     * Determine whether an EngineModeDesc has all the features
     * defined in the require object.  Strings in
     * require which are either null or
     * zero-length ("") are not tested, including those in the
     * Locale.  All string comparisons are exact (case-sensitive).
     */
    public boolean match(EngineModeDesc require) {
        if (require.locale != null) {
            if (this.locale == null) {
                return false;
            }

            if (require.locale.getLanguage() != null && !require.locale.getLanguage().equals("") && !require.locale.getLanguage().equals(this.locale.getLanguage())) {
                return false;
            }

            if (require.locale.getCountry() != null && !require.locale.getCountry().equals("") && !require.locale.getCountry().equals(this.locale.getCountry())) {
                return false;
            }
        }

        if (require.modeName != null && !require.modeName.equals("")) {
            if (this.modeName == null) {
                return false;
            }

            if (!this.modeName.equals(require.modeName)) {
                return false;
            }
        }

        if (require.engineName != null && !require.engineName.equals("")) {
            if (this.engineName == null) {
                return false;
            }

            if (!this.engineName.equals(require.engineName)) {
                return false;
            }
        }

        return require.running == null || require.running.equals(this.running);
    }

    /**
     * Set the engine name.
     * May be null.
     */
    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    /**
     * Set the Locale.
     * May be null.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Set the mode name.
     * May be null.
     */
    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    /**
     * Set the running feature.
     * Values may be TRUE, FALSE or
     * null (null means "don't care").
     */
    public void setRunning(Boolean running) {
        this.running = running;
    }
}
