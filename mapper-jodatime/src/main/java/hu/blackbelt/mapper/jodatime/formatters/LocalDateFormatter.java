package hu.blackbelt.mapper.jodatime.formatters;

/*-
 * #%L
 * Mapper converters for Joda-Time
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
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true)
public class LocalDateFormatter implements Formatter<LocalDate> {

    private DateTimeFormatter formatter = ISODateTimeFormat.yearMonthDay();

    @Override
    public String convertValueToString(final LocalDate value) {
        return formatter.print(value);
    }

    @Override
    public LocalDate parseString(final String str) {
        return formatter.parseLocalDate(str);
    }

    @Override
    public Class<LocalDate> getType() {
        return LocalDate.class;
    }
}
