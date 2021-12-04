package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.sql.Time;
import java.time.OffsetTime;

public class OffsetTimeToSqlTimeConverter implements Converter<OffsetTime, Time> {

    @Override
    public Class<OffsetTime> getSourceType() {
        return OffsetTime.class;
    }

    @Override
    public Class<Time> getTargetType() {
        return Time.class;
    }

    @Override
    public Time apply(final OffsetTime offsetTime) {
        return Time.valueOf(offsetTime.withOffsetSameInstant(ZoneOffset.UTC).toLocalTime());
    }
}
