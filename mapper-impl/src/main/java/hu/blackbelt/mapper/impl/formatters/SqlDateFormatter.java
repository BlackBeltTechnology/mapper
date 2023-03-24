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
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class SqlDateFormatter implements Formatter<Date> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public String convertValueToString(final Date value) {
        return formatter.format(value.toLocalDate());
    }

    @Override
    public Date parseString(final String str) {
        try {
            final java.util.Date date = java.util.Date.from(LocalDate.from(formatter.parse(str)).atStartOfDay(ZoneId.systemDefault()).toInstant());
            return new Date(date.getTime());
        } catch (DateTimeParseException ex) {
            throw new ConverterException("Unable to parse string as LocalDateTime", ex);
        }
    }

    @Override
    public Class<Date> getType() {
        return Date.class;
    }
}
