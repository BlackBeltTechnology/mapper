package hu.blackbelt.mapper.impl.string;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.util.Calendar;

public class CalendarToStringConverter implements Converter<Calendar, String> {

    private Formatter<Calendar> formatter;

    public void setFormatter(final Formatter<Calendar> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<Calendar> getSourceType() {
        return Calendar.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String apply(final Calendar calendar) {
        return formatter.convertValueToString(calendar);
    }
}
