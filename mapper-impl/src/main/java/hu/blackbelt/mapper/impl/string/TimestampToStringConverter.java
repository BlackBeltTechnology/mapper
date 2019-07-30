package hu.blackbelt.mapper.impl.string;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.sql.Timestamp;

public class TimestampToStringConverter implements Converter<Timestamp, String> {

    private Formatter<Timestamp> formatter;

    public void setFormatter(final Formatter<Timestamp> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<Timestamp> getSourceType() {
        return Timestamp.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String apply(final Timestamp timestamp) {
        return formatter.convertValueToString(timestamp);
    }
}
