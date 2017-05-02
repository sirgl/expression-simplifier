package sirgl.lexer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LexerTestUtils {
    private LexerTestUtils() {
    }

    public static Lexer makeLexer(String str) throws IOException {
        return new LangLexer(new StringReader(str));
    }

    public static List<Token> readUntilEof(Lexer lexer) throws IOException {
        Token token = lexer.next();
        List<Token> tokens = new ArrayList<>();
        while (token != null) {
            tokens.add(token);
            token = lexer.next();
        }
        return tokens;
    }

    public static List<Token> readUntilEof(String str) throws IOException {
        return readUntilEof(makeLexer(str));
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
