package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.reader.CodeLoc;

import java.io.IOException;
import java.util.Set;

import com.auberer.compilerdesignlectureproject.reader.Reader;

/**
 * Input: Character Strean
 * Output: Token Stream
 */
public class Lexer implements ILexer {
    private final Reader reader;
    private Token currentToken;

    public Lexer(String inputFilePath) throws IOException {
        this.reader = new Reader(inputFilePath);
        // Read the 1st token
        advance();
    }

    @Override
    public Token getToken() {
        return currentToken;
    }

    @Override
    public void advance() throws IOException {
        // Skip any whitespaces
        while (Character.isWhitespace(reader.getChar())) {
            reader.advance();
        }
        currentToken = consumeToken();
    }

    @Override
    public void expect(TokenType expectedType) throws Exception {
        if (this.currentToken.getType() != expectedType) {
            throw new Exception("Unexpected token type!");
        }
        this.advance();
    }

    @Override
    public void expectOneOf(Set<TokenType> expectedTypes) throws Exception {
        if (!expectedTypes.contains(currentToken.getType())) {
            throw new Exception("Unexpected token type!");
        }
        advance();
    }

    @Override
    public boolean isEOF() {
        return this.currentToken.type == TokenType.TOK_EOF;
    }

    @Override
    public CodeLoc getCodeLoc() {
        return currentToken.getCodeLoc();
    }

    private Token consumeToken() {
        // TODO:
        return null;
    }
}
