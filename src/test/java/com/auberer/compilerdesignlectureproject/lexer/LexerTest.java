package com.auberer.compilerdesignlectureproject.lexer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTest {

    static String basePath = System.getProperty("user.dir");
    static String testfile = new String(basePath + "/test.txt");
    static String emptyfile = new String(basePath + "/empty.txt");
    static String invalidFile = new String(basePath + "/invalid.txt");

    @Test
    @DisplayName("Test for End of file")
    public void isEOF() throws IOException {
        Lexer lexer = new Lexer(emptyfile);
        lexer.start();

        assertEquals(lexer.getTokens().size(), 1);
        assertEquals(lexer.getTokens().get(0).getType(), TokenType.TOK_EOF);
    }

    @Test
    @DisplayName("Test for invalid token")
    public void isInvalid() throws IOException {
        Lexer lexer = new Lexer(invalidFile);
        lexer.start();

        assertEquals(lexer.getTokens().get(0).getType(), TokenType.TOK_INVALID);
    }

    @Test
    @DisplayName("Test for valid file")
    public void isValid() throws IOException{
        Lexer lexer = new Lexer(testfile);
        lexer.start();

        assertEquals(lexer.getTokens().size(), 8);

        TokenType[] expected = {TokenType.TOK_KEYWORD, TokenType.TOK_IDENTIFIER, TokenType.TOK_INTEGER, TokenType.TOK_STRING, TokenType.TOK_KEYWORD, TokenType.TOK_IDENTIFIER, TokenType.TOK_DOUBLE, TokenType.TOK_EOF};

        for(int i = 0; i < lexer.getTokens().size(); i++){
            assertEquals(lexer.getTokens().get(i).getType(), expected[i]);
        }

    }
}
