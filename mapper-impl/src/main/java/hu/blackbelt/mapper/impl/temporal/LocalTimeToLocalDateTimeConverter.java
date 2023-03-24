package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.time.*;
import java.time.LocalDateTime;

public class LocalTimeToLocalDateTimeConverter implements Converter<LocalTime, LocalDateTime> {

    @Override
    public Class<LocalTime> getSourceType() {
        return LocalTime.class;
    }

    @Override
    public Class<LocalDateTime> getTargetType() {
        return LocalDateTime.class;
    }

    /**
     * Time is returned in UTC because no zone is available in LocalTime type. Need to adjust by
     * application if other Time is needed.
     *
     * @param time SQL Time value
     * @return offset datetime
     */
    @Override
    public LocalDateTime apply(final LocalTime time) {
        return LocalDateTime.ofEpochSecond(time.toEpochSecond(LocalDate.EPOCH, ZoneOffset.UTC), time.getNano(), ZoneOffset.UTC);
    }
}
