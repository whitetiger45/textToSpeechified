package javax.speech.synthesis;

/**
 * An object implementing the Speakable interface can
 * be provided to the speak method of a
 * Synthesizer to be spoken.
 * The text is accessed through the getJSMLText method
 * making it the spoken equivalent of the toString method
 * of a Java object.
 * <p>
 * Applications can extend (nearly) any Java object to implement
 * the Speakable interface (strictly speaking, any non-final object).
 * Examples might include graphical objects or database entries.
 * <p>
 * The getJSMLText method returns text formatted
 * for the Java Speech Markup Language -- defined in the
 * <p>
 * <A href="http://java.sun.com/products/java-media/speech/forDevelopers/JSML/index.html">
 * Java Speech Markup Language specification</A>
 * .
 * JSML allows structural information (paragraphs and sentences),
 * production information (pronunciations, emphasis, breaks,
 * and prosody), and other miscellaneous markup.  Appropriate
 * use of this markup improves the quality and understandability
 * of the synthesized speech.
 * <p>
 * The JSML text is a Unicode string and is assumed to contain
 * text of a single language (the language of the Synthesizer).
 * The text is treated as independent of other text output
 * on the synthesizer's text output queue, so, a sentence or
 * other important structure should be contained within a
 * single speakable object.
 * <p>
 * The standard XML header is optional for software-created
 * JSML documents.  Thus, the getJSMLText method
 * is not required to provide the header.
 * <p>
 * A SpeakableListener can be attached to the
 * Synthesizer with the addSpeakableListener
 * method to receive all SpeakableEvents for
 * all Speakable objects on the output queue.
 *
 * @see javax.speech.synthesis.SpeakableListener
 * @see javax.speech.synthesis.SpeakableEvent
 * @see javax.speech.synthesis.Synthesizer
 * @see javax.speech.synthesis.Synthesizer#speak(Speakable, SpeakableListener)
 * @see javax.speech.synthesis.Synthesizer#addSpeakableListener(javax.speech.synthesis.SpeakableListener)
 */
public interface Speakable {

    /**
     * Return text to be spoken formatted for the Java Speech Markup Language.
     * This method is called immediately when a Speakable
     * object is passed to the speak method of a
     * Synthesizer.  The text placed on the speaking queue
     * can be inspected through the SynthesizerQueueItem
     * on the speech output queue available through the synthesizer's
     * enumerateQueue method.
     *
     * @return a string containing Java Speech Markup Language text
     * @see javax.speech.synthesis.Synthesizer#speak(javax.speech.synthesis.Speakable, SpeakableListener)
     * @see javax.speech.synthesis.Synthesizer#enumerateQueue()
     * @see javax.speech.synthesis.SynthesizerQueueItem
     */
    String getJSMLText();
}
