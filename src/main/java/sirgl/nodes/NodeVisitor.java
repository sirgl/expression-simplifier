package sirgl.nodes;

public interface NodeVisitor {
    void visitIdentifier(Identifier identifier);

    void visitLiteral(Literal literal);

    void visitNot(Not not);

    void visitParenWrapper(ParenWrapper parenWrapper);

    void visitRoot(Root root);

    default void visitOr(Or or) {
        visitBinary(or);
    }

    default void visitAnd(And and) {
        visitBinary(and);
    }

    default void visitBinary(BinaryExpr expr) {
    }
}
