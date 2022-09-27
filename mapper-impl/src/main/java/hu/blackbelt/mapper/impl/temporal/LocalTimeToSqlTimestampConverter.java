package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.sql.Timestamp;
import java.time.*;

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
        return Timestamp.valueOf(LocalDateTime.ofEpochSecond(localTime.toEpochSecond(LocalDate.EPOCH, ZoneOffset.UTC),
                localTime.getNano(), ZoneOffset.UTC));
    }
}
