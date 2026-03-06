package com.cmis.cooperative.utils.converter;

public interface Converter<S, T> {
    T convert(S source);
}
