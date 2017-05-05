package sirgl.nodes;

import sirgl.nodes.chains.OperandChain;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class NodeUtils {
    public static ParenWrapper wrap(Node data, Node parent) {
        ParenWrapper parenWrapper = new ParenWrapper(parent);
        parenWrapper.setValue(data);
        return parenWrapper;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BinaryExpr> OperandChain<T> buildMaxOperandChain(T node) {
        Class<T> nodeClass = (Class<T>) node.getClass();
        List<Node> upper = getUpperOperandChain(node, nodeClass);
        List<Node> lower = getLowerOperandChain(node, nodeClass);
        Collections.reverse(lower);
        lower.addAll(upper);
        OperandChain<T> operandChain = new OperandChain<>(lower, nodeClass);
        for (Node operand : lower) {
            if (nodeClass.equals(And.class)) {
                ((And) operand.getParent()).setOperandChain((OperandChain<And>) operandChain);
            } else if (nodeClass.equals(Or.class)) {
                ((Or) operand.getParent()).setOperandChain((OperandChain<Or>) operandChain);
            } else {
                throw new UnsupportedOperationException("This binary expression is not supported");
            }
        }
        return operandChain;
    }

    private static List<Node> getUpperOperandChain(BinaryExpr node, Class<? extends BinaryExpr> nodeClass) {
        List<Node> operands = new ArrayList<>();
        Node parent = node.getParent();
        if (parent == null || !parent.getClass().equals(nodeClass)) {
            return operands;
        }
        BinaryExpr current = (BinaryExpr) node.getParent();
        Node next = current.getParent();
        while (next != null && next.getClass().equals(nodeClass)) {
            BinaryExpr binaryExprNext = (BinaryExpr) next;
            operands.add(binaryExprNext.getRight());
            current = binaryExprNext;
            next = current.getParent();
        }
        return operands;
    }

    private static List<Node> getLowerOperandChain(BinaryExpr node, Class<? extends BinaryExpr> nodeClass) {
        List<Node> operands = new ArrayList<>();
        BinaryExpr current = node;
        operands.add(current.getRight());
        Node next = current.getLeft();
        while (next != null && next.getClass().equals(nodeClass)) {
            BinaryExpr binaryExprNext = (BinaryExpr) next;
            operands.add(binaryExprNext.getRight());
            current = binaryExprNext;
            next = current.getLeft();
        }
        operands.add(current.getLeft());
        return operands;
    }

    public static Set<Node> getLowerOperands(BinaryExpr node) {
        Class<? extends BinaryExpr> nodeClass = node.getClass();
        List<Node> lower = getLowerOperandChain(node, nodeClass);
        return new HashSet<>(lower);
    }

    public static Node reduceNodes(List<Node> nodes, BiFunction<Node, Node, BinaryExpr> reducer) {
        if (nodes.isEmpty()) {
            return null;
        }
        Node first = nodes.get(0);
        if (nodes.size() == 1) {
            return first;
        }
        Node second = nodes.get(1);
        BinaryExpr reduced = reducer.apply(first, second);
        int current = 2;
        while (current < nodes.size()) {
            reduced = reducer.apply(reduced, nodes.get(current));
            current++;
        }
        return reduced;
    }

    public static Node reduceNodes(List<Node> nodes, Function<Node, BinaryExpr> factory) {
        return reduceNodes(nodes, (node1, node2) -> {
            BinaryExpr expr = factory.apply(null);
            expr.setLeft(node1);
            expr.setRight(node2);
            return expr;
        });
    }
}
