package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.time.LocalTime;
import java.time.OffsetTime;

public class OffsetTimeToLocalTimeConverter implements Converter<OffsetTime, LocalTime> {

    @Override
    public Class<OffsetTime> getSourceType() {
        return OffsetTime.class;
    }

    @Override
    public Class<LocalTime> getTargetType() {
        return LocalTime.class;
    }

    @Override
    public LocalTime apply(final OffsetTime offsetTime) {
        return offsetTime.withOffsetSameInstant(ZoneOffset.UTC).toLocalTime();
    }
}
