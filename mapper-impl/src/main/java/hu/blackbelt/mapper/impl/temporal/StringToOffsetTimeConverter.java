package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.time.OffsetTime;
import java.time.ZoneOffset;

public class StringToOffsetTimeConverter implements Converter<String, OffsetTime> {

    private Formatter<OffsetTime> formatter;

    public void setFormatter(final Formatter<OffsetTime> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<OffsetTime> getTargetType() {
        return OffsetTime.class;
    }

    @Override
    public OffsetTime apply(final String s) {
        return formatter.parseString(s).withOffsetSameInstant(ZoneOffset.UTC);
    }
}
