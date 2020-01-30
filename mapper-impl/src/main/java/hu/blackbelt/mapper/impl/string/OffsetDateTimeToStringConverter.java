package hu.blackbelt.mapper.impl.string;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.time.OffsetDateTime;

public class OffsetDateTimeToStringConverter implements Converter<OffsetDateTime, String> {

    private Formatter<OffsetDateTime> formatter;

    public void setFormatter(final Formatter<OffsetDateTime> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<OffsetDateTime> getSourceType() {
        return OffsetDateTime.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String apply(final OffsetDateTime offsetDateTime) {
        return formatter.convertValueToString(offsetDateTime);
    }
}
