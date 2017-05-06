package sirgl.analysis.interceptors;

import sirgl.analysis.rules.ReplacementRule;
import sirgl.nodes.Node;

import java.util.HashMap;
import java.util.Map;

public class StatisticsInterceptor implements ReplacementInterceptor {
    private Map<ReplacementRule<?>, Integer> ruleStats = new HashMap<>();

    @Override
    public void intercept(Node from, Node to, ReplacementRule<?> rule) {
        ruleStats.compute(rule, (replacementRule, count) -> (replacementRule == null || count == null) ? 1 : count + 1);
    }

    public Map<ReplacementRule<?>, Integer> getRuleStats() {
        return ruleStats;
    }
}
