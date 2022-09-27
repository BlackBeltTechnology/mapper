package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SqlTimestampFormatter implements Formatter<Timestamp> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String convertValueToString(final Timestamp value) {
        return formatter.format(value.toLocalDateTime());
    }

    @Override
    public Timestamp parseString(final String str) {
        try {
            return Timestamp.valueOf(LocalDateTime.from(formatter.parse(str)));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as LocalDateTime", ex);
        }
    }

    @Override
    public Class<Timestamp> getType() {
        return Timestamp.class;
    }
}
