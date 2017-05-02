package sirgl.nodes;

public class Identifier extends Node {
    private String name;

    public Identifier(Node parent, String name) {
        super(parent);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
