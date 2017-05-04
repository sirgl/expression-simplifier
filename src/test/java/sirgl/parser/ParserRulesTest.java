package sirgl.parser;

import org.junit.Test;
import sirgl.lexer.Token;
import sirgl.lexer.TokenType;
import sirgl.nodes.*;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static sirgl.parser.ParserTestUtils.parseStr;

public class ParserRulesTest {
    @Test
    public void parseIdentifier() throws Exception {
        assertThat(parseStr("a")).isEqualTo(new Identifier(null, "a"));
    }

    @Test
    public void parseTrue() throws Exception {
        assertThat(parseStr("TRUE")).isEqualTo(new Literal(null, true));
    }

    @Test
    public void parseFalse() throws Exception {
        assertThat(parseStr("FALSE")).isEqualTo(new Literal(null, false));
    }

    @Test
    public void parseParens() throws Exception {
        ParenWrapper parenWrapper = new ParenWrapper(null);
        Identifier identifier = new Identifier(parenWrapper, "a");
        parenWrapper.setValue(identifier);
        assertThat(parseStr("(a)")).isEqualTo(parenWrapper);
    }

    @Test
    public void parseFactor() throws Exception {
        Not notExpr = new Not(null);
        ParenWrapper parenWrapper = new ParenWrapper(null);
        Literal literal = new Literal(parenWrapper, true);
        parenWrapper.setValue(literal);
        notExpr.setExpression(parenWrapper);
        assertThat(parseStr("NOT (TRUE)")).isEqualTo(notExpr);
    }

    @Test
    public void parseTerm() throws Exception {
        And and1 = new And(null);
        and1.setRight(new Identifier(and1, "d"));
        And and2 = new And(and1);
        and1.setLeft(and2);
        and2.setRight(new Identifier(and2, "c"));
        And and3 = new And(and2);
        and2.setLeft(and3);
        and3.setRight(new Identifier(and3, "b"));
        and3.setLeft(new Identifier(and3, "a"));
        assertThat(parseStr("a AND b AND c AND d"))
                .isEqualTo(and1);
    }

    @Test
    public void parseTermInParens() throws Exception {
        And top = new And(null);
        top.setLeft(new Identifier(top, "a"));

        ParenWrapper parenWrapper = new ParenWrapper(top);
        And inner = new And(parenWrapper);
        top.setRight(inner);
        inner.setLeft(new Identifier(inner, "b"));
        inner.setRight(new Identifier(inner, "c"));

        parenWrapper.setValue(inner);
        top.setRight(parenWrapper);
        assertThat(parseStr("a AND (b AND c)"))
                .isEqualTo(top);
    }


    @Test
    public void parseExpr() throws Exception {
        Or or1 = new Or(null);
        or1.setRight(new Identifier(or1, "d"));
        Or or2 = new Or(or1);
        or1.setLeft(or2);
        or2.setRight(new Identifier(or2, "c"));
        Or or3 = new Or(or2);
        or2.setLeft(or3);
        or3.setRight(new Identifier(or3, "b"));
        or3.setLeft(new Identifier(or3, "a"));
        assertThat(parseStr("a OR b OR c OR d"))
                .isEqualTo(or1);
    }


    @Test
    public void complexExample() throws Exception {
        Or or = new Or(null);
        or.setRight(new Identifier(or, "c"));
        ParenWrapper outerParens = new ParenWrapper(or);
        or.setLeft(outerParens);
        And and1 = new And(outerParens);
        outerParens.setValue(and1);
        ParenWrapper parenWrapper = new ParenWrapper(and1);
        parenWrapper.setValue(new Identifier(parenWrapper, "b"));
        and1.setRight(parenWrapper);
        And and2 = new And(and1);
        and2.setLeft(new Identifier(and2, "a"));
        and2.setRight(new Literal(and2, true));
        and1.setLeft(and2);
        assertThat(parseStr("(a AND TRUE AND (b)) OR c")).isEqualTo(or);
    }

    @Test
    public void errorHandling() throws Exception {
        try {
            parseStr("AND");
        } catch (UnexpectedTokenException e) {
            assertThat(e).isEqualToComparingFieldByField(new UnexpectedTokenException(new Token(TokenType.And, 0, "AND"),
                    Arrays.asList(TokenType.Not, TokenType.False, TokenType.Identifier, TokenType.Lparen, TokenType.True)));
        }
    }

}
