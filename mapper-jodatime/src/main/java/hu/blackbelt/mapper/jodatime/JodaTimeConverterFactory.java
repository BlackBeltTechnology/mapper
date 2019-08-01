package hu.blackbelt.mapper.jodatime;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.impl.DefaultConverterFactory;
import hu.blackbelt.mapper.jodatime.formatters.LocalDateFormatter;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JodaTimeConverterFactory extends DefaultConverterFactory {

    private final static LocalDateToStringConverter LOCAL_DATE_TO_STRING = new LocalDateToStringConverter();
    private final static StringToLocalDateConverter STRING_TO_LOCAL_DATE = new StringToLocalDateConverter();

    private final static LocalDateFormatter LOCAL_DATE_FORMATTER = new LocalDateFormatter();

    private final static Collection<Converter> CONVERTERS = Arrays.asList(LOCAL_DATE_TO_STRING, STRING_TO_LOCAL_DATE);

    public JodaTimeConverterFactory() {
        super();

        LOCAL_DATE_TO_STRING.setFormatter(LOCAL_DATE_FORMATTER);
        STRING_TO_LOCAL_DATE.setFormatter(LOCAL_DATE_FORMATTER);
    }

    @Override
    public void destroy() {
        reset();
    }

    @Override
    protected Collection<Converter> getDefaultConverters() {
        return Stream.concat(super.getDefaultConverters().stream(), CONVERTERS.stream())
                .collect(Collectors.toList());
    }
}
