package com.auberer.compilerdesignlectureproject.reader;

import com.auberer.compilerdesignlectureproject.lexer.Token;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader implements IReader {

    private BufferedReader reader;
    private String currentLine = null;
    private char currentChar;
    private long line = 1; // Start line from 1
    private long column = 0; // Start column from 0

    public Reader(String inputFilePath) throws IOException {
        this.reader = new BufferedReader(new FileReader(inputFilePath));
        // read the first line
        this.read();
    }

    public void read() throws IOException {
        this.currentLine = reader.readLine();
        if (this.currentLine != null) {
            // add a line break not to miss it out while reading
            this.currentLine += '\n';
        }
        column = 0; // Reset column counter when reading a new line
    }

    @Override
    public char getChar() {
        return currentChar;
    }

    @Override
    public CodeLoc getCodeLoc() {
        return new CodeLoc(line, column);
    }

    @Override
    public void advance() {
        if (column < currentLine.length() - 1) {
            column++;
            currentChar = currentLine.charAt((int) column);
        } else if (column == currentLine.length() - 1) {
            // if the end of the line is reached read the next line;
            try {
                // reset the column counter and increase the line counter
                this.column = 0;
                line++;
                read();
                if (currentLine != null) {
                    currentChar = currentLine.charAt((int) column);
                } else {
                    currentChar = 0;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // End of file
            currentChar = 0;
        }
    }

    @Override
    public void expect(char expectedChar) throws Exception {
        if (currentChar != expectedChar) {
            throw new Exception("An unexpected symbol '" + currentChar + "' at line " + line + ", column " + (column + 1) + "!"); // Adjust column by adding 1
        }
    }

    @Override
    public boolean isEOF() {
        return currentLine == null;
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}