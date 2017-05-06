package sirgl;

import sirgl.analysis.ExpressionSimplifier;
import sirgl.analysis.rules.*;
import sirgl.analysis.rules.chains.AndChainRule;
import sirgl.analysis.rules.chains.OrChainRule;
import sirgl.console.ConsoleInterface;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        new ConsoleInterface(new ExpressionSimplifier(Arrays.asList(
                new NotLiteralRule(),
                new DoubleNotRule(),
                new DeMorganRule(),
                new LiteralAndExprRule(),
                new LiteralOrExprRule(),
                new ParenRemovingRule()
        ), Arrays.asList(
                new AndChainRule(),
                new OrChainRule()
        ))).run();
    }
}
