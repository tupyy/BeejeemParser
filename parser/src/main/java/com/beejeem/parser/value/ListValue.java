package com.beejeem.parser.value;

import com.beejeem.parser.exception.InterpreterException;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.type.Type;

import java.util.ArrayList;
import java.util.List;

public class ListValue<T> implements Variable,Collection {

    private List<T> values = new ArrayList<>();
    private final Type type;

    public ListValue(Type type) {
        this.type = type;
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

    public Value<?> invokeMethod(Collection.Methods methodID, Value<?> argument) {
        switch (methodID) {
            case GET:
                if (! (argument instanceof IntegerValue)) {
                    throw new InterpreterException("List get method index has to be an integer.");
                }
                Value<T> value = this.type.createValue();
                value.set(this.values.get(((IntegerValue) argument).get()));
                return value;
            case ADD:
                if ( !argument.getType().isEqual(this.type) ) {
                    throw new InvalidOperationException(
                            String.format("Type mismatch. Trying to add an element of " +
                                          "type %s into a list of %s",argument.getType().toString(), this.getType().toString()));
                }
                this.values.add((T) argument.get());
            case SIZE:
                return new IntegerValue(this.values.size());
            case PUT:
                break;
        }
        return new VoidValue();
    }

    public int size() {
        return this.values.size();
    }

    @Override
    public Type getType() {
        return this.type;
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

    public void fromList(List<T> values) {
        this.values.addAll(values);
    }
}
