package sirgl.parser;

import sirgl.nodes.Node;

import java.io.IOException;

public interface ParseFunction {
    Node parse(Node parent) throws IOException, ParsingException;
}
