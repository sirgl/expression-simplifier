package sirgl.lexer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class LexerTestUtils {
    private LexerTestUtils() {
    }

    public static TokenStream makeTokenStream(String str) throws IOException {
        return new LangTokenStream(new StringReader(str));
    }

    public static List<Token> readUntilEof(TokenStream tokenStream) throws IOException {
        Token token = tokenStream.next();
        List<Token> tokens = new ArrayList<>();
        while (token != null) {
            tokens.add(token);
            token = tokenStream.next();
        }
        return tokens;
    }

    public static List<Token> readUntilEof(String str) throws IOException {
        return readUntilEof(makeTokenStream(str));
    }

    public static List<TokenType> toTokenTypes(List<Token> tokens) {
        List<TokenType> list = new ArrayList<>();
        for (Token token : tokens) {
            TokenType type = token.getType();
            list.add(type);
        }
        return list;
    }
}
