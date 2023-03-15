package hu.blackbelt.mapper.jodatime;

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

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.Formatter;
import org.joda.time.LocalDate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class LocalDateToStringConverter implements Converter<LocalDate, String> {

    @Reference
    Formatter<LocalDate> formatter;

    public void setFormatter(final Formatter<LocalDate> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<LocalDate> getSourceType() {
        return LocalDate.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String apply(final LocalDate localDate) {
        return formatter.convertValueToString(localDate);
    }
}
