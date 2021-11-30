package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LocalDateTimeToSqlTimestampConverter implements Converter<LocalDateTime, Timestamp> {

    @Override
    public Class<LocalDateTime> getSourceType() {
        return LocalDateTime.class;
    }

    @Override
    public Class<Timestamp> getTargetType() {
        return Timestamp.class;
    }

    @Override
    public Timestamp apply(final LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
