package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.time.ZonedDateTime;

public class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {

    private Formatter<ZonedDateTime> formatter;

    public void setFormatter(final Formatter<ZonedDateTime> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<ZonedDateTime> getTargetType() {
        return ZonedDateTime.class;
    }

    @Override
    public ZonedDateTime apply(final String s) {
        return formatter.parseString(s);
    }
}
