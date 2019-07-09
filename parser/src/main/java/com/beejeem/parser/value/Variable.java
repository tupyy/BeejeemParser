package com.beejeem.parser.value;

import com.beejeem.parser.type.Type;

public interface Variable {

    /**
     * Return the type
     * @return type
     */
    public Type getType();

    /**
     * Return true if it is a value
     * @see Value
     * @return true if value
     */
    public boolean isValue();

    /**
     * Return true if it is a list
     * @return true if {@link ListValue}
     */
    public boolean isList();

    /**
     * Return true if it a map
     * @return
     */
    public boolean isMap();

    /**
     * Return the value
     * @return
     */
    public Value<?> asValue();

    /**
     * Return the list if any
     * @return
     */
    public ListValue<?> asList();



}
