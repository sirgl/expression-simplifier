package sirgl.analysis;

import sirgl.lexer.LangTokenStream;
import sirgl.nodes.Node;
import sirgl.nodes.Root;
import sirgl.parser.LangParser;
import sirgl.parser.ParsingException;

import java.io.IOException;
import java.io.StringReader;

public class ExpressionSimplifier {
    public SimplificationResult simplify(String str) {
        try {
            LangTokenStream tokenStream = new LangTokenStream(new StringReader(str));
            Node node = new LangParser(tokenStream).parse();
            Root root = new Root(node);
            TreeWalker walker = new TreeWalker(this::acceptNode);
            walker.walk(node);

        } catch (IOException | ParsingException e) {
            e.printStackTrace(); // TODO
        }
        return null;
    }

    public Node acceptNode(Node node) {
        return null;
    }
}
