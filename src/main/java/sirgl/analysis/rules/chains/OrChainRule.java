package sirgl.analysis.rules.chains;

import sirgl.nodes.Literal;
import sirgl.nodes.Node;
import sirgl.nodes.Or;
import sirgl.nodes.chains.OperandChain;

import java.util.Set;
import java.util.function.Function;

public class OrChainRule extends ChainOperationsRule<Or> {
    @Override
    public String getName() {
        return "Or chain";
    }

    @Override
    public Class<Or> getApplicableClass() {
        return Or.class;
    }

    @Override
    public Node onIntersects(Set<Node> positive, Set<Node> negative, Set<Node> intersection) {
        return new Literal(null, true);
    }

    @Override
    public OperandChain<Or> getChain(Or node) {
        return node.getOperandChain();
    }

    @Override
    public Function<Node, Or> getFactory() {
        return Or::new;
    }
}
