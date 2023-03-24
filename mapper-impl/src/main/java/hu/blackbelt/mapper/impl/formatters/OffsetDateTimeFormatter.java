package hu.blackbelt.mapper.impl.formatters;

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

import hu.blackbelt.mapper.api.Formatter;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Slf4j
public class OffsetDateTimeFormatter implements Formatter<OffsetDateTime> {

    @Override
    public String convertValueToString(final OffsetDateTime value) {
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value);
    }

    @Override
    public OffsetDateTime parseString(final String str) {
        try {
            return OffsetDateTime.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(str));
        } catch (Exception e) {
            log.debug("Unable to parse string (" + str + ") to OffsetDateTime, retrying with LocalDateTime");
            try {
                return LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(str)).atOffset(ZoneOffset.UTC);
            } catch (Exception e1) {
                log.debug("Unable to parse string (" + str + ") to LocalDateTime", e1);
                throw new IllegalArgumentException("Unable to parse string (" + str + ") to OffsetDateTime", e);
            }
        }
    }

    @Override
    public Class<OffsetDateTime> getType() {
        return OffsetDateTime.class;
    }

}
