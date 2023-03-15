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

@Slf4j
public class SqlTimestampToZonedDateTimeConverter implements Converter<Timestamp, ZonedDateTime> {

    @Override
    public Class<Timestamp> getSourceType() {
        return Timestamp.class;
    }

    @Override
    public Class<ZonedDateTime> getTargetType() {
        return ZonedDateTime.class;
    }

    /**
     * Timestamp is returned in UTC zone because no zone is available in SQL timestamp type. Need to adjust by
     * application if other timestamp is needed.
     *
     * @param timestamp SQL timestamp value
     * @return zoned datetime
     */
    @Override
    public ZonedDateTime apply(final Timestamp timestamp) {
        ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(timestamp.toLocalDateTime());
        if (zoneOffset.getTotalSeconds() > 0) {
            log.warn("Timestamp converted to UTC, offset of original value: {} s", zoneOffset.getTotalSeconds());
        }
        return timestamp.toInstant().atZone(ZoneOffset.UTC);
    }
}
