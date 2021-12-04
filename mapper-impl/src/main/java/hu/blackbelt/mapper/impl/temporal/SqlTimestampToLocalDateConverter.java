package hu.blackbelt.mapper.impl.temporal;

import hu.blackbelt.mapper.api.Converter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDate;

@Slf4j
public class SqlTimestampToLocalDateConverter implements Converter<Timestamp, LocalDate> {

    @Override
    public Class<Timestamp> getSourceType() {
        return Timestamp.class;
    }

    @Override
    public Class<LocalDate> getTargetType() {
        return LocalDate.class;
    }

    @Override
    public LocalDate apply(final Timestamp timestamp) {
        return timestamp.toLocalDateTime().toLocalDate();
    }
}
