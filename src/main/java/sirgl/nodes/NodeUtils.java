package sirgl.nodes;

import sirgl.nodes.operands.OperandChain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class NodeUtils {
    public static ParenWrapper wrap(Node data, Node parent) {
        ParenWrapper parenWrapper = new ParenWrapper(parent);
        parenWrapper.setValue(data);
        return parenWrapper;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BinaryExpr> OperandChain<T> getMaxOperandChain(T node) {
        Class<T> nodeClass = (Class<T>) node.getClass();
        List<Node> upper = getOperandChain(node, nodeClass, Node::getParent);
        List<Node> lower = getOperandChain(node, nodeClass, BinaryExpr::getLeft);
        Collections.reverse(lower);
        lower.add(node);
        lower.addAll(upper);
        return new OperandChain<>(lower, nodeClass);
    }

    private static List<Node> getOperandChain(BinaryExpr node, Class<? extends BinaryExpr> nodeClass, Function<BinaryExpr, Node> getNext) {
        List<Node> operands = new ArrayList<>();
        BinaryExpr current = node;
        Node next = getNext.apply(current);
        while (next != null && next.getClass().equals(nodeClass)) {
            operands.add(next);
            current = (BinaryExpr) next;
            next = getNext.apply(current);
        }
        return operands;
    }
}
