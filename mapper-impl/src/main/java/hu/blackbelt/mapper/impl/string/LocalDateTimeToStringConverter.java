package hu.blackbelt.mapper.impl.string;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.time.LocalDateTime;

public class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {

    private Formatter<LocalDateTime> formatter;

    public void setFormatter(final Formatter<LocalDateTime> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<LocalDateTime> getSourceType() {
        return LocalDateTime.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String apply(final LocalDateTime localDateTime) {
        return formatter.convertValueToString(localDateTime);
    }
}
