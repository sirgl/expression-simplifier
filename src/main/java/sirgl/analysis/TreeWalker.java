package sirgl.analysis;

import sirgl.nodes.Node;
import sirgl.nodes.Root;

import java.util.List;
import java.util.function.Function;

public class TreeWalker {
    private final Function<Node, Node> preAction;
    private final Function<Node, Node> postAction;

    public TreeWalker(Function<Node, Node> preAction, Function<Node, Node> postAction) {
        this.preAction = preAction;
        this.postAction = postAction;
    }

    public TreeWalker(Function<Node, Node> preAction) {
        this(preAction, n -> null);
    }


    //true if changed
    public boolean walk(Node node) {
        Node newNode = preAction.apply(node);
        if(newNode != null && !(newNode instanceof Root)) {
            return true;
        }
        List<Node> children = node.getChildren();
        for (Node child : children) {
            if(walk(child)) { // child changed and this node need to be rewalked
                return walk(node);
            }
        }
        //all child are same
        Node anotherNew = postAction.apply(node);
        return anotherNew != null && !(anotherNew instanceof Root);
    }
}
