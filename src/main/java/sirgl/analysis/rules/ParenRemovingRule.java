package sirgl.analysis.rules;

import sirgl.nodes.*;

public class ParenRemovingRule implements ReplacementRule<ParenWrapper> {

    @Override
    public String getName() {
        return "Paren removing";
    }

    @Override
    public Class<ParenWrapper> getApplicableClass() {
        return ParenWrapper.class;
    }

    @Override
    public Node tryReplace(ParenWrapper node) {
        Node outside = node.getParent();
        if (outside == null) {
            return null;
        }
        Node inside = node.getValue();
        if (outside instanceof Or && inside instanceof And ||
                outside instanceof Not && inside instanceof Not) {
            return null;
        }
        return inside;
    }
}
