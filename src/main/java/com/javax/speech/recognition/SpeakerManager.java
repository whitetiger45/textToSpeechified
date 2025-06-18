package javax.speech.recognition;

import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.speech.VendorDataException;


/**
 * Provides control of SpeakerProfiles for a Recognizer.
 * The SpeakerManager for a Recognizer
 * is obtained through its getSpeakerManager method.
 * Recognizers that do not maintain speaker profiles - known as
 * speaker-independent recognizers return null for this method.
 * <p>
 * Each SpeakerProfile stored with a Recognizer
 * stored information about an enrollment of a user with the recognizer.
 * The user information allows the recognizer to adapt to the
 * characteristic of the user with the goal of improving performance
 * and recognition accuracy.  For example, the recognizer might adjust
 * to vocabulary preferences and accent.
 * <p>
 * The role of the SpeakerManager is provided access to the
 * known SpeakerProfiles, to enable storage and loading of
 * the profiles once a recognizer is running, and to provide other
 * management functions (storage to file, deletion etc.).  The SpeakerManager
 * has a "current speaker" - the profile which is currently being used
 * by the recognizer.
 * <p>
 * Storing and Loading
 * <p>
 * Speaker data is typically persistent - a user will want their profile
 * to be available from session to session.  An application must explicitly
 * request a recognizer to save a speaker profile.  It is good practice
 * to check with a user before storing their speaker profile in case it
 * has become corrupted.
 * <p>
 * The SpeakerManager interface provides a revert method
 * which requests the engine to restore the last saved profile (possibly the
 * profile loaded at the start of a session).
 * <p>
 * The speaker profile is potentially a large amount of data
 * (often up to several MByte).  So loading and storing profiles,
 * reverting to an old profile and changing speakers may all be
 * slow operations.
 * <p>
 * The SpeakerManager provides methods to load and
 * store speaker profiles to and from streams (e.g. files, URLs).
 * Speaker profiles contain engine-specific and often proprietary
 * information, so a speaker profile from one recognizer can not usually
 * be loaded into a recognizer from a different company.
 * <p>
 * The SpeakerManager for a Recognizer can
 * be obtained from the Recognizer in any state of
 * the recognizer.  However, most methods of the SpeakerManager
 * operate correctly only when the Recognizer is in
 * the ALLOCATED.
 * <p>
 * The getCurrentSpeaker, setCurrentSpeaker
 * and listKnownSpeakers methods operate in any
 * Recognizer state.  This allows the initial speaker
 * profile for a Recognizer to be loaded at allocation
 * time.
 *
 * @see javax.speech.recognition.SpeakerProfile
 * @see javax.speech.recognition.RecognizerModeDesc#getSpeakerProfiles()
 * @see javax.speech.Central
 * @see java.lang.System
 */
public interface SpeakerManager {

    /**
     * Delete a SpeakerProfile.
     * If the deleted speaker is the current speaker,
     * the current speaker is set to null.
     *
     * @throws java.lang.IllegalArgumentException if the speaker is not known
     */
    void deleteSpeaker(SpeakerProfile speaker) throws IllegalArgumentException;

    /**
     * Obtain a component that provides the engine's user interface
     * for managing speaker data and training.
     * If this Recognizer has no default control panel,
     * the return value is null and the application is
     * responsible for providing an appropriate control panel.
     * <p>
     * Note: because the interface is provided by the recognizer, it
     * may allow the management of properties that are not otherwise
     * accessible through the standard API.
     */
    Component getControlComponent();

    /**
     * Get the current SpeakerProfile.
     * Returns null if there is no current speaker.
     */
    SpeakerProfile getCurrentSpeaker();

    /**
     * List the SpeakerProfiles known to this Recognizer.
     * Returns null if there is no known speaker.
     */
    SpeakerProfile[] listKnownSpeakers();

    /**
     * Create a new SpeakerProfile for this Recognizer.
     * The SpeakerProfile object returned by this method
     * is different from the object passed to the method.
     * The input profile contains the new id, name and variant.  The
     * returned object is a reference to a recognizer-internal profile with
     * those settings but with all the additional recognizer-specific
     * information associated with a profile.
     * <p>
     * This method does not change the current speaker.
     * <p>
     * If the input profile's identifier or user name is not specified (is null),
     * the recognizer should assign a unique temporary identifier.
     * The application should request that the user update the id.
     * <p>
     * If the input profile is null, the recognizer should
     * assign a temporary id and user name.  The application should
     * query the user for details.
     *
     * @throws java.lang.IllegalArgumentException if the speaker id is already being used
     */
    SpeakerProfile newSpeakerProfile(SpeakerProfile profile) throws IllegalArgumentException;

    /**
     * Read a SpeakerProfile from a stream and return a
     * reference to it.  This method loads data that may have
     * been stored previously with the writeVendorSpeakerProfile
     * method.
     * <p>
     * If the speaker profile contained in the input stream
     * already exists, the recognizer should create a modified name.
     * An application should inform the user of the name that is loaded and
     * consider giving them an option to modify it.
     * <p>
     * Since speaker profiles are stored in vendor-specific
     * formats, they can only be loaded into a recognizer that understands
     * that format - typically a recognizer from the same provider.
     * <p>
     * Note: The speaker profile is potentially large (up to several MByte).
     *
     * @throws javax.speech.VendorDataException if the data format is not known to the recognizer
     * @throws java.io.IOException              if an I/O error occurs
     * @see SpeakerManager#writeVendorSpeakerProfile(java.io.OutputStream, SpeakerProfile)
     */
    SpeakerProfile readVendorSpeakerProfile(InputStream in) throws IOException, VendorDataException;

    /**
     * Restore the speaker profile for the current speaker to the last
     * saved version.  If the speaker profile has not been saved during
     * the session, the restored version will be the version loaded at
     * the start of the session.
     * <p>
     * Because of the large potential size of the speaker profile,
     * this may be a slow operation.
     *
     * @see javax.speech.recognition.SpeakerManager#saveCurrentSpeakerProfile()
     * @see javax.speech.recognition.SpeakerManager#readVendorSpeakerProfile(java.io.InputStream)
     */
    void revertCurrentSpeaker();

    /**
     * Save the speaker profile for the current speaker.
     * The speaker profile is stored internally by the recognizer
     * and should be available for future sessions.
     * <p>
     * Because of the large potential size of the speaker profile,
     * this may be a slow operation.
     *
     * @see javax.speech.recognition.SpeakerManager#revertCurrentSpeaker()
     * @see SpeakerManager#writeVendorSpeakerProfile(java.io.OutputStream, SpeakerProfile)
     */
    void saveCurrentSpeakerProfile();

    /**
     * Set the current SpeakerProfile.  The SpeakerProfile
     * object should be one of the objects returned by the listKnownSpeakers
     * method.
     * <p>
     * Because a SpeakerProfile may store preferred user
     * settings for the RecognizerProperties, those properties
     * may change as a result of this call.
     *
     * @throws java.lang.IllegalArgumentException if the speaker is not known
     */
    void setCurrentSpeaker(SpeakerProfile speaker) throws IllegalArgumentException;

    /**
     * Write the speaker profile of the named speaker to a stream.
     * This method allows speaker data to be stored and to be
     * transferred between machines.
     * <p>
     * The speaker profile is stored in a vendor-specific
     * format, so it can only be loaded into a recognizer that understands
     * that format - typically a recognizer from the same provider.
     * Speaker profiles are loaded with the readVendorSpeakerProfile
     * method.
     * <p>
     * Note: The speaker profile is potentially large (up to several MByte).
     *
     * @throws java.io.IOException if an I/O error occurs
     * @see javax.speech.recognition.SpeakerManager#readVendorSpeakerProfile(java.io.InputStream)
     */
    void writeVendorSpeakerProfile(OutputStream out, SpeakerProfile speaker) throws IOException;
}
