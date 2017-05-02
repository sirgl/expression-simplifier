package sirgl.nodes;

import java.util.List;

public abstract class Node {
    private Node parent;
    private List<Node> children;

    public Node(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    protected void addChild(Node child) {
        children.add(child);
    }
}
