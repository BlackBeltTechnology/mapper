package hu.blackbelt.mapper.impl;

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

import hu.blackbelt.mapper.api.Coercer;
import hu.blackbelt.mapper.api.CoercerFactory;
import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.ConverterFactory;
import hu.blackbelt.mapper.api.ExtendableCoercer;
import lombok.extern.slf4j.Slf4j;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component(immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class DefaultCoercerFactory implements CoercerFactory {

    @Override
    public Coercer getCoercerInstance() {
        return new DefaultCoercer();
    }

    @Override
    public ExtendableCoercer getExtendableCoercerInstance() {
        return new DefaultCoercer();
    }
}
