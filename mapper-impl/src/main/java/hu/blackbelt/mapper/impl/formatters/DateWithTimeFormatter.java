package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Slf4j
public class DateWithTimeFormatter implements Formatter<Date> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String convertValueToString(final Date value) {
        return formatter.format(LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public Date parseString(final String str) {
        try {
            return Date.from(LocalDateTime.from(formatter.parse(str)).atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as LocalDateTime", ex);
        }
    }

    @Override
    public Class<Date> getType() {
        return Date.class;
    }
}
