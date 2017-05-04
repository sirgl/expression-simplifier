package sirgl.analysis.rules;

import sirgl.nodes.Literal;
import sirgl.nodes.Node;
import sirgl.nodes.Not;

public class NotLiteralReplacementRule implements ReplacementRule<Not> {
    @Override
    public String getName() {
        return "Not literal";
    }

    @Override
    public Class<Not> getApplicableClass() {
        return Not.class;
    }

    @Override
    public Node tryReplace(Not node) {
        Node expression = node.getExpression();
        if(expression.getClass().equals(Literal.class)) {
            Literal literal = (Literal) expression;
            return new Literal(null, !literal.getValue());
        }
        return null;
    }
}
