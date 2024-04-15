package com.auberer.compilerdesignlectureproject.reader;

public interface IReader {
  char getChar();
  CodeLoc getCodeLoc();
  void advance();
  void expect(char expectedChar) throws Exception;
  boolean isEOF();
}
