package sirgl.analysis;

import sirgl.nodes.Node;
import sirgl.nodes.ParenWrapper;

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
        if (newNode != null) {
            Node parent = newNode.getParent();
            if (parent != null) {
                if (parent instanceof ParenWrapper) {
                    walkParenWrapperParent(parent);
                } else {
                    walk(parent);
                }
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

    private void walkParenWrapperParent(Node parent) {
        Node parenParent = parent.getParent();
        if (parenParent != null) {
            walk(parenParent);
        } else {
            walk(parent);
        }
    }
}
