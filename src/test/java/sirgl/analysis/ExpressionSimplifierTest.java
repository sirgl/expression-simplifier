package sirgl.analysis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sirgl.analysis.rules.NotLiteralReplacementRule;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ExpressionSimplifierTest {
    private ExpressionSimplifier simplifier = new ExpressionSimplifier(Arrays.asList(
            new NotLiteralReplacementRule()
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
        });
    }
}