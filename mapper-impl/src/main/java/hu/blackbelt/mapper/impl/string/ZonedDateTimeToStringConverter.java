package hu.blackbelt.mapper.impl.string;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.time.ZonedDateTime;

public class ZonedDateTimeToStringConverter implements Converter<ZonedDateTime, String> {

    private Formatter<ZonedDateTime> formatter;

    public void setFormatter(final Formatter<ZonedDateTime> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<ZonedDateTime> getSourceType() {
        return ZonedDateTime.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String apply(final ZonedDateTime zonedDateTime) {
        return formatter.convertValueToString(zonedDateTime);
    }
}
