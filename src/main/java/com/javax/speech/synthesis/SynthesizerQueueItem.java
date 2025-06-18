package javax.speech.synthesis;

/**
 * Represents an object on the speech output queue of a Synthesizer.
 * The item is described by the source object, the speakable text,
 * a boolean value indicating whether it is a plain text object, and
 * the SpeakableListener for the object.
 * <p>
 * The source object is the object provided to a speak
 * method (a Speakable objects, a URL, or
 * a String).  The text is the Java Speech
 * Markup Language string or plain text obtained from the source object.
 * The listener is the SpeakableListener object passed
 * to the speak method, or null.
 * <p>
 * The enumerateQueue method of a Synthesizer
 * provides a snapshot of the speech output queue.  It returns an
 * enumeration object that is null if the queue is empty
 * or contains a set of SynthesizerQueueItems.  The
 * Synthesizer's queue cannot be manipulated through this
 * enumeration object.
 *
 * @see javax.speech.synthesis.Synthesizer
 * @see javax.speech.synthesis.Synthesizer#enumerateQueue()
 */
public class SynthesizerQueueItem {

    /**
     * The source object for an item on the speech output queue.
     *
     * @see javax.speech.synthesis.SynthesizerQueueItem#getSource()
     */
    protected Object source;

    /**
     * The speakable text for an item on the speech output queue.
     *
     * @see javax.speech.synthesis.SynthesizerQueueItem#getText()
     */
    protected String text;

    /**
     * True if the text object is plain text (not Java Speech Markup Language).
     *
     * @see javax.speech.synthesis.SynthesizerQueueItem#isPlainText()
     */
    protected boolean plainText;

    /**
     * The listener for this object passed to the speak method
     * or null.
     *
     * @see javax.speech.synthesis.SynthesizerQueueItem#getSpeakableListener()
     */
    protected SpeakableListener listener;

    /**
     * Construct a SynthesizerQueueItem with the
     * source object and speakable text.
     */
    public SynthesizerQueueItem(Object source, String text, boolean plainText, SpeakableListener listener) {
        this.source = source;
        this.text = text;
        this.plainText = plainText;
        this.listener = listener;
    }

    /**
     * Return the source object for an item on the speech output
     * queue of a Synthesizer.  The source is one
     * of the three object types passed to the speak
     * or speakPlainText methods of Synthesizer:
     * a Speakable objects, a URL, or
     * a String.
     */
    public Object getSource() {
        return this.source;
    }

    /**
     * Return the SpeakableListener object for this
     * speech output queue item, or null if none
     * was provided to the speak method.
     */
    public SpeakableListener getSpeakableListener() {
        return this.listener;
    }

    /**
     * Return the speakable text for an item on the speech output
     * queue of a Synthesizer.  The text is either a
     * Java Speech Markup Language string or a plain text string that
     * was obtained from source object.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Return true if the item contains plain text
     * (not Java Speech Markup Language text).
     */
    public boolean isPlainText() {
        return this.plainText;
    }
}
