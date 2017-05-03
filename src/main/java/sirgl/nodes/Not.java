package sirgl.nodes;

import java.util.Collections;
import java.util.List;

public class Not extends Node {
    private Node expression;

    public Not(Node parent) {
        super(parent);
    }

    public void setExpression(Node expression) {
        this.expression = expression;
    }

    public Node getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Not not = (Not) o;

        return expression.equals(not.expression);
    }

    @Override
    public int hashCode() {
        return expression.hashCode();
    }

    @Override
    public List<Node> getChildren() {
        return Collections.singletonList(expression);
    }

    @Override
    public String toString() {
        return "Not{" +
                "expression=" + expression +
                '}';
    }
}
