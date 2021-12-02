package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Slf4j
public class SqlTimestampToOffsetDateTimeConverter implements Converter<Timestamp, OffsetDateTime> {

    @Override
    public Class<Timestamp> getSourceType() {
        return Timestamp.class;
    }

    @Override
    public Class<OffsetDateTime> getTargetType() {
        return OffsetDateTime.class;
    }

    /**
     * Timestamp is returned in UTC because no zone is available in SQL timestamp type. Need to adjust by
     * application if other timestamp is needed.
     *
     * @param timestamp SQL timestamp value
     * @return offset datetime
     */
    @Override
    public OffsetDateTime apply(final Timestamp timestamp) {
        return timestamp.toInstant().atOffset(ZoneOffset.UTC);
    }
}
