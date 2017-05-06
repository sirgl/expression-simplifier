package sirgl.nodes;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static sirgl.parser.ParserTestUtils.parseStr;

public class ChainTest {
    @Test
    public void isTop() throws Exception {
        And and = (And) parseStr("a AND b AND c");
        assertThat(and.isUppest()).isTrue();
        assertThat(((And) and.getLeft()).isUppest()).isFalse();
    }
}
