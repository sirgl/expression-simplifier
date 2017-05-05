package sirgl.nodes;

import sirgl.analysis.operands.OperandChain;

import java.util.Arrays;
import java.util.List;

public abstract class BinaryExpr extends Node {
    protected Node left;
    protected Node right;

    public BinaryExpr(Node parent) {
        super(parent);
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node left) {
        this.left = left;
        left.setParent(this);
    }

    public void setRight(Node right) {
        this.right = right;
        right.setParent(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryExpr that = (BinaryExpr) o;

        if (!left.equals(that.left)) return false;
        return right.equals(that.right);
    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }

    @Override
    public List<Node> getChildren() {
        return Arrays.asList(left, right);
    }
}
