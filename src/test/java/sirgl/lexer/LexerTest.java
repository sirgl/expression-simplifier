package sirgl.lexer;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static sirgl.lexer.LexerTestUtils.makeLexer;
import static sirgl.lexer.LexerTestUtils.readUntilEof;

public class LexerTest {
    @Test
    public void spacingSkipped() throws Exception {
        assertThat(makeLexer("  (").next())
                .isEqualTo(new Token(TokenType.Lparen, 2, "("));
    }

    @Test
    public void nullOnEmptyInput() throws IOException {
        assertThat(makeLexer("").next()).isNull();
    }

    @Test
    public void nullOnSpacingInput() throws IOException {
        assertThat(makeLexer(" ").next()).isNull();
    }

    @Test
    public void tokenPositionsMatches() throws IOException {
        assertThat(readUntilEof("(NOT x)"))
                .containsExactly(
                        new Token(TokenType.Lparen, 0, "("),
                        new Token(TokenType.Not, 1, "NOT"),
                        new Token(TokenType.Identifier, 5, "x"),
                        new Token(TokenType.Rparen, 6, ")")
                );
    }

    @Test
    public void complicatedWithSpacing() throws Exception {
        assertThat(readUntilEof("(a AND b) OR (c AND NOT d)"))
                .containsExactly(
                        new Token(TokenType.Lparen, 0, "("),
                        new Token(TokenType.Identifier, 1, "a"),
                        new Token(TokenType.And, 3, "AND"),
                        new Token(TokenType.Identifier, 7, "b"),
                        new Token(TokenType.Rparen, 8, ")"),
                        new Token(TokenType.Or, 10, "OR"),
                        new Token(TokenType.Lparen, 13, "("),
                        new Token(TokenType.Identifier, 14, "c"),
                        new Token(TokenType.And, 16, "AND"),
                        new Token(TokenType.Not, 20, "NOT"),
                        new Token(TokenType.Identifier, 24, "d"),
                        new Token(TokenType.Rparen, 25, ")")
                );
    }

    @Test
    public void operatorsWithoutSpacing() throws Exception {
        assertThat(readUntilEof("ANDORNOT"))
                .containsExactly(
                        new Token(TokenType.And, 0, "AND"),
                        new Token(TokenType.Or, 3, "OR"),
                        new Token(TokenType.Not, 5, "NOT")
                );
    }

    @Test
    public void errorHandling() throws Exception {
        assertThat(readUntilEof("a BAD b"))
                .containsExactly(
                        new Token(TokenType.Identifier, 0, "a"),
                        new Token(TokenType.Error, 2, "BAD"),
                        new Token(TokenType.Identifier, 6, "b")
                );
    }



    @Test
    public void complicatedWithoutSpacing() throws Exception {
        assertThat(readUntilEof("(aANDb)OR(cANDNOTd)"))
                .containsExactly(
                        new Token(TokenType.Lparen, 0, "("),
                        new Token(TokenType.Identifier, 1, "a"),
                        new Token(TokenType.And, 2, "AND"),
                        new Token(TokenType.Identifier, 5, "b"),
                        new Token(TokenType.Rparen, 6, ")"),
                        new Token(TokenType.Or, 7, "OR"),
                        new Token(TokenType.Lparen, 9, "("),
                        new Token(TokenType.Identifier, 10, "c"),
                        new Token(TokenType.And, 11, "AND"),
                        new Token(TokenType.Not, 14, "NOT"),
                        new Token(TokenType.Identifier, 17, "d"),
                        new Token(TokenType.Rparen, 18, ")")
                );
    }
}
