package com.beejeem.parser.domain.variables;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractVariable<T> implements Variable<T> {

    private final String name;
    private T value;

    public AbstractVariable(String name, T value) {

        checkNotNull(name, "Name cannot be null");
        checkNotNull(value, "Value cannot be null");

        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(T newValue) {
        this.value = newValue;
    }
}
