package com.auberer.compilerdesignlectureproject.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReaderTest {

    static String basePath = System.getProperty("user.dir");
    static String testfile = new String(basePath + "/test.txt");
    static String emptyfile = new String(basePath + "/empty.txt");


    @Test
    @DisplayName("This is a default Test")
    public void test() {
        System.out.printf("This is a test:%n");
        Assertions.assertEquals("Test", "Test");
    }

    @Test
    @DisplayName("Test for getChar()")
    public void testGetChar_FirstChar() throws IOException {
        Reader reader = new Reader(testfile);
        reader.advance();
        assertEquals(reader.getChar(), 'I');
        // Test for CodeLoc
        assertEquals(1, reader.getCodeLoc().line);
        assertEquals(1, reader.getCodeLoc().column);
    }
    @Test
    @DisplayName("Test for End of file")
    public void isEOF() throws IOException {
        Reader reader = new Reader(emptyfile);
        reader.advance();
        assertEquals(reader.isEOF(), true);
    }
}


