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

    public Reader(String filePath) throws IOException {
        this.inputStream = new FileInputStream(filePath);
        this.codeLoc = new CodeLoc(1,0);
        advance(); // Initialisiert das erste Zeichen
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

    public void advance() throws IOException {
        currentChar = inputStream.read();
        if (currentChar == -1) {
            eof = true;
        } else {
            if (currentChar == '\n') {
                codeLoc.line++;
                codeLoc.column = 0;
            } else {
                codeLoc.column++;
            }
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
    public void close() throws IOException {
        inputStream.close();
    }
}