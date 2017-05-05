package sirgl.analysis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sirgl.analysis.rules.*;
import sirgl.analysis.rules.chains.AndChainRule;
import sirgl.analysis.rules.chains.OrChainRule;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ExpressionSimplifierTest {
    private ExpressionSimplifier simplifier = new ExpressionSimplifier(Arrays.asList(
            new NotLiteralRule(),
            new DoubleNotRule(),
            new DeMorganRule(),
            new LiteralAndExprRule(),
            new LiteralOrExprRule(),
            new AndChainRule(),
            new OrChainRule()
    ));

    private String before;
    private String expected;

    public ExpressionSimplifierTest(String before, String expected) {
        this.before = before;
        this.expected = expected;
    }

    @Test
    public void simplify() throws Exception {
        SimplificationResult simplify = simplifier.simplify(before);
        assertThat(simplify.getResult().toString()).isEqualTo(expected);
    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"NOT TRUE", "FALSE"},
                {"NOT FALSE", "TRUE"},
                {"NOT (NOT a)", "a"},
                {"NOT (NOT TRUE)", "TRUE"},
                {"NOT (a AND b)", "NOT (a) OR NOT (b)"},
                {"NOT (a OR b)", "NOT (a) AND NOT (b)"},
                {"NOT (a OR NOT b)", "NOT (a) AND b"},
                {"NOT FALSE AND FALSE", "FALSE"},
                {"NOT FALSE OR FALSE", "TRUE"},
                {"a AND TRUE", "a"},
                {"a AND NOT a", "FALSE"},
                {"a AND b AND NOT a", "FALSE"},
                {"a AND a", "a"},
                {"a AND b AND a", "a AND b"},
                {"a OR b OR a", "a OR b"},
                {"a OR a", "a"},
                {"a OR NOT a", "TRUE"},
        });
    }
}