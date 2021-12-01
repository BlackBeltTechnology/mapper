package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
        return LocalDateTime.ofEpochSecond(time.getSecond(), time.getNano(), ZoneOffset.UTC);
    }
}
