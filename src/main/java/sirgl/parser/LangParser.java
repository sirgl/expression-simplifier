package sirgl.parser;

import sirgl.lexer.Token;
import sirgl.lexer.TokenStream;
import sirgl.lexer.TokenType;
import sirgl.nodes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

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
    private boolean lookAhead = false;
    private TokenStream tokenStream;

    public LangParser(TokenStream tokenStream) {
        this.tokenStream = tokenStream;
    }


    private void assertSuitableFirstToken(Set<TokenType> firstTokens) throws ParsingException {
        if (nextToken == null) {
            throw new UnexpectedTokensEnd(firstTokens);
        }
        if (!firstTokens.contains(nextToken.getType())) {
            throw new UnexpectedTokenException(nextToken, new ArrayList<>(firstTokens));
        }
    }

    private void lookAhead() throws IOException {
        if (!lookAhead) {
            nextToken = tokenStream.next();
            lookAhead = true;
        }
    }

    private void getNext() throws IOException {
        if (lookAhead) {
            lookAhead = false;
        } else {
            nextToken = tokenStream.next();
        }
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
                getNext();
                Node expression = tryParseExpression(parenWrapper);
                getNext();
                if (nextToken.getType() != TokenType.Rparen) {
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
        if (nextToken.getType() == TokenType.Not) {
            Not notNode = new Not(parent);
            getNext();
            Node primary = tryParsePrimary(notNode);
            notNode.setExpression(primary);
            return notNode;
        } else {
            return tryParsePrimary(parent);
        }
    }

    private Node tryParseExpression(Node parent) throws IOException, ParsingException {
        assertSuitableFirstToken(firstExpressionTokens);
        return tryParseSequence(this::tryParseTerm, parent, TokenType.Or, Or::new);
    }

    private Node tryParseTerm(Node parent) throws IOException, ParsingException {
        assertSuitableFirstToken(firstTermTokens);
        return tryParseSequence(this::tryParseFactor, parent, TokenType.And, And::new);
    }

    @Override
    public Node parse() throws IOException, ParsingException {
        getNext();
        return tryParseExpression(null);
    }

    private <T extends BinaryExpr> Node tryParseSequence(
            ParseFunction parseFunction,
            Node parent,
            TokenType operator,
            Function<Node, T> factory)
            throws IOException, ParsingException {
        Node first = parseFunction.parse(parent);
        Node left = first;
        T top = null;
        lookAhead();
        while (nextToken != null && nextToken.getType() == operator) {
            top = factory.apply(parent);
            top.setLeft(left);
            left.setParent(top);
            getNext(); // skip
            getNext(); // prepare
            Node right = parseFunction.parse(top);
            top.setRight(right);
            left = top;
            lookAhead();
        }
        if (top != null) {
            return top;
        }
        return first;
    }
}
