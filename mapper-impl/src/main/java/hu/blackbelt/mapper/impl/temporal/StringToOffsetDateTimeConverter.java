package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class StringToOffsetDateTimeConverter implements Converter<String, OffsetDateTime> {

    private Formatter<OffsetDateTime> formatter;

    public void setFormatter(final Formatter<OffsetDateTime> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<OffsetDateTime> getTargetType() {
        return OffsetDateTime.class;
    }

    @Override
    public OffsetDateTime apply(final String s) {
        return formatter.parseString(s).atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime();
    }
}
