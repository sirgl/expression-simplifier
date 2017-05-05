package sirgl.analysis.rules;

import sirgl.nodes.And;
import sirgl.nodes.Literal;
import sirgl.nodes.Node;
import sirgl.nodes.Not;
import sirgl.nodes.operands.OperandChain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AndChainRule implements ReplacementRule<And> {
    @Override
    public String getName() {
        return "And chain";
    }

    @Override
    public Class<And> getApplicableClass() {
        return And.class;
    }

    @Override
    public Node tryReplace(And node) {
        OperandChain<And> chain = node.getOperandChain();
        List<Node> operands = chain.getOperands();
        Set<Node> positiveNodes = new HashSet<>();
        Set<Node> negativeNodes = new HashSet<>();
        boolean containsDuplicates = false;
        for (Node operand : operands) {
            if(operand.getClass().equals(Not.class)) {
                if(!negativeNodes.add(((Not) operand).getExpression())) {
                    containsDuplicates = true;
                }
            } else {
                if(!positiveNodes.add(operand)) {
                    containsDuplicates = true;
                }
            }
        }
        Set<Node> intersection = new HashSet<>(positiveNodes);
        intersection.retainAll(negativeNodes);
        //TODO invalidate chain
        if(!intersection.isEmpty()) { // a and not a
            return new Literal(null, true);
        }
        if(containsDuplicates) {
            ArrayList<Node> uniqueOperands = new ArrayList<>();
            uniqueOperands.addAll(positiveNodes);
            for (Node negativeNode : negativeNodes) {
                Not not = new Not(null);
                not.setExpression(negativeNode);
                uniqueOperands.add(not);
            }
            if(uniqueOperands.size() == 1) {
                return uniqueOperands.get(0);
            }
        }
        return null;
    }
}
