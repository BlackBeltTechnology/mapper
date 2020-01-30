package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Slf4j
public class ZonedDateTimeToTimestampConverter implements Converter<ZonedDateTime, Timestamp> {

    @Override
    public Class<ZonedDateTime> getSourceType() {
        return ZonedDateTime.class;
    }

    @Override
    public Class<Timestamp> getTargetType() {
        return Timestamp.class;
    }

    @Override
    public Timestamp apply(final ZonedDateTime zonedDateTime) {
        final ZoneId targetZone = ZoneOffset.UTC;
        if (!Objects.equals(zonedDateTime.getZone(), targetZone)) {
            log.warn("Zoned date time converted to SQL timestamp, zone info lost");
        }
        return new Timestamp(zonedDateTime.withZoneSameInstant(targetZone).toEpochSecond() * 1000L + zonedDateTime.getNano() / 1000000);
    }
}
