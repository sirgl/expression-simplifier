package sirgl.nodes;

import java.util.Collections;
import java.util.List;

public class Literal extends Node {
    private boolean value;

    public Literal(Node parent, boolean value) {
        super(parent);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Literal literal = (Literal) o;

        return value == literal.value;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }


    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitLiteral(this);
    }

    @Override
    public String toString() {
        return value ? "TRUE" : "FALSE";
    }
}
