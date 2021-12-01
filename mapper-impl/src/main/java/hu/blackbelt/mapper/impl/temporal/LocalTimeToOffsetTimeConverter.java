package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.sql.Time;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class LocalTimeToOffsetTimeConverter implements Converter<LocalTime, OffsetTime> {

    @Override
    public Class<LocalTime> getSourceType() {
        return LocalTime.class;
    }

    @Override
    public Class<OffsetTime> getTargetType() {
        return OffsetTime.class;
    }

    /**
     * Time is returned in UTC because no zone is available in LocalTime type. Need to adjust by
     * application if other Time is needed.
     *
     * @param time SQL Time value
     * @return offset datetime
     */
    @Override
    public OffsetTime apply(final LocalTime time) {
        return time.atOffset(ZoneOffset.UTC);
    }
}
