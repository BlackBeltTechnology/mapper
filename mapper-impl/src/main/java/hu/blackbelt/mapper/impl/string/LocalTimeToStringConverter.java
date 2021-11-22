package hu.blackbelt.mapper.impl.string;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.time.LocalTime;

public class LocalTimeToStringConverter implements Converter<LocalTime, String> {

    private Formatter<LocalTime> formatter;

    public void setFormatter(final Formatter<LocalTime> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<LocalTime> getSourceType() {
        return LocalTime.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String apply(final LocalTime LocalTime) {
        return formatter.convertValueToString(LocalTime);
    }
}
