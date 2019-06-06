package com.beejeem.parser.domain.variables;

public class ListVariable<T> extends AbstractVariable<T> {

    public ListVariable(String name) {
        super(name);
    }

    public ListVariable(String name, T value) {
        super(name, value);
    }
}
