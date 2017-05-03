package sirgl.parser;

import sirgl.lexer.Token;

public class UnexpectedTokensEnd extends ParsingException {
    private Token token;

    public UnexpectedTokensEnd() {
    }
}
