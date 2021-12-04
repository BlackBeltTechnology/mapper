package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalTime;

@Slf4j
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
    public LocalTime apply(final Timestamp timestamp) {
        return timestamp.toLocalDateTime().toLocalTime();
    }
}
