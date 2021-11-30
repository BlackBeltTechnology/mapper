package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.time.LocalTime;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    private Formatter<LocalTime> formatter;

    public void setFormatter(final Formatter<LocalTime> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<LocalTime> getTargetType() {
        return LocalTime.class;
    }

    @Override
    public LocalTime apply(String s) {
        return formatter.parseString(s);
    }
}
