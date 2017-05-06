package sirgl.analysis;

import java.util.List;

public interface AnalysisError {
    String getMessage();
    List<ErrorHighlight> getHighlights();
}
