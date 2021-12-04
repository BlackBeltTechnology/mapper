package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class ZonedDateTimeToLocalDateConverter implements Converter<ZonedDateTime, LocalDate> {

    @Override
    public Class<ZonedDateTime> getSourceType() {
        return ZonedDateTime.class;
    }

    @Override
    public Class<LocalDate> getTargetType() {
        return LocalDate.class;
    }

    @Override
    public LocalDate apply(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.toLocalDateTime().toLocalDate();
    }
}
