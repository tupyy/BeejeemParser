package com.beejeem.parser.domain.variables;

public class IntegerVariable extends AbstractVariable<Integer> {

    public IntegerVariable(String name, Integer value) {
        super(name, value);
    }

    @Override
    public Variable<Integer> clone() {
        return new IntegerVariable(this.getName(), this.getValue());
    }
}
