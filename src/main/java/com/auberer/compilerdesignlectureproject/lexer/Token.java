package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class Token {
  @Getter
  TokenType type;
  String text;
  CodeLoc codeLoc;
}
