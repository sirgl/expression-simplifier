package sirgl.analysis;

import sirgl.nodes.Node;

import java.util.List;

public class SimplificationResult {
    private List<AnalysisError> errors;
    private Node result;

    public SimplificationResult(Node result) {
        this.result = result;
    }

    public SimplificationResult(List<AnalysisError> errors) {
        this.errors = errors;
    }

    public boolean hasErrors() {
        return errors != null && errors.size() > 0;
    }

    public List<AnalysisError> getErrors() {
        return errors;
    }

    public Node getResult() {
        return result;
    }
}
