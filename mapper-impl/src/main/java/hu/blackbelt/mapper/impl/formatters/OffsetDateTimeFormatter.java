package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.Formatter;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Slf4j
public class OffsetDateTimeFormatter implements Formatter<OffsetDateTime> {

    @Override
    public String convertValueToString(final OffsetDateTime value) {
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value);
    }

    @Override
    public OffsetDateTime parseString(final String str) {
        try {
            return OffsetDateTime.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(str));
        } catch (Exception e) {
            log.debug("Unable to parse string (" + str + ") to OffsetDateTime, retrying with LocalDateTime");
            try {
                return LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(str)).atOffset(ZoneOffset.UTC);
            } catch (Exception e1) {
                log.debug("Unable to parse string (" + str + ") to LocalDateTime", e1);
                throw new IllegalArgumentException("Unable to parse string (" + str + ") to OffsetDateTime", e);
            }
        }
    }

    @Override
    public Class<OffsetDateTime> getType() {
        return OffsetDateTime.class;
    }

}
