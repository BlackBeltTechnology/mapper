package hu.blackbelt.mapper.api;

public class ConverterException extends RuntimeException {

    public ConverterException(final String message) {
        super(message);
    }

    public ConverterException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
