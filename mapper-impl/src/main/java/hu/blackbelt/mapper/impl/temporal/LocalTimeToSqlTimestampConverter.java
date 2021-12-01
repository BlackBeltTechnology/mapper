package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalTimeToSqlTimestampConverter implements Converter<LocalTime, Timestamp> {

    @Override
    public Class<LocalTime> getSourceType() {
        return LocalTime.class;
    }

    @Override
    public Class<Timestamp> getTargetType() {
        return Timestamp.class;
    }

    @Override
    public Timestamp apply(final LocalTime localTime) {
        return Timestamp.valueOf(LocalDateTime.of(1970, 1, 1,
                localTime.getHour(), localTime.getMinute(), localTime.getSecond(), localTime.getNano()));
    }
}
