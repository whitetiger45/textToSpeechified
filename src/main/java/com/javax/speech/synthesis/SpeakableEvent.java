package javax.speech.synthesis;

import javax.speech.SpeechEvent;


/**
 * Event issued during spoken output of text.
 * <p>
 * SpeakableEvents are issued to SpeakableListeners.
 * A single SpeakableListener can be provided to any of the
 * speak and speakPlainText methods of
 * a Synthesizer to monitor progress of a single
 * item on the speech output queue.  Any number of
 * SpeakableListener objects can be attached to a
 * Synthesizer with the addSpeakableListener
 * method.  These listeners receive every SpeakableEvent
 * for every item on the speech output queue of the Synthesizer.
 * The SpeakableListener attached to an individual
 * item on the speech output queue is notified before the
 * SpeakableListeners attached to the Synthesizer.
 * <p>
 * The source for a SpeakableEvent is the object
 * from which the JSML text was obtained: a Speakable object,
 * a URL, or a String.
 * <p>
 * The normal sequence of events during output of the item of the
 * top of the synthesizer's speech output is:
 * <p>
 * TOP_OF_QUEUE
 * SPEAKABLE_STARTED
 * Any number of WORD_STARTED and MARKER_REACHED events
 * SPEAKABLE_ENDED
 * <p>
 * A SPEAKABLE_PAUSED may occur any time after the
 * TOP_OF_QUEUE but before the SPEAKABLE_ENDED
 * event.  A SPEAKABLE_PAUSED event can only be
 * followed by a SPEAKABLE_RESUMED or SPEAKABLE_CANCELLED.
 * <p>
 * A SPEAKABLE_CANCELLED event can occur at any time before
 * an SPEAKABLE_ENDED (including before a TOP_OF_QUEUE
 * event).  No other events can follow the SPEAKABLE_CANCELLED event
 * since the item has been removed from the speech output queue.
 * <p>
 * A SPEAKABLE_CANCELLED event can be issued for items
 * that are not at the top of the speech output queue.  The other events
 * are only issued for the top-of-queue item.
 */
public class SpeakableEvent extends SpeechEvent {

    /**
     * Issued when an item on the synthesizer's speech output queue reaches
     * the top of the queue.  If the Synthesizer
     * is not paused, the TOP_OF_QUEUE event will be followed
     * immediately by the SPEAKABLE_STARTED event.  If the
     * Synthesizer is paused, the SPEAKABLE_STARTED
     * event will be delayed until the Synthesizer is resumed.
     * <p>
     * A QUEUE_UPDATED is also issued when the speech
     * output queue changes (e.g. a new item at the top of the queue).
     * The SpeakableEvent is issued prior to the
     * SynthesizerEvent.
     *
     * @see javax.speech.synthesis.SpeakableListener#topOfQueue(javax.speech.synthesis.SpeakableEvent)
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_UPDATED
     * @see javax.speech.Engine#PAUSED
     */
    public static final int TOP_OF_QUEUE = 601;

    /**
     * Issued at the start of audio output of an item on the speech output queue.
     * This event immediately follows the TOP_OF_QUEUE
     * unless the Synthesizer is paused when the speakable
     * text is promoted to the top of the output queue.
     *
     * @see javax.speech.synthesis.SpeakableListener#speakableStarted(javax.speech.synthesis.SpeakableEvent)
     */
    public static final int SPEAKABLE_STARTED = 602;

    /**
     * Issued with the completion of audio output of an object on
     * the speech output queue as the object is removed from the queue.
     * <p>
     * A QUEUE_UPDATED or QUEUE_EMPTIED event is also
     * issued when the speech output queue changes because the speech output
     * of the item at the top of queue is completed.
     * The SpeakableEvent is issued prior to the
     * SynthesizerEvent.
     *
     * @see javax.speech.synthesis.SpeakableListener#speakableEnded(javax.speech.synthesis.SpeakableEvent)
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_EMPTIED
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_UPDATED
     */
    public static final int SPEAKABLE_ENDED = 603;

    /**
     * Issued when audio output of the item at the top of a
     * synthesizer's speech output queue is paused.
     * The SPEAKABLE_PAUSED SpeakableEvent
     * is issued prior to the ENGINE_PAUSED event
     * that is issued to the SynthesizerListener.
     *
     * @see javax.speech.synthesis.SpeakableListener#speakablePaused(javax.speech.synthesis.SpeakableEvent)
     * @see javax.speech.EngineEvent#ENGINE_PAUSED
     */
    public static final int SPEAKABLE_PAUSED = 604;

    /**
     * Issued when audio output of the item at the top of a
     * synthesizer's speech output queue is resumed after a previous pause.
     * The SPEAKABLE_RESUMED SpeakableEvent
     * is issued prior to the ENGINE_RESUMED event
     * that is issued to the SynthesizerListener.
     *
     * @see javax.speech.synthesis.SpeakableListener#speakableResumed(javax.speech.synthesis.SpeakableEvent)
     * @see javax.speech.EngineEvent#ENGINE_RESUMED
     */
    public static final int SPEAKABLE_RESUMED = 605;

    /**
     * Issued when an item on the synthesizer's speech output queue is
     * cancelled and removed from the queue.  A speech output queue item
     * may be cancelled at any time following a call to speak.
     * An item can be cancelled even if it is not at the top of the
     * speech output queue (other SpeakableEvents are
     * issued only to the top-of-queue item).
     * Once cancelled, the listener for the cancelled object receives
     * no further SpeakableEvents.
     * <p>
     * The SPEAKABLE_CANCELLED SpeakableEvent
     * is issued prior to the QUEUE_UPDATED or
     * QUEUE_EMPTIED event that is issued to the
     * SynthesizerListener.
     *
     * @see javax.speech.synthesis.SpeakableListener#speakableCancelled(javax.speech.synthesis.SpeakableEvent)
     * @see javax.speech.synthesis.Synthesizer#cancel()
     * @see javax.speech.synthesis.Synthesizer#cancel(java.lang.Object)
     * @see javax.speech.synthesis.Synthesizer#cancelAll()
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_UPDATED
     * @see javax.speech.synthesis.SynthesizerEvent#QUEUE_EMPTIED
     */
    public static final int SPEAKABLE_CANCELLED = 606;

    /**
     * Issued when a synthesis engine starts the audio output of
     * a word in the speech output queue item.  The text,
     * wordStart and wordEnd parameters
     * defines the segment of the speakable's string which is now being spoken.
     *
     * @see javax.speech.synthesis.SpeakableListener#wordStarted(javax.speech.synthesis.SpeakableEvent)
     * @see javax.speech.synthesis.SpeakableEvent#getText()
     * @see javax.speech.synthesis.SpeakableEvent#getWordStart()
     * @see javax.speech.synthesis.SpeakableEvent#getWordEnd()
     */
    public static final int WORD_STARTED = 607;

    /**
     * Issued when audio output reaches a marker
     * contained in the JSML text of a speech output queue item.
     * The event text is the string of the MARK attribute.
     * The markerType indicates whether the mark is
     * at the opening or close of a JSML element or
     * is an attribute of an empty element (no close).
     *
     * @see javax.speech.synthesis.SpeakableListener#markerReached(javax.speech.synthesis.SpeakableEvent)
     * @see javax.speech.synthesis.SpeakableEvent#getText()
     * @see javax.speech.synthesis.SpeakableEvent#getMarkerType()
     * @see javax.speech.synthesis.SpeakableEvent#ELEMENT_OPEN
     * @see javax.speech.synthesis.SpeakableEvent#ELEMENT_CLOSE
     * @see javax.speech.synthesis.SpeakableEvent#ELEMENT_EMPTY
     */
    public static final int MARKER_REACHED = 608;

    /**
     * The type of MARKER_REACHED event issued at the opening
     * of a JSML container element with a MARK attribute.
     * An ELEMENT_OPEN event is followed by an ELEMENT_CLOSE
     * event for the closing of the element (unless the Speakable
     * is cancelled).
     * <p>
     * Example: the event for the MARK attribute on the
     * opening SENT tag will be issued before the start
     * of the word "Testing" in:
     * <p>
     * SENT MARK="open"
     * Testing one,
     * MARKER MARK="here"/
     * two, three.
     * /SENT
     *
     * @see javax.speech.synthesis.SpeakableEvent#MARKER_REACHED
     * @see javax.speech.synthesis.SpeakableEvent#getMarkerType()
     */
    public static final int ELEMENT_OPEN = 620;

    /**
     * The type of MARKER_REACHED event issued at the close
     * of a JSML container element that has a MARK attribute on
     * the matching opening tag.   The ELEMENT_CLOSE event
     * always follows a matching ELEMENT_OPEN event for the
     * matching opening tag.
     * <p>
     * Example: the event for the closing SENT tag for the
     * MARK attribute at the opening of the SENT
     * element.  The event will be issued after the word "three" is spoken.
     * <p>
     * SENT MARK="open"
     * Testing one,
     * MARKER MARK="here"/
     * two, three.
     * /SENT
     *
     * @see javax.speech.synthesis.SpeakableEvent#MARKER_REACHED
     * @see javax.speech.synthesis.SpeakableEvent#getMarkerType()
     */
    public static final int ELEMENT_CLOSE = 621;

    /**
     * The type of MARKER_REACHED event issued when an empty
     * JSML element with a MARK attribute is reached.
     * (An empty JSML has no closing tag and is
     * indicated by a slash ('/') before the '
     * ' character.)
     * <p>
     * Example: the MARKER tag below is empty event so
     * an ELEMENT_EMPTY type of MARKER_REACHED event
     * is issued before the word "two" is spoken in:
     * <p>
     * SENT MARK="open"
     * Testing one,
     * MARKER MARK="here"/
     * two, three.
     * /SENT
     *
     * @see javax.speech.synthesis.SpeakableEvent#MARKER_REACHED
     * @see javax.speech.synthesis.SpeakableEvent#getMarkerType()
     */
    public static final int ELEMENT_EMPTY = 622;

    /**
     * The text associated with the SpeakableEvent.
     */
    protected String text;

    /**
     * Marker type for a MARKER_REACHED event.
     *
     * @see javax.speech.synthesis.SpeakableEvent#getMarkerType()
     * @see javax.speech.synthesis.SpeakableEvent#ELEMENT_OPEN
     * @see javax.speech.synthesis.SpeakableEvent#ELEMENT_CLOSE
     * @see javax.speech.synthesis.SpeakableEvent#ELEMENT_EMPTY
     */
    protected int markerType;

    /**
     * Index of first character of of word in JSML text for a WORD_STARTED event.
     *
     * @see javax.speech.synthesis.SpeakableEvent#WORD_STARTED
     * @see javax.speech.synthesis.SpeakableEvent#getWordStart()
     */
    protected int wordStart;

    /**
     * Index of last character of of word in JSML text for a WORD_STARTED event.
     *
     * @see javax.speech.synthesis.SpeakableEvent#WORD_STARTED
     * @see javax.speech.synthesis.SpeakableEvent#getWordEnd()
     */
    protected int wordEnd;

    /**
     * Constructs an SpeakableEvent with a specified source and identifier.
     */
    public SpeakableEvent(Object source, int id) {
        super(source, id);
        this.text = null;
        this.markerType = -1;
        this.wordStart = -1;
        this.wordEnd = -1;
    }

    /**
     * Constructs an SpeakableEvent with a specified source, identifier,
     * text and marker type (used for a MARKER_REACHED event).
     */
    public SpeakableEvent(Object source, int id, String text, int markerType) {
        super(source, id);
        this.text = text;
        this.markerType = markerType;
        this.wordStart = -1;
        this.wordEnd = -1;
    }

    /**
     * Constructor for a specified source, identifier,
     * text, wordStart and wordEnd (called for a WORD_STARTED event).
     */
    public SpeakableEvent(Object source, int id, String text, int wordStart, int wordEnd) {
        super(source, id);
        this.text = text;
        this.markerType = -1;
        this.wordStart = wordStart;
        this.wordEnd = wordEnd;
    }

    /**
     * Return the type of a MARKER_REACHED event.
     *
     * @see javax.speech.synthesis.SpeakableEvent#MARKER_REACHED
     * @see javax.speech.synthesis.SpeakableEvent#ELEMENT_OPEN
     * @see javax.speech.synthesis.SpeakableEvent#ELEMENT_CLOSE
     * @see javax.speech.synthesis.SpeakableEvent#ELEMENT_EMPTY
     */
    public int getMarkerType() {
        return this.markerType;
    }

    /**
     * Get the text associated with the event.
     * <p>
     * For WORD_STARTED, the text is the next word to be spoken.
     * This text may differ from the text between the wordStart
     * and wordEnd points is the original JSML text.
     * <p>
     * For MARKER_REACHED, the text is the MARK
     * attribute in the JSML text.
     *
     * @see javax.speech.synthesis.SpeakableEvent#WORD_STARTED
     * @see javax.speech.synthesis.SpeakableEvent#MARKER_REACHED
     */
    public String getText() {
        return this.text;
    }

    /**
     * For a WORD_STARTED event, return the index
     * of the last character of the word in the JSML text.
     */
    public int getWordEnd() {
        return this.wordEnd;
    }

    /**
     * For a WORD_STARTED event, return the index
     * of the first character of the word in the JSML text.
     */
    public int getWordStart() {
        return this.wordStart;
    }

    /**
     * Returns a parameter string identifying this  event.
     * This method is useful for event-logging and for debugging.
     *
     * @return a string identifying the event
     */
    @Override
    public String paramString() {
        switch (super.id) {
        case 601:
            return "TOP_OF_QUEUE";
        case 602:
            return "SPEAKABLE_STARTED";
        case 603:
            return "SPEAKABLE_ENDED";
        case 604:
            return "SPEAKABLE_PAUSED";
        case 605:
            return "SPEAKABLE_RESUMED";
        case 606:
            return "SPEAKABLE_CANCELLED";
        case 607:
            return "WORD_STARTED \"" + this.text + "\" from: " + this.wordStart + " to: " + this.wordEnd;
        case 608:
            StringBuilder sb = new StringBuilder("MARKER_REACHED: ");
            sb.append("\"").append(this.text).append("\" at ");
            switch (this.markerType) {
            case 620:
                sb.append("ELEMENT_OPEN");
                break;
            case 621:
                sb.append("ELEMENT_CLOSE");
                break;
            case 622:
                sb.append("ELEMENT_EMPTY");
                break;
            default:
                sb.append("unknown type");
            }

            return sb.toString();
        default:
            return super.paramString();
        }
    }
}
