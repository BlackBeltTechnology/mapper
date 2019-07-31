package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.ConverterFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@lombok.Getter
@lombok.Setter
@Slf4j
public class DefaultCoercer extends AbstractCoercer {

    private final ConverterFactory converterFactory = new DefaultConverterFactory();

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
            } else {
                // TODO: check if source -> string -> target converters exists
                // TODO: use external framework (ie. Jackson) to convert to to string and string to target type
                throw new UnsupportedOperationException("Not supported yet");
            }
        }

        if (targetClass.isPrimitive() && !targetClass.equals(Void.class) && result == null) {
            log.error("Non-null ({}) target value expected", targetClass.getName());
        }

        if (log.isTraceEnabled()) {
            log.trace("Converted <{}> [{}] to <{}> [{}]", new Object[] {sourceValue, sourceValue != null ? sourceValue.getClass().getName() : "-", result, result != null ? result.getClass().getName() : "-"});
        }

        return result;
    }

    @Override
    public <S, T> T coerce(final S sourceValue, final String targetClassName) {
        Objects.requireNonNull(targetClassName, "Target class must bet set");

        final String resolvedTargetClassName;
        switch (targetClassName) {
            case "byte": resolvedTargetClassName = Byte.class.getName(); break;
            case "short": resolvedTargetClassName = Short.class.getName(); break;
            case "int": resolvedTargetClassName = Integer.class.getName(); break;
            case "long": resolvedTargetClassName = Long.class.getName(); break;
            case "float": resolvedTargetClassName = Float.class.getName(); break;
            case "double": resolvedTargetClassName = Double.class.getName(); break;
            case "char": resolvedTargetClassName = Character.class.getName(); break;
            case "boolean": resolvedTargetClassName = Boolean.class.getName(); break;
            case "void": resolvedTargetClassName = Void.class.getName(); break;
            default:
                resolvedTargetClassName = targetClassName;
        }

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
            } else if (resolvedTargetClassName.equals("java.lang.String")) {
                result = (T) sourceValue.toString();
            } else {
                // TODO: check if source -> string -> target converters exists
                // TODO: use external framework (ie. Jackson) to convert to to string and string to target type
                throw new UnsupportedOperationException("Not supported yet");
            }
        }

        if (!resolvedTargetClassName.equals(targetClassName) && !targetClassName.equals("java.lang.Void") && result == null) {
            log.error("Non-null ({}) target value expected", targetClassName);
        }

        if (log.isTraceEnabled()) {
            log.trace("Converted <{}> [{}] to <{}> [{}]", new Object[] {sourceValue, sourceValue != null ? sourceValue.getClass().getName() : "-", result, result != null ? result.getClass().getName() : "-"});
        }

        return result;
    }

    private static <T> Class getAutoBoxingClass(final Class<T> primitiveClass) {
        if (primitiveClass == byte.class) {
            return Byte.class;
        } else if (primitiveClass == short.class) {
            return Short.class;
        } else if (primitiveClass == int.class) {
            return Integer.class;
        } else if (primitiveClass == long.class) {
            return Long.class;
        } else if (primitiveClass == float.class) {
            return Float.class;
        } else if (primitiveClass == double.class) {
            return Double.class;
        } else if (primitiveClass == char.class) {
            return Character.class;
        } else if (primitiveClass == boolean.class) {
            return Boolean.class;
        } else if (primitiveClass == void.class) {
            return Void.class;
        } else {
            throw new UnsupportedOperationException("Unsupported primitive type");
        }
    }
}
