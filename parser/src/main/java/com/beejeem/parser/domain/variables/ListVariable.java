package com.beejeem.parser.domain.variables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListVariable<T> extends AbstractVariable<T> {

    private List<T> values = new ArrayList<>();

    public ListVariable(String name) {
        super(name);
    }

    public void add(T... values) {
        this.values.addAll(Arrays.asList(values));
    }

    public void add(List<T> values) {
        this.values.addAll(values);
    }

    public T getValue() {
        throw new UnsupportedOperationException("Not supported for list variables");
    }

    public void setValues(T value) {
        throw new UnsupportedOperationException("Not supported for list variables");
    }
}
