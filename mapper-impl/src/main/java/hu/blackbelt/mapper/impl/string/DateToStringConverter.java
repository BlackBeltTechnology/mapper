package hu.blackbelt.mapper.impl.string;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.util.Date;

public class DateToStringConverter implements Converter<Date, String> {

    private Formatter<Date> formatter;

    public void setFormatter(final Formatter<Date> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<Date> getSourceType() {
        return Date.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String apply(final Date date) {
        return formatter.convertValueToString(date);
    }
}
