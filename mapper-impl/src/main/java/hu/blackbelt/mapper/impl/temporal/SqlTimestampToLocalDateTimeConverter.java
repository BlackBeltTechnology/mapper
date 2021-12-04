package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
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
