package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeToLocalTimeConverter implements Converter<LocalDateTime, LocalTime> {

    @Override
    public Class<LocalDateTime> getSourceType() {
        return LocalDateTime.class;
    }

    @Override
    public Class<LocalTime> getTargetType() {
        return LocalTime.class;
    }

    @Override
    public LocalTime apply(final LocalDateTime localDateTime) {
        return localDateTime.toLocalTime();
    }
}
