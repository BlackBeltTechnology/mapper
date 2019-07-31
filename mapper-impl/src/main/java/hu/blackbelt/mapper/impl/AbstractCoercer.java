package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Coercer;
import hu.blackbelt.mapper.api.ConverterFactory;

public abstract class AbstractCoercer implements Coercer {

    protected abstract ConverterFactory getConverterFactory();
}
