package sirgl.analysis.errors;

import sirgl.analysis.AnalysisError;
import sirgl.analysis.ErrorHighlight;
import sirgl.lexer.Token;
import sirgl.lexer.TokenType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UnexpectedToken implements AnalysisError {
    private final Token token;
    private final List<TokenType> possibleAlternatives;

    public UnexpectedToken(Token token, List<TokenType> possibleAlternatives) {
        this.token = token;
        this.possibleAlternatives = possibleAlternatives;
    }

    @Override
    public String getMessage() {
        return "Unexpected token (wanted: " +
                possibleAlternatives.stream().map(Enum::toString).collect(Collectors.joining(", ")) +
                "; got: " + token.getType() + ")";
    }

    @Override
    public List<ErrorHighlight> getHighlights() {
        int position = token.getStartPosition();
        int length = token.getValue().length();
        return Collections.singletonList(new ErrorHighlight(position, position + length - 1));
    }
}
