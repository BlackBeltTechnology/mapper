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
 * Exception thrown on unsupported/invalid conversions.
 */
public class ConverterException extends RuntimeException {

    /**
     * Create a new exception instance.
     *
     * @param message message
     */
    public ConverterException(final String message) {
        super(message);
    }

    /**
     * Create a new exception instance.
     *
     * @param message message
     * @param cause   cause
     */
    public ConverterException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
