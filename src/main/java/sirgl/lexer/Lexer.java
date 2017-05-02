package sirgl.lexer;

import java.io.IOException;

public interface Lexer {
    Token next() throws IOException;
}
