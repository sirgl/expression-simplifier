package sirgl;

import sirgl.analysis.ExpressionSimplifier;
import sirgl.analysis.rules.*;
import sirgl.console.ConsoleInterface;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        new ConsoleInterface(new ExpressionSimplifier(Arrays.asList(
                new NotLiteralRule(),
                new LiteralOrExprRule(),
                new LiteralAndExprRule(),
                new DoubleNotRule(),
                new DeMorganRule()
        ))).run();
    }
}
