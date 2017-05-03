package sirgl.nodes;

public class And extends BinaryExpr {
    public And(Node parent) {
        super(parent);
    }

    @Override
    public String toString() {
        return "And{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
