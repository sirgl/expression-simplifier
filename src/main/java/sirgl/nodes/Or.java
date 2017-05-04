package sirgl.nodes;

public class Or extends BinaryExpr {
    public Or(Node parent) {
        super(parent);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitOr(this);
    }


    @Override
    public String toString() {
        return "Or{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
