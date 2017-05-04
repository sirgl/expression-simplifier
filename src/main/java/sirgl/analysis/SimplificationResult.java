package sirgl.analysis;

import sirgl.nodes.Node;

import java.util.List;

public class SimplificationResult {
    private List<AnalysisError> errors;
    private Node result;

    public SimplificationResult(Node result) {
        this.result = result;
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public List<AnalysisError> getErrors() {
        return errors;
    }

    public Node getResult() {
        return result;
    }
}
