package com.auberer.compilerdesignlectureproject;

import com.auberer.compilerdesignlectureproject.lexer.Lexer;

public class CompilerDesignLectureProject {
    static String basePath = System.getProperty("user.dir");
    static String testfile = new String(basePath + "/test.txt");
    static String emptyfile = new String(basePath + "/empty.txt");
    static String invalidFile = new String(basePath + "/invalid.txt");

    public static void main(String[] args) {
        try {
            Lexer lexer = new Lexer(testfile);
            lexer.start();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
