package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.ConverterFactory;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@lombok.Getter
@lombok.Setter
public class DefaultCoercer extends AbstractCoercer {

    private final ConverterFactory converterFactory = new DefaultConverterFactory();

    @Override
    public <S, T> T coerce(final S sourceValue, final Class<T> targetClass) {
        if (sourceValue == null) {
            return null;
        } else if (targetClass.isAssignableFrom(sourceValue.getClass())) {
            return (T) sourceValue;
        } else {
            final Class<S> sourceClass = (Class<S>) sourceValue.getClass();
            final Collection<Converter<? extends S, T>> converters = getConverterFactory().getConvertersTo(targetClass).stream()
                    .filter(c -> c.getSourceType().isAssignableFrom(sourceValue.getClass()))
                    .map(c -> (Converter<? extends S, T>) c)
                    .collect(Collectors.toList());
            final Optional<Converter<S, T>> matchingTypeConverter = converters.stream().filter(c -> c.getSourceType() == sourceClass).map(c -> (Converter<S, T>) c).findAny();

            if (matchingTypeConverter.isPresent()) {
                return matchingTypeConverter.get().apply(sourceValue);
            } else if (!converters.isEmpty()) {
                final Converter<S, T> converter = (Converter<S, T>) converters.iterator().next();
                return converter.apply(sourceValue);
            } else if (String.class == targetClass) {
                return (T) sourceValue.toString();
            } else {
                // TODO: check if source -> string -> target converters exists
                // TODO: use external framework (ie. Jackson) to convert to to string and string to target type
                throw new UnsupportedOperationException("Not supported yet");
            }
        }
    }
}
