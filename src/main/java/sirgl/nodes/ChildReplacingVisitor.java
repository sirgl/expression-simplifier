package sirgl.nodes;

public class ChildReplacingVisitor implements NodeVisitor {
    private final Node replaceFrom;
    private final Node replaceTo;

    public ChildReplacingVisitor(Node replaceFrom, Node replaceTo) {
        this.replaceFrom = replaceFrom;
        this.replaceTo = replaceTo;
    }

    @Override
    public void visitIdentifier(Identifier identifier) {
    }

    @Override
    public void visitLiteral(Literal literal) {
    }

    @Override
    public void visitRoot(Root root) {
        root.setValue(replaceTo);
        replaceTo.setParent(root);
    }

    @Override
    public void visitNot(Not not) {
        not.setExpression(replaceTo);
        replaceTo.setParent(not);
    }

    @Override
    public void visitParenWrapper(ParenWrapper parenWrapper) {
        parenWrapper.setValue(replaceTo);
        replaceTo.setParent(parenWrapper);
    }

    @Override
    public void visitBinary(BinaryExpr expr) {
        if (expr.getLeft().equals(replaceFrom)) {
            expr.setLeft(replaceTo);
            replaceTo.setParent(expr);
        } else {
            expr.setRight(replaceTo);
            replaceTo.setParent(expr);
        }
    }
}
