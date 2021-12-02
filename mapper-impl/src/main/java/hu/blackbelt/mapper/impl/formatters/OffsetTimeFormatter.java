package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetTimeFormatter implements Formatter<OffsetTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Override
    public String convertValueToString(final OffsetTime value) {
        return formatter.format(value);
    }

    @Override
    public OffsetTime parseString(final String str) {
        try {
            return OffsetTime.from(formatter.parse(str));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as OffsetTime", ex);
        }
    }

    @Override
    public Class<OffsetTime> getType() {
        return OffsetTime.class;
    }
}
