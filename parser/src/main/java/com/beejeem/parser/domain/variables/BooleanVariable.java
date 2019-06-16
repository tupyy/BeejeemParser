package com.beejeem.parser.domain.variables;

public class BooleanVariable extends AbstractVariable<Boolean> {

    public BooleanVariable(String name, Boolean value) {
        super(name, value);
    }

    @Override
    public Variable<Boolean> clone() {
        return new BooleanVariable(this.getName(), this.getValue());
    }
}
