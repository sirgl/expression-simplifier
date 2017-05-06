package sirgl.analysis.errors;

import sirgl.analysis.AnalysisError;
import sirgl.analysis.ErrorHighlight;
import sirgl.lexer.TokenType;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UnexpectedEndOfTokens implements AnalysisError {
    private Set<TokenType> expectedTokens;
    private int lastPosition;

    public UnexpectedEndOfTokens(Set<TokenType> expectedTokens, int lastPosition) {
        this.expectedTokens = expectedTokens;
        this.lastPosition = lastPosition;
    }

    @Override
    public String getMessage() {
        return "Unexpected end of expression (wanted: " +
                expectedTokens.stream().map(Enum::toString).collect(Collectors.joining(", ")) + ")";
    }

    @Override
    public List<ErrorHighlight> getHighlights() {
        return Collections.singletonList(new ErrorHighlight(lastPosition, lastPosition));
    }
}
