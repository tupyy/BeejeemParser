package domain.variables;

public class StringVariable extends AbstractVariable<String> {
    public StringVariable(String name) {
        super(name);
    }

    public StringVariable(String name, String value) {
        super(name, value);
    }
}
