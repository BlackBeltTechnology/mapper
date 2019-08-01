package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.ConverterFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Default coercer implementation.
 *
 * Result is:
 * - <code>null</code> if source value is also <code>null</code>,
 * - the source object itself if target is matching to (specified by string) or assignable from (specified by class) source object class,
 * - produced by single converter that type of source object is matching to source type of the converter,
 * - produced by <i>any</i> converter that source object is assignable to source type of the converter,
 * - produced by two converters, the first converts source object to String and the second converts String to target type.
 *
 * Coercers use autoboxing converters for primitive targets and an error log is produced if target type is primitive
 * but converted value is null.
 */
@lombok.Getter
@lombok.Setter
@Slf4j
public class DefaultCoercer extends AbstractCoercer {

    private final ConverterFactory converterFactory = new DefaultConverterFactory();

    private final static Map<Class, Class> PRIMITIVES = new HashMap<>();

    {
        PRIMITIVES.put(byte.class, Byte.class);
        PRIMITIVES.put(short.class, Short.class);
        PRIMITIVES.put(int.class, Integer.class);
        PRIMITIVES.put(long.class, Long.class);
        PRIMITIVES.put(float.class, Float.class);
        PRIMITIVES.put(double.class, Double.class);
        PRIMITIVES.put(char.class, Character.class);
        PRIMITIVES.put(boolean.class, Boolean.class);
        PRIMITIVES.put(void.class, Void.class);
    }

    @Override
    public <S, T> T coerce(final S sourceValue, final Class<T> targetClass) {
        Objects.requireNonNull(targetClass, "Target class must bet set");

        final Class<T> resolvedTargetClass = targetClass.isPrimitive() ? getAutoBoxingClass(targetClass) : targetClass;

        final T result;
        if (sourceValue == null || resolvedTargetClass == Void.class) {
            result = null;
        } else if (resolvedTargetClass.isAssignableFrom(sourceValue.getClass())) {
            result = (T) sourceValue;
        } else {
            final Class<S> sourceClass = (Class<S>) sourceValue.getClass();
            final Collection<Converter<? extends S, T>> converters = getConverterFactory().getConvertersTo(resolvedTargetClass).stream()
                    .filter(c -> c.getSourceType().isAssignableFrom(sourceValue.getClass()))
                    .map(c -> (Converter<? extends S, T>) c)
                    .collect(Collectors.toList());
            final Optional<Converter<S, T>> matchingTypeConverter = converters.stream().filter(c -> c.getSourceType() == sourceClass).map(c -> (Converter<S, T>) c).findAny();

            if (matchingTypeConverter.isPresent()) {
                result = matchingTypeConverter.get().apply(sourceValue);
            } else if (!converters.isEmpty()) {
                final Converter<S, T> converter = (Converter<S, T>) converters.iterator().next();
                result = converter.apply(sourceValue);
            } else if (String.class == resolvedTargetClass) {
                result = (T) sourceValue.toString();
//            } else if (external_framework_found_supporting_direct_mapping) {
//                // TODO: try to use external framework (ie. Jackson) to continue
            } else {
                // try to convert value to String and the result to the expected type
                final Optional<String> str = converterFactory.getConverters(sourceClass, String.class).stream().findAny()
                        .map(c -> (String)c.apply(sourceValue));
                if (str.isPresent()) {
                    final Optional<T> converted = converterFactory.getConverters(String.class, targetClass).stream().findAny()
                            .map(c -> (T)c.apply(str));
                    if (converted.isPresent()) {
                        result = converted.get();
                    } else {
                        // no String -> target type converter found
                        // TODO: try to use external framework (ie. Jackson) to continue

                        throw new UnsupportedOperationException("Not supported yet");
                    }
                } else {
                    // no source -> String converter found
                    // TODO: try to use external framework (ie. Jackson) to continue

                    throw new UnsupportedOperationException("Not supported yet");
                }
            }
        }

        if (targetClass.isPrimitive() && !targetClass.equals(Void.class) && result == null) {
            log.error("Non-null ({}) target value expected", targetClass.getName());
        }

        if (log.isTraceEnabled()) {
            log.trace("Converted <{}> [{}] to <{}> [{}]", new Object[]{sourceValue, sourceValue != null ? sourceValue.getClass().getName() : "-", result, result != null ? result.getClass().getName() : "-"});
        }

        return result;
    }

    @Override
    public <S, T> T coerce(final S sourceValue, final String targetClassName) {
        Objects.requireNonNull(targetClassName, "Target class must bet set");

        final String resolvedTargetClassName = PRIMITIVES.entrySet().stream()
                .filter(e -> e.getKey().getName().equals(targetClassName))
                .map(e -> e.getValue().getName())
                .findAny().orElse(targetClassName);

        final T result;
        if (sourceValue == null || "Void".equals(resolvedTargetClassName)) {
            result = null;
        } else if (resolvedTargetClassName.equals(sourceValue.getClass().getName())) {
            result = (T) sourceValue;
        } else {
            final Class<S> sourceClass = (Class<S>) sourceValue.getClass();
            final Collection<Converter<? extends S, T>> converters = getConverterFactory().getConvertersTo(resolvedTargetClassName).stream()
                    .filter(c -> c.getSourceType().isAssignableFrom(sourceValue.getClass()))
                    .map(c -> (Converter<? extends S, T>) c)
                    .collect(Collectors.toList());
            final Optional<Converter<S, T>> matchingTypeConverter = converters.stream().filter(c -> c.getSourceType() == sourceClass).map(c -> (Converter<S, T>) c).findAny();

            if (matchingTypeConverter.isPresent()) {
                result = matchingTypeConverter.get().apply(sourceValue);
            } else if (!converters.isEmpty()) {
                final Converter<S, T> converter = (Converter<S, T>) converters.iterator().next();
                result = converter.apply(sourceValue);
            } else if (resolvedTargetClassName.equals(String.class.getName())) {
                result = (T) sourceValue.toString();
//            } else if (external_framework_found_supporting_direct_mapping) {
//                // TODO: try to use external framework (ie. Jackson) to continue
            } else {
                // try to convert value to String and the result to the expected type
                final Optional<String> str = converterFactory.getConverters(sourceClass, String.class).stream().findAny()
                        .map(c -> (String)c.apply(sourceValue));
                if (str.isPresent()) {
                    final Optional<T> converted = converterFactory.getConvertersFrom(String.class).stream()
                            .filter(c -> resolvedTargetClassName.equals(c.getTargetType().getName())).findAny()
                            .map(c -> (T)c.apply(str));
                    if (converted.isPresent()) {
                        result = converted.get();
                    } else {
                        // no String -> target type converter found
                        // TODO: try to use external framework (ie. Jackson) to continue

                        throw new UnsupportedOperationException("Not supported yet");
                    }
                } else {
                    // no source -> String converter found
                    // TODO: try to use external framework (ie. Jackson) to continue

                    throw new UnsupportedOperationException("Not supported yet");
                }
            }
        }

        if (!resolvedTargetClassName.equals(targetClassName) && !targetClassName.equals(Void.class.getName()) && result == null) {
            log.error("Non-null ({}) target value expected", targetClassName);
        }

        if (log.isTraceEnabled()) {
            log.trace("Converted <{}> [{}] to <{}> [{}]", new Object[]{sourceValue, sourceValue != null ? sourceValue.getClass().getName() : "-", result, result != null ? result.getClass().getName() : "-"});
        }

        return result;
    }

    private static <T> Class getAutoBoxingClass(final Class<T> primitiveClass) {
        final Class c = PRIMITIVES.get(primitiveClass);
        if (c == null) {
            throw new UnsupportedOperationException("Unsupported primitive type: {}" + primitiveClass.getName());
        } else {
            return c;
        }
    }
}
