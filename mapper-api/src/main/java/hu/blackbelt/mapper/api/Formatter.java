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
 * Formatter of a given (structured primitive) type that can parse strings and convert values to string.
 *
 * @param <T> type
 */
public interface Formatter<T> {

    /**
     * Convert a given value to string.
     *
     * @param value value
     * @return string value
     */
    String convertValueToString(T value);

    /**
     * Parse string and return value in a given type.
     *
     * @param str string value
     * @return parsed value
     */
    T parseString(final String str);

    /**
     * Get type of formatter.
     *
     * @return formatter class
     */
    Class<T> getType();
}
