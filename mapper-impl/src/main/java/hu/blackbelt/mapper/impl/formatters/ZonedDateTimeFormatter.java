package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ZonedDateTimeFormatter implements Formatter<ZonedDateTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    @Override
    public String convertValueToString(final ZonedDateTime value) {
        return formatter.format(value);
    }

    @Override
    public ZonedDateTime parseString(final String str) {
        try {
            return ZonedDateTime.from(formatter.parse(str));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as ZonedDateTime", ex);
        }
    }

    @Override
    public Class<ZonedDateTime> getType() {
        return ZonedDateTime.class;
    }
}
