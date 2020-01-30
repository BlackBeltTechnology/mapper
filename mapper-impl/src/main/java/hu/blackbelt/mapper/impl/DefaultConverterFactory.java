package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.ConverterFactory;
import hu.blackbelt.mapper.api.ConverterRegistry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Default converter factory that provides list of converters from and/or to given types. Runtime changes are
 * supported by {@link ConverterRegistry} interface.
 */
@Slf4j
public class DefaultConverterFactory implements ConverterFactory, ConverterRegistry {

    private final Map<Key, Collection<Converter>> converters = new ConcurrentHashMap<>();

    @Override
    public <S> Collection<Converter> getConvertersFrom(final Class<S> sourceType) {
        return converters.entrySet().stream()
                .filter(e -> e.getKey().getSourceType() == sourceType)
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
    }

    @Override
    public <T> Collection<Converter> getConvertersTo(final Class<T> targetType) {
        return converters.entrySet().stream()
                .filter(e -> e.getKey().getTargetType() == targetType)
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
    }

    @Override
    public <T> Collection<Converter> getConvertersTo(final String targetTypeName) {
        return converters.entrySet().stream()
                .filter(e -> Objects.equals(e.getKey().getTargetType().getName(), targetTypeName))
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
    }

    @Override
    public <S, T> Collection<Converter> getConverters(final Class<S> sourceType, final Class<T> targetType) {
        final Key key = new Key(sourceType, targetType);

        return converters.containsKey(key) ? converters.get(key) : Collections.emptyList();
    }

    @Override
    public <S, T> void registerConverter(final Converter<S, T> converter) {
        final Key key = new Key(converter.getSourceType(), converter.getTargetType());

        synchronized (this) {
            if (converters.containsKey(key)) {
                converters.get(key).add(converter);
            } else {
                final Set set = ConcurrentHashMap.newKeySet();
                set.add(converter);
                converters.put(key, set);
            }
        }
    }

    @Override
    public <S, T> void unregisterConverter(final Converter<S, T> converter) {
        final Key key = new Key(converter.getSourceType(), converter.getTargetType());

        synchronized (this) {
            if (converters.containsKey(key)) {
                converters.get(key).remove(converter);
            }
        }
    }

    /**
     * Key used to find converters.
     */
    @lombok.Data
    @RequiredArgsConstructor
    private static class Key {

        @NonNull
        private Class sourceType;

        @NonNull
        private Class targetType;
    }
}
