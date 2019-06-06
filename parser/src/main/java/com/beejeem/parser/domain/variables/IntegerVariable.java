package com.beejeem.parser.domain.variables;

public class IntegerVariable extends AbstractVariable<Integer> {

    public IntegerVariable(String name) {
        super(name);
    }

    public IntegerVariable(String name, Integer value) {
        super(name, value);
    }
}
