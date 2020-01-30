package hu.blackbelt.mapper.api;

/**
 * Exception thrown on unsupported/invalid conversions.
 */
public class ConverterException extends RuntimeException {

    /**
     * Create a new exception instance.
     *
     * @param message message
     */
    public ConverterException(final String message) {
        super(message);
    }

    /**
     * Create a new exception instance.
     *
     * @param message message
     * @param cause   cause
     */
    public ConverterException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
