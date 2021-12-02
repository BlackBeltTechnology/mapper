package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeToOffsetDateTimeConverter implements Converter<LocalDateTime, OffsetDateTime> {

    @Override
    public Class<LocalDateTime> getSourceType() {
        return LocalDateTime.class;
    }

    @Override
    public Class<OffsetDateTime> getTargetType() {
        return OffsetDateTime.class;
    }

    /**
     * Time is returned in UTC because no zone is available in LocalDateTime type. Need to adjust by
     * application if other Time is needed.
     *
     * @param time SQL Time value
     * @return offset datetime
     */
    @Override
    public OffsetDateTime apply(final LocalDateTime time) {
        return time.toInstant(ZoneOffset.UTC).atOffset(ZoneOffset.UTC);
    }
}
