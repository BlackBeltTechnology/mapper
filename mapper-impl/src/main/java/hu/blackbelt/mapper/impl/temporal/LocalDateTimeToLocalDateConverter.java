package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeToLocalDateConverter implements Converter<LocalDateTime, LocalDate> {

    @Override
    public Class<LocalDateTime> getSourceType() {
        return LocalDateTime.class;
    }

    @Override
    public Class<LocalDate> getTargetType() {
        return LocalDate.class;
    }

    @Override
    public LocalDate apply(final LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }
}
