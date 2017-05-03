package sirgl.nodes;

import java.util.List;

public abstract class Node {
    private Node parent;

    public Node(Node parent) {
        this.parent = parent;
    }

    public abstract List<Node> getChildren();

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
