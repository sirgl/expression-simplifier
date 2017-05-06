package sirgl.analysis.interceptors;

import sirgl.analysis.rules.ReplacementRule;
import sirgl.nodes.Node;

public interface ReplacementInterceptor {
    //Before replacement
    void intercept(Node from, Node to, ReplacementRule<?> rule);
}
