package hu.blackbelt.mapper.impl.temporal;

/*-
 * #%L
 * Mapper implementation
 * %%
 * Copyright (C) 2018 - 2023 BlackBelt Technology
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import hu.blackbelt.mapper.api.Converter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Slf4j
public class ZonedDateTimeToSqlTimestampConverter implements Converter<ZonedDateTime, Timestamp> {

    @Override
    public Class<ZonedDateTime> getSourceType() {
        return ZonedDateTime.class;
    }

    @Override
    public Class<Timestamp> getTargetType() {
        return Timestamp.class;
    }

    @Override
    public Timestamp apply(final ZonedDateTime zonedDateTime) {
        final ZoneId targetZone = ZoneOffset.UTC;
        if (!Objects.equals(zonedDateTime.getZone(), targetZone)) {
            log.warn("Zoned date time converted to SQL timestamp, zone info lost");
        }
        return new Timestamp(zonedDateTime.withZoneSameInstant(targetZone).toEpochSecond() * 1000L + zonedDateTime.getNano() / 1000000);
    }
}
