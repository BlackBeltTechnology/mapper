package hu.blackbelt.mapper.api;

/**
 * Converter registry that converters can be registered in / unregistered from.
 */
public interface ConverterRegistry {

    /**
     * Register a converter.
     *
     * @param converter converter
     * @param <S>       source type
     * @param <T>       target type
     */
    <S, T> void registerConverter(Converter<S, T> converter);

    /**
     * Unregister a converter.
     *
     * @param converter converter
     * @param <S>       source type
     * @param <T>       target type
     */
    <S, T> void unregisterConverter(Converter<S, T> converter);
}
