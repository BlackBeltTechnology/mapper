package hu.blackbelt.mapper.api;

/**
 * Formatter of a given (structured primitive) type that can parse strings and convert values to string.
 *
 * @param <T> type
 */
public interface Formatter<T> {

    /**
     * Convert a given value to string.
     *
     * @param value value
     * @return string value
     */
    String convertValueToString(T value);

    /**
     * Parse string and return value in a given type.
     *
     * @param str string value
     * @return parsed value
     */
    T parseString(final String str);

    /**
     * Get type of formatter.
     *
     * @return formatter class
     */
    Class<T> getType();
}
