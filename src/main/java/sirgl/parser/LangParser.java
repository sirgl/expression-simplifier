package sirgl.parser;

import org.assertj.core.util.Lists;
import sirgl.lexer.Token;
import sirgl.lexer.TokenStream;
import sirgl.lexer.TokenType;
import sirgl.nodes.Identifier;
import sirgl.nodes.Literal;
import sirgl.nodes.Node;
import sirgl.nodes.ParenWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LangParser implements Parser {
    private static final Set<TokenType> firstPrimaryTokens = new HashSet<>();
    private static final Set<TokenType> firstFactorTokens = new HashSet<>();
    private static final Set<TokenType> firstExpressionTokens = new HashSet<>();
    private static final Set<TokenType> firstTermTokens = new HashSet<>();


    static {
        firstPrimaryTokens.add(TokenType.True);
        firstPrimaryTokens.add(TokenType.False);
        firstPrimaryTokens.add(TokenType.Identifier);
        firstPrimaryTokens.add(TokenType.Lparen);

        firstFactorTokens.addAll(firstPrimaryTokens);
        firstFactorTokens.add(TokenType.Not);

        firstTermTokens.addAll(firstFactorTokens);

        firstExpressionTokens.addAll(firstTermTokens);
    }

    @Override
    public Node parse(TokenStream tokenStream) throws IOException {
        return parseExpression(tokenStream, tokenStream.next(), null);
    }

    private Node parsePrimary(TokenStream tokenStream, Token currentToken, Node parent) throws IOException, UnexpectedTokenException {
        switch (currentToken.getType()) {
            case True:
                return new Literal(parent, true);
            case False:
                return new Literal(parent, false);
            case Identifier:
                return new Identifier(parent, currentToken.getValue());
            case Lparen:
                Token next = tokenStream.next();
                if(!startsLikeExpression(next)) {
                    throw new UnexpectedTokenException(next, new ArrayList<>(firstPrimaryTokens));
                }
                ParenWrapper parenWrapper = new ParenWrapper(parent);
                Node expression = parseExpression(tokenStream, next, parenWrapper);
                parenWrapper.setValue(expression);
                return parenWrapper;
        }
    }

    private boolean startsLikePrimary(Token currentToken) {
        return firstPrimaryTokens.contains(currentToken.getType());
    }

    private Node parseFactor(TokenStream tokenStream, Token currentToken, Node parent) {

    }

    private boolean startsLikeFactor(Token currentToken) {
        return firstFactorTokens.contains(currentToken.getType());
    }

    private Node parseTerm(TokenStream tokenStream, Token currentToken, Node parent) {

    }

    private boolean startsLikeTerm(Token currentToken) {
        return firstTermTokens.contains(currentToken.getType());
    }

    private Node parseExpression(TokenStream tokenStream, Token currentToken, Node parent) {

    }

    private boolean startsLikeExpression(Token currentToken) {
        return firstExpressionTokens.contains(currentToken.getType());
    }
}
