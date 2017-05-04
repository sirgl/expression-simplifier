package sirgl.analysis;

import org.junit.Test;
import sirgl.nodes.*;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static sirgl.parser.ParserTestUtils.parseStr;

public class TreeWalkerTest {
    @Test
    public void walkAllNodes() throws Exception {
        Node node = parseStr("(a AND b) OR c");
        CounterHolder holder = new CounterHolder();
        Function<Node, Node> couningAction = node1 -> {
            holder.count++;
            return null;
        };
        new TreeWalker(couningAction).walk(node);
        assertThat(holder.count).isEqualTo(6);
    }

    private static class CounterHolder {
        int count;
    }

    @Test
    public void replacedNodeIsWalked() throws Exception {
        Node node = parseStr("(a AND b) OR c");
        Set<Class<?>> classes = new HashSet<>();
        new TreeWalker(node1 -> {
            if(node1 == null) {
                return null;
            }
            if(node1.getClass().equals(ParenWrapper.class)) {
                return new Literal(null, false);
            }
            classes.add(node1.getClass());
            return null;
        }).walk(node);
        assertThat(classes).containsOnly(Or.class, Literal.class, Identifier.class);
    }
}
