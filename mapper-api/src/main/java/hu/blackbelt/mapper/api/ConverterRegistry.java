package hu.blackbelt.mapper.api;

/*-
 * #%L
 * Mapper API
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

/**
 * Converter registry that converters can be registered in / unregistered from.
 */
public interface ConverterRegistry {

    /**
     * Register a converter.
     *
     * @param converter converter
     * @param <S>       source type
     * @param <T>       target type
     */
    <S, T> void registerConverter(Converter<S, T> converter);

    /**
     * Unregister a converter.
     *
     * @param converter converter
     * @param <S>       source type
     * @param <T>       target type
     */
    <S, T> void unregisterConverter(Converter<S, T> converter);
}
