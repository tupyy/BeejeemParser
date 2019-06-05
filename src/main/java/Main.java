import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {
    public static void main(String[] args) {
        String initialString = "var int x=\"ff,fdf\"\n";
        JobfileLexer lexer = new JobfileLexer(CharStreams.fromString(initialString));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JobfileParser parser = new JobfileParser(tokenStream);
        JobfileParser.ProgContext tree = parser.prog();
        JobfileBaseListener htmlListener = new HtmlChatListener();

        ParseTreeWalker walker = new ParseTreeWalker();
//        TestExpr loader = new TestExpr();
        walker.walk(htmlListener,tree);



    }
}
