package sirgl.nodes;

import org.junit.Test;
import sirgl.nodes.chains.OperandChain;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static sirgl.parser.ParserTestUtils.parseStr;

public class NodeUtilsTest {
    @Test
    public void chainTest() throws Exception {
        Literal l = new Literal(null, false);
        Identifier a1 = new Identifier(null, "a1");
        Identifier a2 = new Identifier(null, "a2");
        Identifier a3 = new Identifier(null, "a3");
        Node node = NodeUtils.reduceNodes(Arrays.asList(a1, l, a2, a3), And::new);
        assertThat(node.toString())
                .isEqualTo("a1 AND FALSE AND a2 AND a3");
    }

    @Test
    public void buildMaxOperandChain() throws Exception {
        And and = (And) parseStr("NOT (a) AND NOT (b)");
        OperandChain<And> operandChain = and.getOperandChain();
        List<Node> operands = operandChain.getOperands();
        assertThat(operands).hasSize(2);
        assertThat(operands)
                .containsOnly(
                        parseStr("NOT (a)"),
                        parseStr("NOT (b)")
                );
    }
}