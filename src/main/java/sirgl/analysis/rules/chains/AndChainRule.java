package sirgl.analysis.rules.chains;

import sirgl.nodes.And;
import sirgl.nodes.Literal;
import sirgl.nodes.Node;
import sirgl.nodes.chains.OperandChain;

import java.util.Set;
import java.util.function.Function;

public class AndChainRule extends ChainOperationsRule<And> {
    @Override
    public String getName() {
        return "And chain";
    }

    @Override
    public Class<And> getApplicableClass() {
        return And.class;
    }


    @Override
    public Node onIntersects(Set<Node> positive, Set<Node> negative, Set<Node> intersection) {
        return new Literal(null, false);
    }

    @Override
    public OperandChain<And> getChain(And node) {
        return node.getOperandChain();
    }

    @Override
    public Function<Node, And> getFactory() {
        return And::new;
    }
}
