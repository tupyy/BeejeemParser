package domain.variables;

public class BooleanVariable extends AbstractVariable<Boolean> {
    public BooleanVariable(String name) {
        super(name);
    }

    public BooleanVariable(String name, Boolean value) {
        super(name, value);
    }
}
