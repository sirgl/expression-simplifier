package sirgl.analysis.rules;

import sirgl.nodes.Node;

public interface ReplacementRule <T extends Node> {
    String getName();
    Class<T> getAppliableClass();
    Node tryReplace(T node);
}
