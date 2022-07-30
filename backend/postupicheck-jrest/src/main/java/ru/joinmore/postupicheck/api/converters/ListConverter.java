package ru.joinmore.postupicheck.api.converters;

import java.util.Collection;
import java.util.List;

public interface ListConverter<S, T> {

    List<T> convert(List<S> source);
}
