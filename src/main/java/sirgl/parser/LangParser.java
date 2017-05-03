package sirgl.parser;

import sirgl.lexer.Token;
import sirgl.lexer.TokenStream;
import sirgl.lexer.TokenType;
import sirgl.nodes.*;

import java.io.IOException;
import java.util.*;

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

    private Token nextToken;
    private boolean nextUsed = true;
    private TokenStream tokenStream;

    public LangParser(TokenStream tokenStream) {
        this.tokenStream = tokenStream;
    }


    private void assertSuitableFirstToken(Set<TokenType> firstTokens) throws ParsingException {
        if(nextToken == null) {
            throw new UnexpectedTokensEnd();
        }
        if(!firstTokens.contains(nextToken.getType())) {
            throw new UnexpectedTokenException(nextToken, new ArrayList<>(firstTokens));
        }
        nextUsed = true;
    }

    private void getNextToken() throws IOException {
        if(nextUsed) {
            nextToken = tokenStream.next();
            nextUsed = false;
        }
    }

    private void lookNextToken() throws IOException {
        nextToken = tokenStream.next();
        nextUsed = false;
    }

    private Node tryParsePrimary(Node parent) throws ParsingException, IOException {
        assertSuitableFirstToken(firstPrimaryTokens);
        switch (nextToken.getType()) {
            case True:
                return new Literal(parent, true);
            case False:
                return new Literal(parent, false);
            case Identifier:
                return new Identifier(parent, nextToken.getValue());
            case Lparen:
                ParenWrapper parenWrapper = new ParenWrapper(parent);
                getNextToken();
                Node expression = tryParseExpression(parenWrapper);
                getNextToken();
                if(nextToken.getType() != TokenType.Rparen) {
                    throw new UnexpectedTokenException(nextToken, Collections.singletonList(TokenType.Rparen));
                }
                parenWrapper.setValue(expression);
                return parenWrapper;
            default: // It could never happen!
                throw new UnexpectedTokenException(nextToken, new ArrayList<>(firstPrimaryTokens));
        }
    }

    private Node tryParseFactor(Node parent) throws IOException, ParsingException {
        assertSuitableFirstToken(firstFactorTokens);
        if(nextToken.getType() == TokenType.Not) {
            Not notNode = new Not(parent);
            getNextToken();
            Node primary = tryParsePrimary(notNode);
            notNode.setExpression(primary);
            return notNode;
        } else {
            return tryParsePrimary(parent);
        }
    }

    private Node tryParseExpression(Node parent) throws IOException, ParsingException {
        assertSuitableFirstToken(firstExpressionTokens);
        Node term = tryParseTerm(parent);
        lookNextToken();
        if(nextToken != null && nextToken.getType() == TokenType.Or) {
            Or orExpr = new Or(parent);
            orExpr.setLeft(term);
            term.setParent(orExpr);
            lookNextToken();
            Node right = tryParseTerm(orExpr);
            orExpr.setRight(right);
            return orExpr;
        }
        return term;
    }

    private Node tryParseTerm(Node parent) throws IOException, ParsingException {
        assertSuitableFirstToken(firstTermTokens);
        Node factor = tryParseFactor(parent);
        lookNextToken();
        if(nextToken != null && nextToken.getType() == TokenType.And) {
            And andExpr = new And(parent);
            andExpr.setLeft(factor);
            factor.setParent(andExpr);
            lookNextToken();
            Node expr = tryParseFactor(andExpr);
            andExpr.setRight(expr);
            return andExpr;
        }
        return factor;
    }

    @Override
    public Node parse() throws IOException, ParsingException {
        getNextToken();
        return tryParseExpression(null);
    }
}
