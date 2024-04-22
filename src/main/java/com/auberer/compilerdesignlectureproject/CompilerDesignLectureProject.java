package com.auberer.compilerdesignlectureproject;

import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class CompilerDesignLectureProject {
    static String basePath = System.getProperty("user.dir");
    static String testfile = new String(basePath + "/test.txt");
    static String emptyfile = new String(basePath + "/empty.txt");
    static String invalidFile = new String(basePath + "/invalid.txt");

    public static void main(String[] args) {
        try {
            Lexer lexer = new Lexer(testfile);
            List<Token> tokens = new ArrayList<>();
            do {
                lexer.advance();
                tokens.add(lexer.getToken());
            }
            while(!lexer.isEOF());
            System.out.println(tokens.size());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
