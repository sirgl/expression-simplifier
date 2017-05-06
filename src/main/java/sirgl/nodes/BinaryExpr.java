package sirgl.nodes;

import java.util.Arrays;
import java.util.List;

import static sirgl.nodes.NodeUtils.getLowerOperands;

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
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryExpr that = (BinaryExpr) o;

        return getLowerOperands(this).equals(getLowerOperands(that));
    }

    @Override
    public int hashCode() {
        return getLowerOperands(this).hashCode();
    }

    @Override
    public List<Node> getChildren() {
        return Arrays.asList(left, right);
    }

    public abstract void invalidateChain();

    public abstract boolean isUppest();


}
