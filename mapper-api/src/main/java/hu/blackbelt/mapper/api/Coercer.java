package hu.blackbelt.mapper.api;

/**
 * Makes use of Coercions to convert between an input value (of some specific type) and a desired output type.
 */
public interface Coercer {

    /**
     * Performs a coercion from an input type to a desired output type.
     *
     * @param sourceValue source value
     * @param targetClass target class
     * @param <S> source type
     * @param <T> target type
     * @return target value
     */
    <S, T> T coerce(S sourceValue, Class<T> targetClass);
}
