package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeFormatter implements Formatter<Time> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    @Override
    public String convertValueToString(final Time value) {
        return formatter.format(LocalTime.ofNanoOfDay(value.getTime() * 1000 * 1000));
    }

    @Override
    public Time parseString(final String str) {
        try {
            return new Time(LocalTime.from(formatter.parse(str)).toNanoOfDay() / (1000 * 1000));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as LocalTime", ex);
        }
    }

    @Override
    public Class<Time> getType() {
        return Time.class;
    }
}
