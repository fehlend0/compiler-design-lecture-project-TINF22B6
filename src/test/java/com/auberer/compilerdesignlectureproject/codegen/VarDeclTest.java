package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.ASTVarDeclNode;
import com.auberer.compilerdesignlectureproject.codegen.instructions.AllocaInstruction;
import com.auberer.compilerdesignlectureproject.codegen.instructions.Instruction;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableBuilder;
import com.auberer.compilerdesignlectureproject.sema.TypeChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VarDeclTest {

    @Test
    @DisplayName("Integration test - Codegen VarDecl (Mit rechteseite)")
    public void VarDeclTestmitrechts() {
        String code = "int x = 20";

        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTVarDeclNode node = parser.parseVarDecl();

        IRGenerator irGenerator = new IRGenerator("VarDecl");
        BasicBlock basicBlock = new BasicBlock("Start-Block");
        irGenerator.setCurrentBlock(basicBlock);

        SymbolTableBuilder symboltable = new SymbolTableBuilder();
        symboltable.visitVarDecl(node);

        TypeChecker typechecker = new TypeChecker();
        typechecker.visitVarDecl(node);

        IRExprResult irExprResult = irGenerator.visitVarDecl(node);

        assertEquals(irExprResult.getNode(), node);
        assertEquals(irExprResult.getEntry(), node.getCurrentSymbol());

        assertEquals("Start-Block", irGenerator.getCurrentBlock().getLabel());
        assertEquals(2, irGenerator.getCurrentBlock().getInstructions().size());

        Instruction allocaInstruction = irGenerator.getCurrentBlock().getInstructions().getFirst();
        assertTrue(allocaInstruction instanceof AllocaInstruction);

        StringBuilder sb = new StringBuilder();
        Function function = new Function("main", new ArrayList<>());
        function.setEntryBlock(basicBlock);
        function.dumpIR(sb);
        String irCode = sb.toString();
        assertTrue(irCode.contains("Start-Block"));
    }

    @Test
    @DisplayName("Integration test - Codegen VarDecl (Ohne rechteseite)")
    public void VarDeclTestohnerechts() {
        String code = "int x;";

        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTVarDeclNode node = parser.parseVarDecl();

        IRGenerator irGenerator = new IRGenerator("VarDecl");
        BasicBlock basicBlock = new BasicBlock("Start-Block");
        irGenerator.setCurrentBlock(basicBlock);

        SymbolTableBuilder symboltable = new SymbolTableBuilder();
        symboltable.visitVarDecl(node);


        IRExprResult irExprResult = irGenerator.visitVarDecl(node);

        assertEquals(irExprResult.getNode(), node);
        assertEquals(irExprResult.getEntry(), node.getCurrentSymbol());

        assertEquals("Start-Block", irGenerator.getCurrentBlock().getLabel());
        assertEquals(1, irGenerator.getCurrentBlock().getInstructions().size());

        Instruction instruction = irGenerator.getCurrentBlock().getInstructions().getFirst();
        assertTrue(instruction instanceof AllocaInstruction);

        StringBuilder sb = new StringBuilder();
        Function function = new Function("main", new ArrayList<>());
        function.setEntryBlock(basicBlock);
        function.dumpIR(sb);
        String irCode = sb.toString();
        assertTrue(irCode.contains("Start-Block"));
    }
}
