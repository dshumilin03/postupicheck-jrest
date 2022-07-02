package ru.joinmore.postupicheck.api.converters;

public interface Converter<S,T> {

    T convert(S source);

}
