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
 * Makes use of {@link Converter}s to convert between an input value (of some specific type) and a desired output type.
 */
public interface Coercer {

    /**
     * Performs a coercion from an input type to a desired output type.
     *
     * @param sourceValue source value
     * @param targetClass target class
     * @param <S> source type
     * @param <T> target type
     * @return target value
     */
    <S, T> T coerce(S sourceValue, Class<T> targetClass);

    /**
     * Performs a coercion from an input type to a desired output type.
     *
     * @param sourceValue source value
     * @param targetClassName target class name
     * @param <S> source type
     * @param <T> target type
     * @return target value
     */
    <S, T> T coerce(S sourceValue, String targetClassName);
}
