package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class OffsetDateTimeToLocalDateTimeConverter implements Converter<OffsetDateTime, LocalDateTime> {

    @Override
    public Class<OffsetDateTime> getSourceType() {
        return OffsetDateTime.class;
    }

    @Override
    public Class<LocalDateTime> getTargetType() {
        return LocalDateTime.class;
    }

    @Override
    public LocalDateTime apply(final OffsetDateTime offsetDateTime) {
        return offsetDateTime.toLocalDateTime();
    }
}