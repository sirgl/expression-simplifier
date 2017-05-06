package sirgl.analysis.errors;

import sirgl.analysis.AnalysisError;
import sirgl.analysis.ErrorHighlight;

import java.util.Collections;
import java.util.List;

public class BasicError implements AnalysisError {
    private final String message;

    public BasicError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<ErrorHighlight> getHighlights() {
        return Collections.emptyList();
    }
}
