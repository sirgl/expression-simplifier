package sirgl.lexer;

public class Token {
    private TokenType type;
    private int startPosition;
    private String value;

    public Token(TokenType type, int startPosition, String value) {
        this.type = type;
        this.startPosition = startPosition;
        this.value = value;
    }


    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (startPosition != token.startPosition) return false;
        if (type != token.type) return false;
        return value.equals(token.value);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + startPosition;
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", startPosition=" + startPosition +
                ", value='" + value + '\'' +
                '}';
    }
}
