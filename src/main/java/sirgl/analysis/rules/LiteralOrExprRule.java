package sirgl.analysis.rules;

import sirgl.nodes.Literal;
import sirgl.nodes.Node;
import sirgl.nodes.Or;

public class LiteralOrExprRule implements ReplacementRule<Or> {
    @Override
    public String getName() {
        return "Literal or expression";
    }

    @Override
    public Class<Or> getApplicableClass() {
        return Or.class;
    }

    @Override
    public Node tryReplace(Or node) {
        if(node.getLeft().getClass().equals(Literal.class)) {
            Literal literal = (Literal) node.getLeft();
            if(literal.getValue()) {
                return literal;
            } else {
                return node.getRight();
            }
        } else if(node.getRight().getClass().equals(Literal.class)) {
            Literal literal = (Literal) node.getRight();
            boolean literalValue = literal.getValue();
            if(literalValue) {
                return literal;
            } else {
                return node.getLeft();
            }
        }
        return null;
    }
}
