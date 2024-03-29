package hu.blackbelt.mapper.impl.numeric;

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
import lombok.NonNull;

import java.util.Date;

public class DateToLongConverter implements Converter<Date, Long> {

    @Override
    public Class<Date> getSourceType() {
        return Date.class;
    }

    @Override
    public Class<Long> getTargetType() {
        return Long.class;
    }

    @Override
    public Long apply(@NonNull final Date date) {
        return date.getTime();
    }
}
