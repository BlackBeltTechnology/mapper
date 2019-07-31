package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.sql.Date;

public class StringToSqlDateConverter implements Converter<String, Date> {

    private Formatter<Date> formatter;

    public void setFormatter(final Formatter<Date> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Date> getTargetType() {
        return Date.class;
    }

    @Override
    public Date apply(final String s) {
        return formatter.parseString(s);
    }
}
