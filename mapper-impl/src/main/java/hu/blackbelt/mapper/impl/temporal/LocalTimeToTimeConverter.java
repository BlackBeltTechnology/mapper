package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.sql.Time;
import java.time.LocalTime;

public class LocalTimeToTimeConverter implements Converter<LocalTime, Time> {

    @Override
    public Class<LocalTime> getSourceType() {
        return LocalTime.class;
    }

    @Override
    public Class<Time> getTargetType() {
        return Time.class;
    }

    @Override
    public Time apply(final LocalTime LocalTime) {
        return new Time(LocalTime.toNanoOfDay() / (1000 * 1000));
    }
}
