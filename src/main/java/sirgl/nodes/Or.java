package sirgl.nodes;

import sirgl.analysis.operands.OperandChain;

public class Or extends BinaryExpr {
    private OperandChain<Or> operandChain;

    public Or(Node parent) {
        super(parent);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitOr(this);
    }


    @Override
    public String toString() {
        return left + " OR " + right;
    }

    public OperandChain<Or> getOperandChain() {
        if(operandChain != null && operandChain.isActive()) {
            return operandChain;
        }
        operandChain = NodeUtils.getMaxOperandChain(this);
        return operandChain;
    }
}
