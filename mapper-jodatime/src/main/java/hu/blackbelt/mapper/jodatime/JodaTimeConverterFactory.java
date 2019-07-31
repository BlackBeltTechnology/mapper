package hu.blackbelt.mapper.jodatime;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.impl.DefaultConverterFactory;
import hu.blackbelt.mapper.jodatime.formatters.LocalDateFormatter;

import java.util.Arrays;
import java.util.Collection;

public class JodaTimeConverterFactory extends DefaultConverterFactory {

    private final LocalDateToStringConverter localDate2string = new LocalDateToStringConverter();
    private final StringToLocalDateConverter string2localDate = new StringToLocalDateConverter();

    private final LocalDateFormatter localDateFormatter = new LocalDateFormatter();

    private final Collection<Converter> converters = Arrays.asList(localDate2string, string2localDate);

    public JodaTimeConverterFactory() {
        super();
        localDate2string.setFormatter(localDateFormatter);
        string2localDate.setFormatter(localDateFormatter);

        converters.forEach(c -> registerConverter(c));
    }

    @Override
    public void destroy() {
        converters.forEach(c -> unregisterConverter(c));
        super.destroy();
    }
}
