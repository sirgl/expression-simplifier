package sirgl.nodes.chains;

import sirgl.nodes.BinaryExpr;
import sirgl.nodes.Node;

import java.util.List;

public class OperandChain<T extends BinaryExpr> {
    private List<Node> operands;
    private boolean isActive = true;
    private Class<T> operatorClass;

    public OperandChain(List<Node> operands, Class<T> operatorClass) {
        this.operands = operands;
        this.operatorClass = operatorClass;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<Node> getOperands() {
        return operands;
    }

    public void invalidate() {
        isActive = false;
    }

    public boolean isTopOperand(T node) {
        return node.getRight().equals(operands.get(operands.size() - 1));
    }
}
