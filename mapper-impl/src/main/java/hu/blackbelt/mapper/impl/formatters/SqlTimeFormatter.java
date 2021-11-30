package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SqlTimeFormatter implements Formatter<Time> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    @Override
    public String convertValueToString(final Time value) {
        return formatter.format(value.toLocalTime());

    }

    @Override
    public Time parseString(final String str) {
        try {
            return Time.valueOf(LocalTime.from(formatter.parse(str)));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as LocalTime", ex);
        }
    }

    @Override
    public Class<Time> getType() {
        return Time.class;
    }
}
