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

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String convertValueToString(final LocalDateTime value) {
        return formatter.format(value);
    }

    @Override
    public LocalDateTime parseString(final String str) {
        try {
            return LocalDateTime.from(formatter.parse(str));
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as LocalDateTime", ex);
        }
    }

    @Override
    public Class<LocalDateTime> getType() {
        return LocalDateTime.class;
    }
}
