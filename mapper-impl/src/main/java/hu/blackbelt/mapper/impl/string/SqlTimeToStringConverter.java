package hu.blackbelt.mapper.impl.string;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.sql.Time;

public class SqlTimeToStringConverter implements Converter<Time, String> {

    private Formatter<Time> formatter;

    public void setFormatter(final Formatter<Time> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<Time> getSourceType() {
        return Time.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String apply(final Time Time) {
        return formatter.convertValueToString(Time);
    }
}
