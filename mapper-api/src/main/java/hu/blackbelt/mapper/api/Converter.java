package hu.blackbelt.mapper.api;

import java.util.function.Function;

/**
 * Converter from a given type to another.
 *
 * @param <S> source type
 * @param <T> target type
 */
public interface Converter<S, T> extends Function<S, T> {

    /**
     * Get source type.
     *
     * @return source type
     */
    Class<S> getSourceType();

    /**
     * Get target type.
     *
     * @return target type
     */
    Class<T> getTargetType();

    /**
     * Convert value s to target type.
     *
     * @param s value to convert
     * @return converted value
     */
    @Override
    T apply(S s);
}
