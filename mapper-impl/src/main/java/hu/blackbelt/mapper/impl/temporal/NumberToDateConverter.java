package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.util.Date;

public class NumberToDateConverter implements Converter<Number, Date> {

    @Override
    public Class<Number> getSourceType() {
        return Number.class;
    }

    @Override
    public Class<Date> getTargetType() {
        return Date.class;
    }

    @Override
    public Date apply(final Number number) {
        return new Date(number.longValue());
    }
}
