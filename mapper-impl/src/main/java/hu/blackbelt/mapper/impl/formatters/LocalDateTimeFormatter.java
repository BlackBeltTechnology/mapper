package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.Formatter;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    @Override
    public String convertValueToString(final LocalDateTime value) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value);
    }

    @Override
    public LocalDateTime parseString(final String str) {
        try {
            return LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(str));
        } catch (Exception e) {
            log.debug("Unable to parse string (" + str + ") to LocalDateTime, retrying with OffsetDateTime");
            try {
                return OffsetDateTime.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(str)).atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            } catch (Exception e1) {
                log.debug("Unable to parse string (" + str + ") to OffsetDateTime, retrying with LocalTime", e1);
                try {
                    LocalTime localTime = LocalTime.parse(str);
                    return LocalDateTime.ofEpochSecond(localTime.toSecondOfDay(), localTime.getNano(), ZoneOffset.UTC);
                } catch (Exception e2) {
                    log.debug("Unable to parse string (" + str + ") to LocalTime", e2);
                    throw new IllegalArgumentException("Unable to parse string (" + str + ") to LocalDateTime", e);
                }
            }
        }
    }

    @Override
    public Class<LocalDateTime> getType() {
        return LocalDateTime.class;
    }

}
