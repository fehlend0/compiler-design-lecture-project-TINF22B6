package com.auberer.compilerdesignlectureproject.reader;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
public class Reader implements IReader{

    private InputStream inputStream;
    private int currentChar;
    private CodeLoc codeLoc;
    private boolean eof = false;

    public Reader(String filePath){
        try {
            this.inputStream = new FileInputStream(filePath);
            this.codeLoc = new CodeLoc(1, 0);
            advance(); // Initialisiert das erste Zeichen
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public char getChar() {
        if (eof) {
            System.out.println("End of file reached");
        }
        return (char) currentChar;
    }

    @Override
    public CodeLoc getCodeLoc() {
        return codeLoc;
    }

    public void advance(){
        try{
            currentChar = inputStream.read();
            if (currentChar == -1) {
                eof = true;
            } else {
                if (currentChar == '\n') {
                    codeLoc.line++;
                    codeLoc.column = 1;
                } else {
                    codeLoc.column++;
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void expect(char expectedChar) throws IOException {
        if (eof) {
            System.out.println("End of file reached");
        }
        if ((char) currentChar != expectedChar) {
            throw new IOException();
        }
    }
    public boolean isEOF() {
        return eof;
    }
    public void close() {
        try{
            inputStream.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}