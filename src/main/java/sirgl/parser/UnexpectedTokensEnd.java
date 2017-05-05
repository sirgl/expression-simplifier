package sirgl.parser;

import sirgl.lexer.TokenType;

import java.util.Set;

public class UnexpectedTokensEnd extends ParsingException {
    private Set<TokenType> expectedTokens;

    public UnexpectedTokensEnd(Set<TokenType> expectedTokens) {
        this.expectedTokens = expectedTokens;
    }
}
