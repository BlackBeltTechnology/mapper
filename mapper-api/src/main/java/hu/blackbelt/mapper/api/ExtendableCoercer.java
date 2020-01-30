package hu.blackbelt.mapper.api;

import hu.blackbelt.mapper.api.Coercer;
import hu.blackbelt.mapper.api.ConverterFactory;

/**
 * Extendable coerces which allow to extend converter functions related to the implemented @{@link Coercer}.
 * Converters are provided by a{@link ConverterFactory}.
 */
public interface ExtendableCoercer extends Coercer {

    ConverterFactory getConverterFactory();
}
