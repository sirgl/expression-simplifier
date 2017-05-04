package sirgl.nodes;

public interface NodeVisitor {
    void visitAnd(And and);
    void visitIdentifier(Identifier identifier);
    void visitLiteral(Literal literal);
    void visitNot(Not not);
    void visitOr(Or or);
    void visitParenWrapper(ParenWrapper parenWrapper);
}
