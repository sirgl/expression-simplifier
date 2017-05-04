package sirgl.nodes;

import java.util.Collections;
import java.util.List;

//Meta Node using for simplification
public class Root extends Node {
    private Node value;

    public Root(Node value) {
        super(null);
        this.value = value;
        value.setParent(this);
    }

    @Override
    public List<Node> getChildren() {
        return Collections.singletonList(value);
    }

    @Override
    public void accept(NodeVisitor visitor) {
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        this.value = value;
        value.setParent(this);
    }

    @Override
    public void replace(Node replacement) {
        throw new UnsupportedOperationException("Root can't be replaced");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
