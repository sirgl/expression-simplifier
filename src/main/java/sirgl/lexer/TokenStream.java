package sirgl.lexer;

import java.io.IOException;

public interface TokenStream {
    Token next() throws IOException;
}
