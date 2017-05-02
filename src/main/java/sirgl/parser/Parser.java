package sirgl.parser;

import sirgl.lexer.TokenStream;
import sirgl.nodes.Node;

public interface Parser {
    Node parse(TokenStream tokenStream);
}
