package hu.blackbelt.mapper.api;

/**
 * Corercer factory interface. It can give a new coercer instance.
 */
public interface CoercerFactory {

    Coercer getCoercerInstance();
    Coercer getExtendableCoercerInstance();
}
