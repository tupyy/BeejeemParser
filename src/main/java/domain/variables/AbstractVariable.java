package domain.variables;

public abstract class AbstractVariable<T> implements Variable<T> {

    private final String name;
    private T value = null;

    public  AbstractVariable(String name) {
        this.name = name;
    }

    public AbstractVariable(String name, T value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public T getValue() {
        return null;
    }

    @Override
    public void setValue(T newValue) {
        this.value = newValue;
    }
}
