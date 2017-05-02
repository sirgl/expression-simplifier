package sirgl.parser;

import sirgl.lexer.TokenStream;
import sirgl.nodes.Node;

import java.io.IOException;

public interface Parser {
    Node parse(TokenStream tokenStream) throws IOException;
}
