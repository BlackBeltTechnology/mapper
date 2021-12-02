package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.sql.Time;

public class StringToSqlTimeConverter implements Converter<String, Time> {

    private Formatter<Time> formatter;

    public void setFormatter(final Formatter<Time> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Time> getTargetType() {
        return Time.class;
    }

    @Override
    public Time apply(final String s) {
        return formatter.parseString(s);
    }
}
