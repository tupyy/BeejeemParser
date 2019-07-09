package com.beejeem.parser.value;

import com.beejeem.parser.type.ListType;
import com.beejeem.parser.type.Type;

import java.util.ArrayList;
import java.util.List;

public class ListValue<T> implements Variable {

    private List<T> values = new ArrayList<>();
    private Type type;

    public ListValue() {
        this.type = new ListType();
    }

    public void add(T value) {
        this.values.add(value);
    }

    public T get(int index) {
        try {
            return this.values.get(index);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public Value<?> getAsValue(int index) {
        T value = this.get(index);
        if (value != null) {
            if (value instanceof Integer) {
                return new IntegerValue((Integer) value);
            } else if (value instanceof Float) {
                return new FloatValue((Float) value);
            } else if (value instanceof String) {
                return new StringValue((String) value);
            } else if (value instanceof Boolean) {
                return new BooleanValue((Boolean) value);
            }
        }
        return null;
    }

    public int size() {
        return this.values.size();
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public boolean isValue() {
        return false;
    }

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    public boolean isMap() {
        return false;
    }

    @Override
    public Value<?> asValue() {
        return null;
    }

    @Override
    public ListValue<T> asList() {
        ListValue<T> newList = new ListValue<>();
        for (T element: this.values) {
            newList.add(element);
        }
        return newList;
    }
}
