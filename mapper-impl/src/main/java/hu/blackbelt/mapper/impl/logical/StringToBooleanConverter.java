package hu.blackbelt.mapper.impl.logical;

import hu.blackbelt.mapper.api.Converter;

public class StringToBooleanConverter implements Converter<String, Boolean> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Boolean> getTargetType() {
        return Boolean.class;
    }

    @Override
    public Boolean apply(final String s) {
        return Boolean.parseBoolean(s);
    }
}
