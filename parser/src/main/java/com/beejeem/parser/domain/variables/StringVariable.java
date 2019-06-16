package com.beejeem.parser.domain.variables;

public class StringVariable extends AbstractVariable<String> {
    public StringVariable(String name, String value) {
        super(name, value);
    }

    @Override
    public Variable<String> clone() {
        return new StringVariable(this.getName(), this.getValue());
    }
}
