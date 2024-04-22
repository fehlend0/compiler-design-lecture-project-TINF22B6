package com.auberer.compilerdesignlectureproject.lexer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTest {

    static String basePath = System.getProperty("user.dir");
    static String testfile = new String(basePath + "/test.txt");
    static String emptyfile = new String(basePath + "/empty.txt");
    static String invalidFile = new String(basePath + "/invalid.txt");

    @Test
    @DisplayName("Test for End of file")
    public void isEOF(){
        Lexer lexer = new Lexer(emptyfile);
        List<Token> tokens = new ArrayList<>();
        do {
            lexer.advance();
            tokens.add(lexer.getToken());
        }
        while(!lexer.isEOF());

        assertEquals(tokens.size(), 1);
        assertEquals(tokens.get(0).getType(), TokenType.TOK_EOF);
    }

    @Test
    @DisplayName("Test for invalid token")
    public void isInvalid(){
        Lexer lexer = new Lexer(invalidFile);
        List<Token> tokens = new ArrayList<>();
        do {
            lexer.advance();
            tokens.add(lexer.getToken());
        }
        while(!lexer.isEOF());

        assertEquals(tokens.get(0).getType(), TokenType.TOK_INVALID);
    }

    @Test
    @DisplayName("Test for valid file")
    public void isValid(){
        Lexer lexer = new Lexer(testfile);
        List<Token> tokens = new ArrayList<>();
        do {
            lexer.advance();
            tokens.add(lexer.getToken());
        }
        while(!lexer.isEOF());

        assertEquals(tokens.size(), 8);

        TokenType[] expected = {TokenType.TOK_KEYWORD, TokenType.TOK_IDENTIFIER, TokenType.TOK_INTEGER, TokenType.TOK_STRING, TokenType.TOK_KEYWORD, TokenType.TOK_IDENTIFIER, TokenType.TOK_DOUBLE, TokenType.TOK_EOF};

        for(int i = 0; i < tokens.size(); i++){
            assertEquals(tokens.get(i).getType(), expected[i]);
        }

    }
}
