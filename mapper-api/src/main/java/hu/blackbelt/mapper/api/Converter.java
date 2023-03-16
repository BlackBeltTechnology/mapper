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

import java.util.function.Function;

/**
 * Converter from a given type to another.
 *
 * @param <S> source type
 * @param <T> target type
 */
public interface Converter<S, T> extends Function<S, T> {

    /**
     * Get source type.
     *
     * @return source type
     */
    Class<S> getSourceType();

    /**
     * Get target type.
     *
     * @return target type
     */
    Class<T> getTargetType();

    /**
     * Convert value s to target type.
     *
     * @param s value to convert
     * @return converted value
     */
    @Override
    T apply(S s);
}
