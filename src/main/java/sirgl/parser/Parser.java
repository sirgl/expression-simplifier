package sirgl.parser;

import sirgl.nodes.Node;

import java.io.IOException;

public interface Parser {
    Node parse() throws IOException, ParsingException;
}
