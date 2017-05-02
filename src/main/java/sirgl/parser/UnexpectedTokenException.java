package sirgl.parser;

import sirgl.lexer.Token;
import sirgl.lexer.TokenType;

import java.util.List;

public class UnexpectedTokenException extends ParsingException {
    private final Token token;
    private final List<TokenType> possibleAlternatives;

    public UnexpectedTokenException(Token token, List<TokenType> possibleAlternatives) {
        this.token = token;
        this.possibleAlternatives = possibleAlternatives;
    }
}
