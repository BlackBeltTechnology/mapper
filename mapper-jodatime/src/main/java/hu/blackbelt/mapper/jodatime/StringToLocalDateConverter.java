package hu.blackbelt.mapper.jodatime;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;
import org.joda.time.LocalDate;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private Formatter<LocalDate> formatter;

    public void setFormatter(Formatter<LocalDate> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<LocalDate> getTargetType() {
        return LocalDate.class;
    }

    @Override
    public LocalDate apply(final String s) {
        return formatter.parseString(s);
    }
}
