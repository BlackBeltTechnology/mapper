package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Slf4j
public class TimestampToZonedDateTimeConverter implements Converter<Timestamp, ZonedDateTime> {

    @Override
    public Class<Timestamp> getSourceType() {
        return Timestamp.class;
    }

    @Override
    public Class<ZonedDateTime> getTargetType() {
        return ZonedDateTime.class;
    }

    /**
     * Timestamp is returned in UTC zone because no zone is available in SQL timestamp type. Need to adjust by
     * application if other timestamp is needed.
     *
     * @param timestamp SQL timestamp value
     * @return zoned datetime
     */
    @Override
    public ZonedDateTime apply(final Timestamp timestamp) {
        if (timestamp.getTimezoneOffset() != 0) {
            log.warn("Timestamp converted to UTC, offset: {} min", timestamp.getTimezoneOffset());
        }
        return timestamp.toInstant().atZone(ZoneOffset.UTC);
    }
}
