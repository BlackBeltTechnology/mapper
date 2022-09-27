package hu.blackbelt.mapper.impl.string;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.time.OffsetTime;

public class OffsetTimeToStringConverter implements Converter<OffsetTime, String> {

    private Formatter<OffsetTime> formatter;

    public void setFormatter(final Formatter<OffsetTime> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<OffsetTime> getSourceType() {
        return OffsetTime.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String apply(final OffsetTime offsetTime) {
        return formatter.convertValueToString(offsetTime);
    }
}
