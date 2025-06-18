package javax.speech;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.speech.recognition.Recognizer;
import javax.speech.recognition.RecognizerModeDesc;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;


/**
 * The Central class is the initial access point to all
 * speech input and output capabilities.
 * Central provides the ability to locate, select and
 * create speech recognizers and speech synthesizers
 * <p>
 * <A/>
 * Creating a Recognizer or Synthesizer
 * <p>
 * The createRecognizer and createSynthesizer
 * methods are used to create speech engines.  Both methods accept
 * a single parameter that defines the required properties for
 * the engine to be created.  The parameter is an EngineModeDesc
 * and may be one of the sub-classes: RecognizerModeDesc
 * or SynthesizerModeDesc.
 * <p>
 * A mode descriptor defines a set of required properties for an engine.
 * For example, a SynthesizerModeDesc can describe
 * a Synthesizer for Swiss German that has a male voice.
 * Similarly, a RecognizerModeDesc can describe a
 * Recognizer that supports dictation for Japanese.
 * <p>
 * An application is responsible for determining its own functional
 * requirements for speech input/output and providing an appropriate
 * mode descriptor.  There are three cases for mode descriptors:
 * <ol>
 * <li>null
 * <li>Created by the application
 * <li>Obtained from the availableRecognizers
 * or availableSynthesizers methods of Central.
 * </ol>
 * The mode descriptor is passed to the createRecognizer
 * or createSynthesizer methods of Central to create
 * a Recognizer or Synthesizer.  The created
 * engine matches all the engine properties in the mode descriptor passed
 * to the create method.  If no suitable speech engine is available,
 * the create methods return null.
 * <p>
 * The create engine methods operate differently for the three cases.
 * That is, engine selection depends upon the type of the mode descriptor:
 * <ol>
 * <li>null mode descriptor: the Central class
 * selects a suitable engine for the default Locale.
 * <li>Application-created mode descriptor: the Central
 * class attempts to locate an engine with all application-specified
 * properties.
 * <li>Mode descriptor from availableRecognizers
 * or availableSynthesizers: descriptors returned
 * by these two methods identify a specific engine with a
 * specific operating mode. Central creates an instance
 * of that engine.  (Note: these mode descriptors are distinguished
 * because they implement the EngineCreate interface.)
 * </ol>
 * <h3>
 * Case 1: Example
 * </h3>
 * <pre>
 * // Create a synthesizer for the default Locale
 * Synthesizer synth = Central.createSynthesizer(null);
 * </pre>
 * <h3>
 * Case 2: Example
 * </h3>
 * <pre>
 * // Create a dictation recognizer for British English
 * // Note: the UK locale is English spoken in Britain
 * RecognizerModeDesc desc = new RecognizerModeDesc(Locale.UK, Boolean.TRUE);
 * Recognizer rec = Central.createRecognizer(desc);
 * </pre>
 * <h3>
 * Case 3: Example
 * </h3>
 * <pre>
 * // Obtain a list of all German recognizers
 * RecognizerModeDesc desc = new RecognizerModeDesc(Locale.GERMAN);
 * EngineList list = Central.availableRecognizers(desc);
 * // select amongst by other desired engine properties
 * RecognizerModeDesc chosen = ...
 * // create an engine from "chosen" - an engine-provided descriptor
 * Recognizer rec = Central.createRecognizer(chosen);
 * </pre>
 * <h3>
 * Engine Selection Procedure: Cases 1
 * 2
 * </h3>
 * For cases 1 and 2 there is a defined procedure for selecting
 * an engine to be created.  (For case 3, the application
 * can apply it's own selection procedure.)
 * <p>
 * Locale is treated specially in the selection to ensure
 * that language is always considered when selecting an engine.
 * If a locale is not provided, the default locale
 * (java.util.Locale.getDefault) is used.
 * <p>
 * The selection procedure is:
 * <ol>
 * <li>If the locale is undefined add the language of the
 * default locale to the required properties.
 * </li>
 * <li>If a Recognizer or Synthesizer
 * has been created already and it has the required properties,
 * return a reference to it.  (The last created engine is checked.)
 * </li>
 * <li>Obtain a list of all recognizer or synthesizer modes that
 * match the required properties.
 * </li>
 * <li>Amongst the matching engines, give preference to:
 * <ul>
 * <li>A running engine (EngineModeDesc.getRunning is true),
 * <li>An engine that matches the default locale's country.
 * </ul>
 * </ol>
 * When more than one engine is a legal match in the final step, the engines
 * are ordered as returned by the availableRecognizers
 * or availableSynthesizers method.
 * <h3>
 * Security
 * </h3>
 * Access to speech engines is restricted by Java's security system.
 * This is to ensure that malicious applets don't use the speech
 * engines inappropriately.  For example, a recognizer should
 * not be usable without explicit permission because it could be used to
 * monitor ("bug") an office.
 * <p>
 * A number of methods throughout the API throw SecurityException.
 * Individual implementations of Recognizer and Synthesizer
 * may throw SecurityException on additional methods as required
 * to protect a client from malicious applications and applets.
 * <p>
 * The
 * <A href="SpeechPermission.html">SpeechPermission</A>
 * class defines the types of permission that can be granted or
 * denied for applications.  This permission system is based on
 * the JDK 1.2 fine-grained security model.
 * <h3>
 * Engine Registration
 * </h3>
 * The Central class locates, selects and creates
 * speech engines from amongst a list of registered engines.
 * Thus, for an engine to be used by Java applications, the engine
 * must register itself with Central.  There are
 * two registration mechanisms: (1) add an EngineCentral
 * class to a speech properties file, (2) temporarily register
 * an engine by calling the registerEngineCentral method.
 * <p>
 * The speech properties files provide persistent registration
 * of speech engines.  When Central is first called, it
 * looks for properties in two files:
 * <pre>
 * &lt;user.home&gt;/speech.properties
 * &lt;java.home&gt;/lib/speech.properties
 * </pre>
 * where the &lt;user.home&gt; and &lt;java.home&gt;
 * are the values obtained from the System properties object.
 * (The '/' separator will vary across operating systems.)
 * Engines identified in either properties file are made
 * available through the methods of Central.
 * <p>
 * The property files must contain data in the format
 * that is read by the load method of the
 * Properties class.  Central
 * looks for properties of the form
 * <pre>
 * com.acme.recognizer.EngineCentral=com.acme.recognizer.AcmeEngineCentral
 * </pre>
 * This line is interpreted as "the EngineCentral
 * object for the com.acme.recognizer engine
 * is the class called com.acme.recognizer.AcmeEngineCentral.
 * When it is first called, the Central class will
 * attempt to create an instance of each EngineCentral
 * object and will ensure that it implements the EngineCentral
 * interface.
 * <p>
 * Note to engine providers:
 * Central calls each EngineCentral
 * for each call to availableRecognizers or
 * availableSynthesizers and sometimes
 * createRecognizer and createSynthesizer
 * The results are not stored.  The EngineCentral.createEngineList
 * method should be reasonably efficient.
 */
public class Central {

    private static final Logger logger = Logger.getLogger(Central.class.getName());

    private static boolean loadedProperties = false;

    private static Map<String, EngineCentral> centralList = new HashMap<>();

    private static Recognizer lastRecognizer = null;

    private static Synthesizer lastSynthesizer = null;

    /**
     * List EngineModeDesc objects for available recognition
     * engine modes that match the required properties.
     * If the require parameter is null, then all
     * known recognizers are listed.
     * <p>
     * Returns a zero-length list if no engines are available or if no
     * engines have the required properties.  (The method never returns null).
     * <p>
     * The order of the EngineModeDesc objects in the list
     * is partially defined.  For each registered engine (technically,
     * each registered EngineCentral object) the order
     * of the descriptors is preserved.  Thus, each installed speech
     * engine should order its descriptor objects with the most
     * useful modes first, for example, a mode that is already loaded
     * and running on a desktop.
     *
     * @param require an EngineModeDesc or RecognizerModeDesc
     *                defining the required features of the mode descriptors in the returned list
     * @return list of mode descriptors with the required properties
     * @throws java.lang.SecurityException if the caller does not have permission to use speech recognition
     */
    @SuppressWarnings("unchecked")
    public static synchronized EngineList availableRecognizers(EngineModeDesc require) throws SecurityException {
        if (require == null) {
            require = new RecognizerModeDesc();
        } else if (!(require instanceof RecognizerModeDesc)) {
            require = new RecognizerModeDesc(require.getEngineName(), require.getModeName(), require.getLocale(), require.getRunning(), null, null);
        }

        loadProps();
        EngineList engineList = new EngineList();
        Iterator<?> e = centralList.values().iterator();

        while (true) {
            EngineList el;
            do {
                if (!e.hasNext()) {
                    return engineList;
                }

                EngineCentral engineCentral = (EngineCentral) e.next();
                el = engineCentral.createEngineList(require);
            } while (el == null);

            for (int i = 0; i < el.size(); ++i) {
                if (el.elementAt(i) instanceof RecognizerModeDesc && el.elementAt(i) instanceof EngineCreate) {
                    engineList.addElement(el.elementAt(i));
                }
            }
        }
    }

    /**
     * List EngineModeDesc objects for available synthesis
     * engine modes that match the required properties.
     * If the require parameter is null, then all available
     * known synthesizers are listed.
     * <p>
     * Returns an empty list (rather than null) if no engines are available or if no
     * engines have the required properties.
     * <p>
     * The order of the EngineModeDesc objects in the list
     * is partially defined.  For each speech installation (technically,
     * each registered EngineCentral object) the order
     * of the descriptors is preserved.  Thus, each installed speech
     * engine should order its descriptor objects with the most
     * useful modes first, for example, a mode that is already loaded
     * and running on a desktop.
     *
     * @throws java.lang.SecurityException if the caller does not have permission to use speech engines
     */
    @SuppressWarnings("unchecked")
    public static synchronized EngineList availableSynthesizers(EngineModeDesc require) throws SecurityException {
        if (require == null) {
            require = new SynthesizerModeDesc();
        } else if (!(require instanceof SynthesizerModeDesc)) {
            require = new SynthesizerModeDesc(require.getEngineName(), require.getModeName(), require.getLocale(), require.getRunning(), null);
        }

        loadProps();
        EngineList engineList = new EngineList();
        Iterator<?> e = centralList.values().iterator();

        while (true) {
            EngineList el;
            do {
                if (!e.hasNext()) {
                    return engineList;
                }

                EngineCentral central = (EngineCentral) e.next();
                el = central.createEngineList(require);
            } while (el == null);

            for (int i = 0; i < el.size(); ++i) {
                if (el.elementAt(i) instanceof SynthesizerModeDesc && el.elementAt(i) instanceof EngineCreate) {
                    engineList.addElement(el.elementAt(i));
                }
            }
        }
    }

    /**
     * Create a Recognizer with specified required properties.
     * If there is no Recognizer with the required properties
     * the method returns null.
     * <p>
     * The required properties defined in the input parameter may be
     * provided as either an EngineModeDesc object or
     * a RecognizerModeDesc object.  The input parameter
     * may also be null, in which case an engine is
     * selected that supports the language of the default locale.
     * <p>
     * A non-null mode descriptor may be either application-created
     * or a mode descriptor returned by the availableRecognizers method.
     * <p>
     * The mechanisms for
     * <A href="#creatingEngines">creating a Recognizer</A>
     * are described above in detail.
     *
     * @param require required engine properties or null for
     *                default engine selection
     * @return a recognizer matching the required properties or null
     * if none is available
     * @throws java.lang.IllegalArgumentException if the properties of the EngineModeDesc do not refer to a known
     *                                            engine or engine mode.
     * @throws javax.speech.EngineException       if the engine defined by this RecognizerModeDesc could not be properly created.
     * @throws java.lang.SecurityException        if the caller does not have createRecognizer permission
     * @see javax.speech.Central#availableRecognizers(javax.speech.EngineModeDesc)
     * @see javax.speech.recognition.RecognizerModeDesc
     */
    public static synchronized Recognizer createRecognizer(EngineModeDesc require) throws IllegalArgumentException, EngineException, SecurityException {
        if (require == null) {
            require = new RecognizerModeDesc();
        }

        if (require instanceof EngineCreate) {
            EngineCreate engineCreate = (EngineCreate) require;
            return (Recognizer) engineCreate.createEngine();
        } else {
            boolean useDefault = false;
            String language = Locale.getDefault().getLanguage();
            String country = Locale.getDefault().getCountry();
            if (require.getLocale() == null) {
                require.setLocale(new Locale(language, ""));
                useDefault = true;
            }

            if (lastRecognizer != null && lastRecognizer.getEngineModeDesc().match(require)) {
                if (useDefault) {
                    require.setLocale(null);
                }

                return lastRecognizer;
            } else {
                EngineList engineList = availableRecognizers(require);
                if (engineList.isEmpty()) {
                    return null;
                } else {
                    if (useDefault) {
                        require.setLocale(null);
                    }

                    if (useDefault) {
                        engineList.orderByMatch(new EngineModeDesc(new Locale(language, country)));
                    }

                    if (require.getRunning() == null) {
                        EngineModeDesc engineModeDesc = new EngineModeDesc();
                        engineModeDesc.setRunning(Boolean.TRUE);
                        engineList.orderByMatch(engineModeDesc);
                    }

                    for (int i = 0; i < engineList.size(); ++i) {
                        try {
                            EngineCreate engineCreate = (EngineCreate) engineList.elementAt(i);
                            Recognizer recognizer = (Recognizer) engineCreate.createEngine();
                            lastRecognizer = recognizer;
                            return recognizer;
                        } catch (IllegalArgumentException | EngineException e) {
                            logger.log(Level.FINER, e.getMessage(), e);
                        }
                    }

                    return null;
                }
            }
        }
    }

    /**
     * Create a Synthesizer with specified required properties.
     * If there is no Synthesizer with the required properties
     * the method returns null.
     * <p>
     * The required properties defined in the input parameter may be
     * provided as either an EngineModeDesc object or
     * a SynthesizerModeDesc object.  The input parameter
     * may also be null, in which case an engine is
     * selected that supports the language of the default locale.
     * <p>
     * A non-null mode descriptor may be either application-created
     * or a mode descriptor returned by the availableSynthesizers method.
     * <p>
     * The mechanisms for
     * <A href="#creatingEngines">creating a Synthesizer</A>
     * are described above in detail.
     *
     * @param require required engine properties or null for
     *                default engine selection
     * @return a Synthesizer matching the required properties or null
     * if none is available
     * @throws java.lang.IllegalArgumentException if the properties of the EngineModeDesc do not refer to a known
     *                                            engine or engine mode.
     * @throws javax.speech.EngineException       if the engine defined by this SynthesizerModeDesc could not be properly created.
     * @see javax.speech.Central#availableSynthesizers(javax.speech.EngineModeDesc)
     * @see javax.speech.synthesis.SynthesizerModeDesc
     */
    public static synchronized Synthesizer createSynthesizer(EngineModeDesc require) throws IllegalArgumentException, EngineException {
        if (require == null) {
            require = new SynthesizerModeDesc();
        }

        if (require instanceof EngineCreate) {
            EngineCreate engineCreate = (EngineCreate) require;
            return (Synthesizer) engineCreate.createEngine();
        } else {
            boolean useDefault = false;
            String language = Locale.getDefault().getLanguage();
            String country = Locale.getDefault().getCountry();
            if (require.getLocale() == null) {
                require.setLocale(new Locale(language, ""));
                useDefault = true;
            }

            if (lastSynthesizer != null && lastSynthesizer.getEngineModeDesc().match(require)) {
                if (useDefault) {
                    require.setLocale(null);
                }

                return lastSynthesizer;
            } else {
                EngineList engineList = availableSynthesizers(require);
                if (engineList.isEmpty()) {
                    return null;
                } else {
                    if (useDefault) {
                        require.setLocale(null);
                    }

                    if (useDefault) {
                        engineList.orderByMatch(new EngineModeDesc(new Locale(language, country)));
                    }

                    if (require.getRunning() == null) {
                        EngineModeDesc engineModeDesc = new EngineModeDesc();
                        engineModeDesc.setRunning(Boolean.TRUE);
                        engineList.orderByMatch(engineModeDesc);
                    }

                    for (int i = 0; i < engineList.size(); ++i) {
                        try {
                            EngineCreate engineCreate = (EngineCreate) engineList.elementAt(i);
                            Synthesizer synthesizer = (Synthesizer) engineCreate.createEngine();
                            lastSynthesizer = synthesizer;
                            return synthesizer;
                        } catch (IllegalArgumentException | EngineException e) {
                            logger.log(Level.FINER, e.getMessage(), e);
                        }
                    }

                    return null;
                }
            }
        }
    }

    private static synchronized void loadProps() {
        if (!loadedProperties) {
            loadedProperties = true;
            String FS = File.separator;
            String[] paths = new String[] {System.getProperty("user.home") + FS + "speech.properties", System.getProperty("java.home") + FS + "lib" + FS + "speech.properties"};

            for (String path : paths) {
                Properties props = new Properties();
                File file = new File(path);

                try {
                    FileInputStream dis = new FileInputStream(file);
                    props.load(new BufferedInputStream(dis));
                    dis.close();
                } catch (IOException e) {
                    logger.log(Level.FINER, e.getMessage(), e);
                }

                Enumeration<?> names = props.propertyNames();

                while (names.hasMoreElements()) {
                    String name = (String) names.nextElement();
                    if (name.endsWith("EngineCentral")) {
                        String value = props.getProperty(name);

                        try {
                            registerEngineCentral(value);
                        } catch (EngineException e) {
                            logger.log(Level.FINER, e.getMessage(), e);
                        }
                    }
                }
            }
        }
    }

    /**
     * Register a speech engine with the Central class
     * for use by the current application. This call adds the specified
     * class name to the list of EngineCentral objects.
     * The registered engine is not stored persistently in the
     * properties files.
     * If className is already registered, the call has no effect.
     * <p>
     * The class identified by className must have an
     * empty constructor.
     *
     * @param className name of a class that implements the EngineCentral
     *                  interface and provides access to an engine implementation
     * @throws javax.speech.EngineException if className is not a legal class or it does not
     *                                      implement the EngineCentral interface
     */
    public static synchronized void registerEngineCentral(String className) throws EngineException {
        if (!centralList.containsKey(className)) {
            Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new EngineException("javax.speech.Central: no class found for " + className);
            }

            try {
                EngineCentral engineCentral = (EngineCentral) clazz.newInstance();
                centralList.put(className, engineCentral);
            } catch (Exception e) {
                throw new EngineException("javax.speech.Central: error creating EngineCentral from " + className);
            }
        }
    }
}
