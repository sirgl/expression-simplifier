package sirgl.nodes;

public class And extends BinaryExpr {
    public And(Node parent) {
        super(parent);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitAnd(this);
    }

    @Override
    public String toString() {
        return left + " AND " + right;
    }
}
