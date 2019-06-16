package com.beejeem.parser.domain.variables;

import com.beejeem.parser.domain.Statement;

public interface Variable<T> extends Statement {

    /**
     * Get variable name
     * @return name
     */
    public String getName();

    /**
     * Return the value
     * @return value
     */
    public T getValue();

    /**
     * Set new value
     * @param newValue
     */
    public void setValue(T newValue);

    /**
     * Clone the variable
     * @return the clone
     */
    public Variable<T> clone();
}
