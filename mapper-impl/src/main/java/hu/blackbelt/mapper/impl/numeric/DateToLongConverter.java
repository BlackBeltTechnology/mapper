package hu.blackbelt.mapper.impl.numeric;

import hu.blackbelt.mapper.api.Converter;
import lombok.NonNull;

import java.util.Date;

public class DateToLongConverter implements Converter<Date, Long> {

    @Override
    public Class<Date> getSourceType() {
        return Date.class;
    }

    @Override
    public Class<Long> getTargetType() {
        return Long.class;
    }

    @Override
    public Long apply(@NonNull final Date date) {
        return date.getTime();
    }
}
