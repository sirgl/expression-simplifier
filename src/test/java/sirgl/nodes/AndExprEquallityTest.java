package sirgl.nodes;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static sirgl.parser.ParserTestUtils.parseStr;

public class AndExprEquallityTest {
    @Test
    public void equality() throws Exception {
        assertThat(parseStr("a AND b AND c")
                .equals(parseStr("a AND c AND b"))).isTrue();
    }
}
