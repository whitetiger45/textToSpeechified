package javax.speech;

import java.security.BasicPermission;


/**
 * This class represents speech permissions.
 * A SpeechPermission contains a target name but
 * no actions list; you either have the named permission
 * or you don't.
 * <p>
 * The target name is the name of the speech permission. The naming
 * convention follows the hierarchical property naming convention.
 * So, an asterisk could be used to represent all speech permissions.
 * <p>
 * In Java Speech API version 1.0 there is a single SpeechPermission -
 * javax.speech.  When that permission is granted an
 * application or applet has access to all the capabilities provided
 * by installed speech recognizers and synthesizers.  Without that
 * permission an application or applet has no access to speech capabilities.
 * <p>
 * As speech technology matures it is anticipated that a finer-grained
 * permission model will be introduced to provide access by applications
 * and applets to some, but not all, speech capabilities.
 * <p>
 * Before granting speech permission, developers and users should
 * consider the potential impact of the grant.
 *
 * <TABLE>
 *
 * <TR>Permission Target Name
 * What the Permission Allows
 * Risks of Allowing this Permission
 * </TR>
 * <p>
 * <TR>
 * <TD>javax.speech</TD>
 * <TD>Creation of a speech recognizer installed on the client.</TD>
 * <TD>A malicious applet could use a RuleGrammar or
 * DictationGrammar to monitor ("bug") a user's
 * office by transmitting the text or audio of results.</TD>
 * </TR>
 * <p>
 * <TR>
 * <TD>Access to user information stored in speaker profiles
 * possibly including user names and identifiers.</TD>
 * <TD>Applet can obtain user names, user identifiers and possibly
 * other sensitive information about users of a system.</TD>
 * </TR>
 * <p>
 * <TR>
 * <TD>Access to EngineModeDesc objects through the
 * the Central class and from engines.</TD>
 * <TD>Applet could obtain information about the speech
 * technology software installed on a system.</TD>
 * </TR>
 *
 * </TABLE>
 */
public final class SpeechPermission extends BasicPermission {

    /**
     * Creates a new SpeechPermission with the specified name.
     * The name is the symbolic name of the SpeechPermission:
     * e.g., javax.speech.
     *
     * @param name the name of the SpeechPermission.
     */
    public SpeechPermission(String name) {
        super(name);
    }

    /**
     * Creates a new SpeechPermission object with the specified name.
     * The name is the symbolic name of the SpeechPermission, and the
     * actions String is currently unused and should be null.
     * This constructor exists for use by the Policy object
     * to instantiate new Permission objects.
     *
     * @param name    the name of the SpeechPermission.
     * @param actions should be null.
     */
    public SpeechPermission(String name, String actions) {
        super(name, actions);
    }
}
