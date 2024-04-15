package com.auberer.compilerdesignlectureproject;

import com.auberer.compilerdesignlectureproject.reader.Reader;

public class CompilerDesignLectureProject {
    public static void main(String[] args) {
        String filePath = " "; //add a path to a test file
        StringBuilder tokenBuilder = new StringBuilder();
        try {
            Reader reader = new Reader("filePath");
            while (!(reader.isEOF())) {
                tokenBuilder.setLength(0);
                char c = 'a';
                while (c != ' ') {
                    reader.advance();
                    c = reader.getChar();
                    tokenBuilder.append(c);
                }
                System.out.print(tokenBuilder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
