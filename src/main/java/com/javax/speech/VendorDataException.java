package javax.speech;

/**
 * Signals that a problem has been encountered loading or saving
 * some type of vendor-specific data.
 */
public class VendorDataException extends SpeechException {

    /**
     * Construct a VendorDataException with no detail message.
     */
    public VendorDataException() {
    }

    /**
     * Construct a VendorDataException with the specified detail message.
     * A detail message is a String that describes this particular exception.
     *
     * @param s the detail message
     */
    public VendorDataException(String s) {
        super(s);
    }
}
