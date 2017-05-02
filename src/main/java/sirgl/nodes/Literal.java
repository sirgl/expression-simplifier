package sirgl.nodes;

public class Literal extends Node {
    private boolean value;

    public Literal(Node parent, boolean value) {
        super(parent);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
