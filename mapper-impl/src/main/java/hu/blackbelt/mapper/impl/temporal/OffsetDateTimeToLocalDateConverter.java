package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.time.*;

public class OffsetDateTimeToLocalDateConverter implements Converter<OffsetDateTime, LocalDate> {

    @Override
    public Class<OffsetDateTime> getSourceType() {
        return OffsetDateTime.class;
    }

    @Override
    public Class<LocalDate> getTargetType() {
        return LocalDate.class;
    }

    @Override
    public LocalDate apply(final OffsetDateTime offsetDateTime) {
        return offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime().toLocalDate();
    }
}
