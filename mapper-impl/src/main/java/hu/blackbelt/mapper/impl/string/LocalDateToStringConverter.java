package hu.blackbelt.mapper.impl.string;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;

import java.time.LocalDate;

public class LocalDateToStringConverter implements Converter<LocalDate, String> {

    private Formatter<LocalDate> formatter;

    public void setFormatter(final Formatter<LocalDate> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<LocalDate> getSourceType() {
        return LocalDate.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String apply(final LocalDate localDate) {
        return formatter.convertValueToString(localDate);
    }
}
