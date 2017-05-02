package sirgl.lexer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static sirgl.lexer.LexerTestUtils.makeTokenStream;

@RunWith(Parameterized.class)
public class TokenStreamSingleTokenTest {
    private final String str;
    private TokenType tokenType;

    public TokenStreamSingleTokenTest(String str, TokenType tokenType) {
        this.str = str;
        this.tokenType = tokenType;
    }

    @Test
    public void singleToken() throws IOException {
        assertThat(makeTokenStream(str).next().getType())
                .isEqualTo(tokenType);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(", TokenType.Lparen},
                {")", TokenType.Rparen},
                {"NOT", TokenType.Not},
                {"OR", TokenType.Or},
                {"test", TokenType.Identifier},
                {"BAD", TokenType.Error},
                {"TRUE", TokenType.True},
                {"FALSE", TokenType.False},
        });
    }
}
