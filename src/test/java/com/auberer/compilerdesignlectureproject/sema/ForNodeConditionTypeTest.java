package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTForNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ForNodeConditionTypeTest {
    private Parser parserCorrect;
    private Parser parserFalse;
    private SymbolTableBuilder symbolTableBuilder;
    private TypeChecker typeChecker;

    @BeforeEach
    void setUp() {
        Reader readerCorrect = new Reader("for (int i = 0; i == 10; i = i + 1) {  }");
        Lexer lexerCorrect = new Lexer(readerCorrect, false);
        parserCorrect = new Parser(lexerCorrect);

        Reader readerFalse = new Reader("for (int i = 0; 10.5; i = i + 1) {  }");
        Lexer lexerFalse = new Lexer(readerFalse, false);
        parserFalse = new Parser(lexerFalse);

        symbolTableBuilder = new SymbolTableBuilder();
        typeChecker = new TypeChecker();
    }

    // Test correct condition type
    @Test
    void testCorrectForNodeConditionType() {
        ASTForNode forNode = assertDoesNotThrow(() -> parserCorrect.parseForLoop());

        symbolTableBuilder.visitForLoop(forNode);

        assertDoesNotThrow(() -> typeChecker.visitForLoop(forNode));
    }

    // Test wrong condition type
    @Test
    void testForNodeConditionType() {
        ASTForNode forNode = assertDoesNotThrow(() -> parserFalse.parseForLoop());

        symbolTableBuilder.visitForLoop(forNode);

        SemaError thrown = assertThrows(
            SemaError.class,
            () -> typeChecker.visitForLoop(forNode),
            "For loop condition must be a boolean expression"
        );

        assertEquals("L1C1: Boolean type expected, but instead got 'TY_DOUBLE'", thrown.getMessage());
    }
}
