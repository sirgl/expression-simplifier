package sirgl.nodes;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static sirgl.parser.ParserTestUtils.parseStr;

public class ChildReplacingVisitorTest {
    @Test
    public void replacement() throws Exception {
        Node node = parseStr("(a AND b) OR c");
        Literal literal = new Literal(null, false);
        ((Or) node).getLeft().replaceTo(literal);
        Or or = new Or(null);
        or.setLeft(literal);
        or.setRight(new Identifier(or, "c"));
        assertThat(node).isEqualTo(or);
    }
}