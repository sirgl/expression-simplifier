package sirgl.nodes;

import java.util.Collections;
import java.util.List;

public class ParenWrapper extends Node {
    private Node value;

    public ParenWrapper(Node parent) {
        super(parent);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitParenWrapper(this);
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        this.value = value;
        value.setParent(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParenWrapper that = (ParenWrapper) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public List<Node> getChildren() {
        return  Collections.singletonList(value);
    }

    @Override
    public String toString() {
        return "(" + value + ")";
    }
}
