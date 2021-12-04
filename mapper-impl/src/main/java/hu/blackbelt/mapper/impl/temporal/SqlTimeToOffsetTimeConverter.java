package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.sql.Time;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class SqlTimeToOffsetTimeConverter implements Converter<Time, OffsetTime> {

    @Override
    public Class<Time> getSourceType() {
        return Time.class;
    }

    @Override
    public Class<OffsetTime> getTargetType() {
        return OffsetTime.class;
    }

    /**
     * Time is returned in UTC because no zone is available in SQL Time type. Need to adjust by
     * application if other Time is needed.
     *
     * @param time SQL Time value
     * @return offset datetime
     */
    @Override
    public OffsetTime apply(final Time time) {
        return time.toLocalTime().atOffset(ZoneOffset.UTC);
    }
}
