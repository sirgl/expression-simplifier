package sirgl.analysis.rules.chains;

import sirgl.analysis.rules.ReplacementRule;
import sirgl.nodes.BinaryExpr;
import sirgl.nodes.Node;
import sirgl.nodes.NodeUtils;
import sirgl.nodes.Not;
import sirgl.nodes.chains.OperandChain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public abstract class ChainOperationsRule<T extends BinaryExpr> implements ReplacementRule<T> {
    @Override
    public Node tryReplace(T node) {
        if(!node.isUppest()) {
            return null;
        }
        OperandChain<T> chain = getChain(node);
        List<Node> operands = chain.getOperands();
        Set<Node> positiveNodes = new HashSet<>();
        Set<Node> negativeNodes = new HashSet<>();
        boolean containsDuplicates = false;
        for (Node operand : operands) {
            if (operand.getClass().equals(Not.class)) {
                if (!negativeNodes.add(((Not) operand).getExpression())) {
                    containsDuplicates = true;
                }
            } else {
                if (!positiveNodes.add(operand)) {
                    containsDuplicates = true;
                }
            }
        }
        Set<Node> intersection = new HashSet<>(positiveNodes);
        intersection.retainAll(negativeNodes);
        if (!intersection.isEmpty()) { // a and not a
            return onIntersects(positiveNodes, negativeNodes, intersection);
        }
        if (containsDuplicates) {
            ArrayList<Node> uniqueOperands = new ArrayList<>();
            uniqueOperands.addAll(positiveNodes);
            for (Node negativeNode : negativeNodes) {
                Not not = new Not(null);
                not.setExpression(negativeNode);
                uniqueOperands.add(not);
            }
            return NodeUtils.reduceNodes(uniqueOperands, n -> getFactory().apply(n));
        }
        return null;
    }

    public abstract Node onIntersects(Set<Node> positive, Set<Node> negative, Set<Node> intersection);

    public abstract OperandChain<T> getChain(T node);

    public abstract Function<Node, T> getFactory();
}
