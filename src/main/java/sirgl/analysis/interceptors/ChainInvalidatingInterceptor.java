package sirgl.analysis.interceptors;

import sirgl.analysis.rules.ReplacementRule;
import sirgl.nodes.And;
import sirgl.nodes.Node;
import sirgl.nodes.Or;

public class ChainInvalidatingInterceptor implements ReplacementInterceptor {
    @Override
    public void intercept(Node from, Node to, ReplacementRule<?> rule) {
        tryInvalidate(from);
        Node parent = from.getParent();
        tryInvalidate(parent);
    }

    private void tryInvalidate(Node node) {
        if(node != null) {
            if(node instanceof And) {
                ((And) node).getOperandChain().invalidate();
            } else if (node instanceof Or) {
                ((Or) node).getOperandChain().invalidate();
            }
        }
    }
}
