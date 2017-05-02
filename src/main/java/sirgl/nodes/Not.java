package sirgl.nodes;

public class Not extends Node {
    private final Node expression;

    public Not(Node parent, Node expression) {
        super(parent);
        this.expression = expression;
    }
}
