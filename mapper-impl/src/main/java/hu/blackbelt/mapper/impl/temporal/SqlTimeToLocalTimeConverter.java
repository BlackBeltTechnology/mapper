package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.sql.Time;
import java.time.*;

public class SqlTimeToLocalTimeConverter implements Converter<Time, LocalTime> {

    @Override
    public Class<Time> getSourceType() {
        return Time.class;
    }

    @Override
    public Class<LocalTime> getTargetType() {
        return LocalTime.class;
    }

    @Override
    public LocalTime apply(final Time time) {
        return Instant.ofEpochMilli(time.getTime()).atOffset(ZoneOffset.UTC).toLocalTime();
    }
}
