package sirgl.nodes.operands;

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
}
