package hu.blackbelt.mapper.impl.formatters;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter implements Formatter<Date> {

    private DateFormat dateFormat = new SimpleDateFormat();

    @Override
    public String convertValueToString(final Date value) {
        return dateFormat.format(value);
    }

    @Override
    public Date parseString(final String str) {
        try {
            return dateFormat.parse(str);
        } catch (ParseException ex) {
            throw new ConverterException("Unable to parse date value", ex);
        }
    }

    @Override
    public Class<Date> getType() {
        return Date.class;
    }
}
