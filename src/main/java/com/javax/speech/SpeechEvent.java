package javax.speech;

import java.util.EventObject;


/**
 * The root event class for all speech events.
 * All events from a speech engine (recognizer or synthesizer)
 * are synchronized with the AWT event queue.  This allows an
 * application to mix speech and AWT events with being concerned
 * with multi-threading problems.
 * <p>
 * Note to Engine Developers
 * <p>
 * The AWT event queue is obtained through the AWT Toolkit:
 * <p>
 * import java.awt.*;
 * ...
 * EventQueue q = Toolkit.getDefaultToolkit().getSystemEventQueue();
 * <p>
 * An engine should create a sub-class of AWTEvent
 * that can be placed on the AWT event queue.  The engine also
 * needs to create a non-visual AWT Component to
 * receive the engine's AWTEvent.  When the
 * AWT event is notified to the engine's component, the engine
 * should issue the appropriate speech event.  The speech event
 * can be issued either from the AWT event thread or from a
 * separate thread created by the speech engine.
 * (Note that SpeechEvent is not a sub-class
 * of AWTEvent so speech events can not be placed
 * directly onto the AWT event queue.)
 */
public class SpeechEvent extends EventObject {

    /**
     * Event identifier.  Id values are defined for each sub-class
     * of SpeechEvent.
     *
     * @see javax.speech.SpeechEvent#getId()
     */
    protected int id;

    /**
     * Constructs a SpeechEvent with a specified source.
     * The source must be non-null.
     */
    protected SpeechEvent(Object source) {
        super(source);
    }

    /**
     * Constructs a SpeechEvent.
     *
     * @param source the object that issued the event
     * @param id     the identifier for the event type
     */
    protected SpeechEvent(Object source, int id) {
        super(source);
        this.id = id;
    }

    /**
     * Return the event identifier.  Id values are defined for each sub-class
     * of SpeechEvent.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns a parameter string identifying this  event.
     * This method is useful for event-logging and for debugging.
     *
     * @return a string identifying the event
     */
    public String paramString() {
        return "unknown type";
    }

    /**
     * Return a printable String.  Useful for event-logging and debugging.
     */
    public String toString() {
        return this.getClass().getName() + "[" + this.paramString() + "] on " + super.source;
    }
}
