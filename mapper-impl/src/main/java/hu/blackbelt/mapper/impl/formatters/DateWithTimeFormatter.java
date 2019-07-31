package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Slf4j
public class DateWithTimeFormatter implements Formatter<Date> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final static int OFFSET_IN_MINUTES = new Date().getTimezoneOffset();

    private int offsetInMinutes = OFFSET_IN_MINUTES;

    @Override
    public String convertValueToString(final Date value) {
        if (value.getTimezoneOffset() != offsetInMinutes) {
            log.warn("Offset is different from converter configuration (ie. value is in another time zone than the application is running in), need to adjust value when loaded");
        }

        return formatter.format(LocalDateTime.ofEpochSecond(value.getTime() / 1000L - value.getTimezoneOffset() * 60, (int) (value.getTime() % 1000L) * 1000000, ZoneOffset.UTC));
    }

    @Override
    public Date parseString(final String str) {
        try {
            return Date.from(LocalDateTime.from(formatter.parse(str)).toInstant(ZoneOffset.ofTotalSeconds(-offsetInMinutes * 60)));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as LocalDateTime", ex);
        }
    }

    @Override
    public Class<Date> getType() {
        return Date.class;
    }

    public void setOffsetInMinutes(final int offsetInMinutes) {
        this.offsetInMinutes = offsetInMinutes;
    }
}
