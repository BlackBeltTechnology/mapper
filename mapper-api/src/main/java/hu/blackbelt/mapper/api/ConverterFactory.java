package hu.blackbelt.mapper.api;

import java.util.Collection;

/**
 * Converter factory that provides converters.
 */
public interface ConverterFactory {

    /**
     * Get all converters that convert any values from a given type.
     *
     * @param sourceType source class
     * @param <S>        source type
     * @return converters
     */
    <S> Collection<Converter> getConvertersFrom(Class<S> sourceType);

    /**
     * Get all converters that convert any values to a given type.
     *
     * @param targetType target class
     * @param <T>        target type
     * @return converters
     */
    <T> Collection<Converter> getConvertersTo(Class<T> targetType);

    /**
     * Get all converters that convert any values to a given type.
     *
     * @param targetTypeName target class name
     * @param <T>            target type
     * @return converters
     */
    <T> Collection<Converter> getConvertersTo(String targetTypeName);

    /**
     * Get all converters that convert any values from a given type to a given type.
     *
     * @param sourceType source class
     * @param targetType target class
     * @param <S>        source type
     * @param <T>        target type
     * @return converters
     */
    <S, T> Collection<Converter> getConverters(Class<S> sourceType, Class<T> targetType);
}
