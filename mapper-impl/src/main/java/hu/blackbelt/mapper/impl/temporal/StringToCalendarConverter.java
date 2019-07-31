package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.util.Calendar;

public class StringToCalendarConverter implements Converter<String, Calendar> {

    private Formatter<Calendar> formatter;

    public void setFormatter(final Formatter<Calendar> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Calendar> getTargetType() {
        return Calendar.class;
    }

    @Override
    public Calendar apply(final String s) {
        return formatter.parseString(s);
    }
}
