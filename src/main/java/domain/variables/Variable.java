package domain.variables;

public interface Variable<T> {

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
}
