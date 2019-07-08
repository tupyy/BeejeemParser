package com.beejeem.parser.value;

import com.beejeem.parser.type.Type;

import java.util.ArrayList;
import java.util.List;

public class ListValue<T> implements Variable<T> {

    private List<T> values = new ArrayList<>();
    private Type type;

    public ListValue(Type type) {
        this.type = type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Variable<T> clone() {
        List<T> clone = new ArrayList<>();
        clone.addAll(this.values);
        return (Variable<T>) clone;
    }

    @Override
    public Type getType() {
        return null;
    }

    public T get(int index) {
        return values.get(index);
    }

    @SuppressWarnings("unchecked")
    public Value<T> getValue(int index) {
        Value<T> value = this.type.createValue();
        value.set(this.values.get(index));
        return value;
    }

    @Override
    public void set(Variable<T> variable) {
        if (variable instanceof ListValue) {
            this.values = (List<T>) variable;
        } else {
            this.values.add(((Value<T>) variable).get());
        }
    }
}
