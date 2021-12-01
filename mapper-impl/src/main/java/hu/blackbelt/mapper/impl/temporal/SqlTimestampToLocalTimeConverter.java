package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class SqlTimestampToLocalTimeConverter implements Converter<Timestamp, LocalTime> {

    @Override
    public Class<Timestamp> getSourceType() {
        return Timestamp.class;
    }

    @Override
    public Class<LocalTime> getTargetType() {
        return LocalTime.class;
    }

    @Override
    public LocalTime apply(final Timestamp time) {
        return time.toInstant().atOffset(ZoneOffset.UTC).toLocalTime();
    }
}
