package com.beejeem.parser.domain.variables;

public class FloatVariable extends AbstractVariable<Float> {

    public FloatVariable(String name, Float value) {
        super(name, value);
    }

    @Override
    public Variable<Float> clone() {
        return new FloatVariable(this.getName(), this.getValue());
    }
}
