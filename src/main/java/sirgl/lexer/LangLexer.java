package sirgl.lexer;

import java.io.IOException;
import java.io.Reader;

public class LangLexer implements Lexer {
    private final Reader reader;
    private int nextCharPosition = -1;
    private int nextChar;

    public LangLexer(Reader reader) throws IOException {
        this.reader = reader;
        readNextChar();
    }

    private void readNextChar() throws IOException {
        nextChar = reader.read();
        nextCharPosition++;
    }

    @Override
    public Token next() throws IOException {
        while (isSpacingLetter(nextChar)) {
            readNextChar();
        }
        int first = this.nextChar;
        if (first == -1) {
            return null;
        }
        if (first == '(') {
            return tokenFromCurrentLetter(TokenType.Lparen);
        } else if (first == ')') {
            return tokenFromCurrentLetter(TokenType.Rparen);
        } else if (isIdentifierLetter(first)) {
            return parseIdentifier();
        } else if (isProbablyOperatorLetter(first)) {
            return parseOperator();
        } else {
            return parseUnknownInput();
        }
    }

    private boolean isSpacingLetter(int ch) {
        return ch == ' ';
    }

    private Token parseUnknownInput() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int startPosition = nextCharPosition;
        while (nextChar != -1
                && !isIdentifierLetter(nextChar)
                && !isProbablyOperatorLetter(nextChar)
                && nextChar != '('
                && nextChar != ')'
                && !isSpacingLetter(nextChar)) {
            stringBuilder.append((char) nextChar);
            readNextChar();
        }
        return new Token(TokenType.Error, startPosition, stringBuilder.toString());
    }

    private Token tokenFromCurrentLetter(TokenType type) throws IOException {
        int nextCharPosition = this.nextCharPosition;
        String nextChar = Character.toString((char) this.nextChar);
        readNextChar();
        return new Token(type, nextCharPosition, nextChar);
    }

    private boolean isIdentifierLetter(int ch) {
        return ch >= 'a' && ch <= 'z';
    }

    private boolean isProbablyOperatorLetter(int ch) {
        return ch >= 'A' && ch <= 'Z';
    }


    private Token parseIdentifier() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int startPosition = nextCharPosition;
        while (nextChar != -1 && isIdentifierLetter(nextChar)) {
            stringBuilder.append((char) nextChar);
            readNextChar();
        }
        return new Token(TokenType.Identifier, startPosition, stringBuilder.toString());
    }

    private Token parseOperator() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int startPosition = nextCharPosition;
        while (nextChar != -1 && isProbablyOperatorLetter(nextChar)) {
            stringBuilder.append((char) nextChar);
            readNextChar();
            String token = stringBuilder.toString();
            if (token.length() <= 3) {
                switch (token) {
                    case "AND":
                        return new Token(TokenType.And, startPosition, token);
                    case "OR":
                        return new Token(TokenType.Or, startPosition, token);
                    case "NOT":
                        return new Token(TokenType.Not, startPosition, token);
                }
            }
        }
        return new Token(TokenType.Error, startPosition, stringBuilder.toString());
    }
}
