import com.beejeem.parser.domain.Program;
import com.beejeem.parser.parser.DefaultParser;

public class Main {
    public static void main(String[] args) {
        String initialString = "var x=true\n";
        final Program result = new DefaultParser().parse(initialString);
    }
}
