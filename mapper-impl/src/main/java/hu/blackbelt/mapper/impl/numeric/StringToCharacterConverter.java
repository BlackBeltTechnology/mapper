package hu.blackbelt.mapper.impl.numeric;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.ConverterException;

public class StringToCharacterConverter implements Converter<String, Character> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Character> getTargetType() {
        return Character.class;
    }

    @Override
    public Character apply(final String s) {
        if (s.length() != 1) {
            throw new ConverterException("Length must be 1");
        }
        return s.charAt(0);
    }
}
