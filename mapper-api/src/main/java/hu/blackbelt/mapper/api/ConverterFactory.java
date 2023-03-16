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

import java.util.Collection;

/**
 * Converter factory that provides converters.
 */
public interface ConverterFactory extends ConverterRegistry {

    /**
     * Get all converters that convert any values from a given type.
     *
     * @param sourceType source class
     * @param <S>        source type
     * @return converters
     */
    <S> Collection<Converter> getConvertersFrom(Class<S> sourceType);

    /**
     * Get all converters that convert any values to a given type.
     *
     * @param targetType target class
     * @param <T>        target type
     * @return converters
     */
    <T> Collection<Converter> getConvertersTo(Class<T> targetType);

    /**
     * Get all converters that convert any values to a given type.
     *
     * @param targetTypeName target class name
     * @param <T>            target type
     * @return converters
     */
    <T> Collection<Converter> getConvertersTo(String targetTypeName);

    /**
     * Get all converters that convert any values from a given type to a given type.
     *
     * @param sourceType source class
     * @param targetType target class
     * @param <S>        source type
     * @param <T>        target type
     * @return converters
     */
    <S, T> Collection<Converter> getConverters(Class<S> sourceType, Class<T> targetType);
}
