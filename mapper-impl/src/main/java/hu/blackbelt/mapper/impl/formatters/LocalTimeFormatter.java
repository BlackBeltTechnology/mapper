package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    @Override
    public String convertValueToString(final LocalTime value) {
        return formatter.format(value);
    }

    @Override
    public LocalTime parseString(final String str) {
        try {
            return LocalTime.from(formatter.parse(str));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as LocalTime", ex);
        }
    }

    @Override
    public Class<LocalTime> getType() {
        return LocalTime.class;
    }
}
