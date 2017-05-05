package sirgl.analysis.rules;

import sirgl.nodes.Node;

public interface ReplacementRule<T extends Node> {
    String getName();

    Class<T> getApplicableClass();

    Node tryReplace(T node);
}
