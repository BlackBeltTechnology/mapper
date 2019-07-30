package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String convertValueToString(final LocalDateTime value) {
        return formatter.format(value);
    }

    @Override
    public LocalDateTime parseString(String str) {
        try {
            return LocalDateTime.from(formatter.parse(str));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as LocalDateTime", ex);
        }
    }

    @Override
    public Class<LocalDateTime> getType() {
        return LocalDateTime.class;
    }
}
