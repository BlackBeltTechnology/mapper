package hu.blackbelt.mapper.jodatime.formatters;

import hu.blackbelt.mapper.api.Formatter;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true)
public class LocalDateFormatter implements Formatter<LocalDate> {

    private DateTimeFormatter formatter = ISODateTimeFormat.yearMonthDay();

    @Override
    public String convertValueToString(final LocalDate value) {
        return formatter.print(value);
    }

    @Override
    public LocalDate parseString(final String str) {
        return formatter.parseLocalDate(str);
    }

    @Override
    public Class<LocalDate> getType() {
        return LocalDate.class;
    }
}
