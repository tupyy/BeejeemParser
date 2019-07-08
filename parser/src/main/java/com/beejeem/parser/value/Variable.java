package com.beejeem.parser.value;

import com.beejeem.parser.type.Type;

public interface Variable<T> extends Cloneable {

    public Variable<T> clone();

    public Type getType();

    public void set(Variable<T> variable);
}
