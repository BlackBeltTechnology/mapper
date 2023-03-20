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
import hu.blackbelt.mapper.api.Formatter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class StringToDateConverter implements Converter<String, Date> {

    private Formatter<Date> formatter;

    public void setFormatter(final Formatter<Date> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Date> getTargetType() {
        return Date.class;
    }

    @Override
    public Date apply(final String s) {
        return formatter.parseString(s);
    }
}
