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

import java.math.BigDecimal;

public class StringToByteConverter implements Converter<String, Byte> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Byte> getTargetType() {
        return Byte.class;
    }

    @Override
    public Byte apply(final String s) {
        return new BigDecimal(s).byteValueExact();
    }
}
