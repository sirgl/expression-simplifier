package sirgl.parser;

import sirgl.nodes.Node;

import java.io.IOException;

import static sirgl.lexer.LexerTestUtils.makeTokenStream;

public class ParserTestUtils {
    private ParserTestUtils() {
    }

    public static Node parseStr(String str) throws IOException, ParsingException {
        return new LangParser(makeTokenStream(str)).parse();
    }
}
