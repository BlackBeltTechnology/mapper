package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarFormatter implements Formatter<Calendar> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    @Override
    public String convertValueToString(final Calendar value) {
        return formatter.format(((GregorianCalendar) value).toZonedDateTime());
    }

    @Override
    public Calendar parseString(String str) {
        try {
            return GregorianCalendar.from(ZonedDateTime.from(formatter.parse(str)));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as ZonedDateTime", ex);
        }
    }

    @Override
    public Class<Calendar> getType() {
        return Calendar.class;
    }
}
