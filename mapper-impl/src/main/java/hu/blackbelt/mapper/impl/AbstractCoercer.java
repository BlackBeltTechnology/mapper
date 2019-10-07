package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Coercer;
import hu.blackbelt.mapper.api.ConverterFactory;

/**
 * Abstract coerces, converters are provided by a{@link ConverterFactory}.
 */
public abstract class AbstractCoercer implements Coercer {

    public abstract ConverterFactory getConverterFactory();
}
