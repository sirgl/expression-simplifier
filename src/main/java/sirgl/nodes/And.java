package sirgl.nodes;

import sirgl.nodes.chains.OperandChain;

public class And extends BinaryExpr {
    private OperandChain<And> operandChain;

    public And(Node parent) {
        super(parent);
    }

    @Override
    void invalidateChain() {
        if(operandChain != null) {
            operandChain.invalidate();
        }
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitAnd(this);
    }

    @Override
    public String toString() {
        return left + " AND " + right;
    }

    public OperandChain<And> getOperandChain() {
        if(operandChain != null && operandChain.isActive()) {
            return operandChain;
        }
        operandChain = NodeUtils.buildMaxOperandChain(this);
        return operandChain;
    }

    public void setOperandChain(OperandChain<And> operandChain) {
        this.operandChain = operandChain;
    }
}
