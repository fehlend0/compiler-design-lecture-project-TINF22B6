package com.auberer.compilerdesignlectureproject.reader;

import java.io.IOException;

public interface IReader {
  char getChar();
  CodeLoc getCodeLoc();
  void advance() throws IOException;
  void expect(char expectedChar) throws Exception;
  boolean isEOF();
}
