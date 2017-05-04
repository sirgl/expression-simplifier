package sirgl.analysis.rules;

import sirgl.nodes.*;

import java.util.function.Function;

public class DeMorganRule implements ReplacementRule<Not> {
    @Override
    public String getName() {
        return "De Morgan's laws";
    }

    @Override
    public Class<Not> getApplicableClass() {
        return Not.class;
    }

    @Override
    public Node tryReplace(Not node) {
        Node expression = node.getExpression();
        if(expression.getClass().equals(ParenWrapper.class)) {
            Node value = ((ParenWrapper) expression).getValue();
            if(value.getClass().equals(And.class)) {
                And and = (And) value;
                return invert(and, Or::new);
            } else if (value.getClass().equals(Or.class)) {
                Or or = (Or) value;
                return invert(or, And::new);
            }
        }
        return null;
    }

    private BinaryExpr invert(BinaryExpr expr, Function<Node, BinaryExpr> factory) {
        Node left = expr.getLeft();
        Node right = expr.getRight();
        BinaryExpr invertedExpr = factory.apply(null);

        Not notLeft = new Not(invertedExpr);
        Not notRight = new Not(invertedExpr);
        ParenWrapper leftWrapper = NodeUtils.wrap(left, notLeft);
        ParenWrapper rightWrapper = NodeUtils.wrap(right, notRight);
        notLeft.setExpression(leftWrapper);
        notRight.setExpression(rightWrapper);
        invertedExpr.setLeft(notLeft);
        invertedExpr.setRight(notRight);
        return invertedExpr;
    }
}
