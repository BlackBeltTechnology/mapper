package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class SqlDateFormatter implements Formatter<Date> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public String convertValueToString(final Date value) {
        return formatter.format(value.toLocalDate());
    }

    @Override
    public Date parseString(final String str) {
        try {
            final java.util.Date date = java.util.Date.from(LocalDate.from(formatter.parse(str)).atStartOfDay(ZoneId.systemDefault()).toInstant());
            return new Date(date.getTime());
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as LocalDateTime", ex);
        }
    }

    @Override
    public Class<Date> getType() {
        return Date.class;
    }
}
