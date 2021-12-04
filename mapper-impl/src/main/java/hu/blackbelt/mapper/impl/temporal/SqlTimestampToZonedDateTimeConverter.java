package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Slf4j
public class SqlTimestampToZonedDateTimeConverter implements Converter<Timestamp, ZonedDateTime> {

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
        ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(timestamp.toLocalDateTime());
        if (zoneOffset.getTotalSeconds() > 0) {
            log.warn("Timestamp converted to UTC, offset of original value: {} s", zoneOffset.getTotalSeconds());
        }
        return timestamp.toInstant().atZone(ZoneOffset.UTC);
    }
}
