package sirgl.analysis;

import sirgl.nodes.Node;

import java.util.List;
import java.util.function.Function;

public class TreeWalker {
    private final Function<Node, Node> action;

    public TreeWalker(Function<Node, Node> action) {
        this.action = action;
    }

    public void walk(Node node) {
        Node newNode = action.apply(node);
        List<Node> children;
        if(newNode != null) {
            if(newNode.getParent() != null) {
                walk(newNode.getParent());
            } else {
                walk(newNode);
            }
        } else {
            children = node.getChildren();
            for (Node child : children) {
                walk(child);
            }
        }
    }
}
