package sirgl.analysis.rules;

import sirgl.nodes.And;
import sirgl.nodes.Literal;
import sirgl.nodes.Node;

public class LiteralAndExprRule implements ReplacementRule<And> {
    @Override
    public String getName() {
        return "Literal and expression";
    }

    @Override
    public Class<And> getApplicableClass() {
        return And.class;
    }

    @Override
    public Node tryReplace(And node) {
        if(node.getLeft().getClass().equals(Literal.class)) {
            Literal literal = (Literal) node.getLeft();
            if(literal.getValue()) {
                return node.getRight();
            } else {
                return literal;
            }
        } else if(node.getRight().getClass().equals(Literal.class)) {
            Literal literal = (Literal) node.getRight();
            boolean literalValue = literal.getValue();
            if(literalValue) {
                return node.getLeft();
            } else {
                return literal;
            }
        }
        return null;
    }
}
