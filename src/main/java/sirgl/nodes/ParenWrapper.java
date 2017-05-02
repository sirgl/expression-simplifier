package sirgl.nodes;

public class ParenWrapper extends Node {
    private Node value;

    public ParenWrapper(Node parent) {
        super(parent);
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        this.value = value;
    }
}
