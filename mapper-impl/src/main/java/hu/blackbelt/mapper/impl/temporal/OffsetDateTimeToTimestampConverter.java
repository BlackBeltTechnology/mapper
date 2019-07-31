package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

public class OffsetDateTimeToTimestampConverter implements Converter<OffsetDateTime, Timestamp> {

    @Override
    public Class<OffsetDateTime> getSourceType() {
        return OffsetDateTime.class;
    }

    @Override
    public Class<Timestamp> getTargetType() {
        return Timestamp.class;
    }

    @Override
    public Timestamp apply(final OffsetDateTime offsetDateTime) {
        // TODO - log warning message if zone info is set
        return Timestamp.valueOf(offsetDateTime.toLocalDateTime());
    }
}
