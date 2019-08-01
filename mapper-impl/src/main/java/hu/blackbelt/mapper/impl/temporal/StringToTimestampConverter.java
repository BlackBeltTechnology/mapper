package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.sql.Timestamp;

public class StringToTimestampConverter implements Converter<String, Timestamp> {

    private Formatter<Timestamp> formatter;

    public void setFormatter(final Formatter<Timestamp> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Timestamp> getTargetType() {
        return Timestamp.class;
    }

    @Override
    public Timestamp apply(final String s) {
        return formatter.parseString(s);
    }
}
