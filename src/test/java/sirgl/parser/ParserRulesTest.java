package sirgl.parser;

import org.junit.Test;
import sirgl.nodes.Identifier;

import static org.assertj.core.api.Assertions.assertThat;
import static sirgl.parser.ParserTestUtils.parseStr;

public class ParserRulesTest {
    @Test
    public void parseIdentifier() throws Exception {
        assertThat(parseStr("a")).isEqualTo(new Identifier(null, "a"));
    }
}
