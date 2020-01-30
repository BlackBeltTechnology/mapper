package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetDateTimeFormatter implements Formatter<OffsetDateTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Override
    public String convertValueToString(final OffsetDateTime value) {
        return formatter.format(value);
    }

    @Override
    public OffsetDateTime parseString(final String str) {
        try {
            return OffsetDateTime.from(formatter.parse(str));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as OffsetDateTime", ex);
        }
    }

    @Override
    public Class<OffsetDateTime> getType() {
        return OffsetDateTime.class;
    }
}
