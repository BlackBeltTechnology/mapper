package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateFormatter implements Formatter<LocalDate> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public String convertValueToString(final LocalDate value) {
        return formatter.format(value);
    }

    @Override
    public LocalDate parseString(final String str) {
        try {
            return LocalDate.from(formatter.parse(str));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as LocalDate", ex);
        }
    }

    @Override
    public Class<LocalDate> getType() {
        return LocalDate.class;
    }
}
