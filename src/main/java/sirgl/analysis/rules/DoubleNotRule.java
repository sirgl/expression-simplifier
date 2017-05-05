package sirgl.analysis.rules;

import sirgl.nodes.Node;
import sirgl.nodes.Not;
import sirgl.nodes.ParenWrapper;

public class DoubleNotRule implements ReplacementRule<Not> {

    @Override
    public String getName() {
        return "Double not";
    }

    @Override
    public Class<Not> getApplicableClass() {
        return Not.class;
    }

    @Override
    public Node tryReplace(Not node) {
        Node expression = node.getExpression();
        if (expression.getClass().equals(ParenWrapper.class)) {
            ParenWrapper parenWrapper = (ParenWrapper) expression;
            Node value = parenWrapper.getValue();
            if (value instanceof Not) {
                return ((Not) value).getExpression();
            }
        }
        return null;
    }
}
