package com.beejeem.parser.domain.variables;

public class FloatVariable extends AbstractVariable<Float> {

    public FloatVariable(String name) {
        super(name);
    }

    public FloatVariable(String name, Float value) {
        super(name, value);
    }
}
