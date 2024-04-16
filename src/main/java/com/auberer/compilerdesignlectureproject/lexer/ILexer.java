package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.reader.CodeLoc;

import java.io.IOException;
import java.util.Set;

public interface ILexer {
  Token getToken();
  void advance() throws IOException;
  void expect(TokenType expectedType) throws Exception;
  void expectOneOf(Set<TokenType> expectedTypes) throws Exception;
  boolean isEOF();
  CodeLoc getCodeLoc();
}
