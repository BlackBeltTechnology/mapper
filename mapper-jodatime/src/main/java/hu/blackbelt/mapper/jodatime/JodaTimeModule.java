package hu.blackbelt.mapper.jodatime;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.ConverterFactory;
import hu.blackbelt.mapper.impl.AbstractCoercer;
import hu.blackbelt.mapper.impl.DefaultCoercer;
import hu.blackbelt.mapper.impl.DefaultConverterFactory;
import hu.blackbelt.mapper.jodatime.formatters.LocalDateFormatter;

import java.util.Arrays;
import java.util.Collection;

public class JodaTimeModule {

    private final static LocalDateToStringConverter LOCAL_DATE_TO_STRING = new LocalDateToStringConverter();
    private final static StringToLocalDateConverter STRING_TO_LOCAL_DATE = new StringToLocalDateConverter();

    private final static LocalDateFormatter LOCAL_DATE_FORMATTER = new LocalDateFormatter();

    private final static Collection<Converter> CONVERTERS = Arrays.asList(LOCAL_DATE_TO_STRING, STRING_TO_LOCAL_DATE);

    private ConverterFactory converterFactory;

    public JodaTimeModule() {
        this(new DefaultConverterFactory());
    }

    public JodaTimeModule(final ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;

        LOCAL_DATE_TO_STRING.setFormatter(LOCAL_DATE_FORMATTER);
        STRING_TO_LOCAL_DATE.setFormatter(LOCAL_DATE_FORMATTER);CONVERTERS.forEach(c -> converterFactory.registerConverter(c));
    }

    public static AbstractCoercer decorateWithJodaTime(final AbstractCoercer coercer) {
        final JodaTimeModule module = new JodaTimeModule(coercer.getConverterFactory());
        return new DefaultCoercer() {

            @Override
            public ConverterFactory getConverterFactory() {
                return module.converterFactory;
            }
        };
    }
}
