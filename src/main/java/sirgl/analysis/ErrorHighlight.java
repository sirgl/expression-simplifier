package sirgl.analysis;

public class ErrorHighlight {
    private int start;
    private int end;

    public ErrorHighlight(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean inRange(int position) {
        return position >= start && position <= end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
