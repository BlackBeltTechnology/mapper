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
