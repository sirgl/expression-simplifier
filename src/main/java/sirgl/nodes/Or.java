package sirgl.nodes;

import sirgl.nodes.chains.OperandChain;

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
        if (operandChain != null && operandChain.isActive()) {
            return operandChain;
        }
        operandChain = NodeUtils.buildMaxOperandChain(this);
        return operandChain;
    }

    public void setOperandChain(OperandChain<Or> operandChain) {
        this.operandChain = operandChain;
    }

    @Override
    public void invalidateChain() {
        if (operandChain != null) {
            operandChain.invalidate();
        }
    }

    @Override
    public boolean isUppest() {
        return getOperandChain().isTopOperand(this);
    }
}
