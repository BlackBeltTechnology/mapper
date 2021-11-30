package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SqlTimestampToLocalDateTimeConverter implements Converter<Timestamp, LocalDateTime> {

    @Override
    public Class<Timestamp> getSourceType() {
        return Timestamp.class;
    }

    @Override
    public Class<LocalDateTime> getTargetType() {
        return LocalDateTime.class;
    }

    @Override
    public LocalDateTime apply(final Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
}
