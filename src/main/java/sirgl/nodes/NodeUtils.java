package sirgl.nodes;

public class NodeUtils {
    public static ParenWrapper wrap(Node data, Node parent) {
        ParenWrapper parenWrapper = new ParenWrapper(parent);
        parenWrapper.setValue(data);
        return parenWrapper;
    }
}
