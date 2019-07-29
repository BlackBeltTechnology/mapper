package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.ConverterFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractConverterFactory implements ConverterFactory {

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
    public <S, T> Collection<Converter> getConverters(final Class<S> sourceType, final Class<T> targetType) {
        final Key key = new Key(sourceType, targetType);

        return converters.containsKey(key) ? converters.get(key) : Collections.emptyList();
    }

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

    public <S, T> void unregisterConverter(final Converter<S, T> converter) {
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

    @lombok.Data
    @RequiredArgsConstructor
    private static class Key {

        @NonNull
        private Class sourceType;

        @NonNull
        private Class targetType;
    }
}
