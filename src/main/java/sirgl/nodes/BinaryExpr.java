package sirgl.nodes;

public abstract class BinaryExpr extends Node {
    protected Node left;
    protected Node right;

    public BinaryExpr(Node parent, Node left, Node right) {
        super(parent);
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
