package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.*;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.auberer.compilerdesignlectureproject.reader.Reader;

/**
 * Input: Character Strean
 * Output: Token Stream
 */
public class Lexer implements ILexer {
    private final Reader reader;
    private Token currentToken;

    private List<StateMachine> stateMachineList = new ArrayList<>();

    public Lexer(String inputFilePath) throws IOException {
        this.reader = new Reader(inputFilePath);

        stateMachineList.add(new IntegerLiteralStateMachine());
        stateMachineList.add(new KeywordStateMachine("DankeRoethig"));
        stateMachineList.add(new StringLiteralStateMachine());
        stateMachineList.add(new KeywordStateMachine("XSLT"));
        stateMachineList.add(new KeywordStateMachine("XML"));
        stateMachineList.add(new DoubleLiteralStateMachine());
        stateMachineList.add(new IntegerLiteralStateMachine());

        for (StateMachine stateMachine : stateMachineList) {
            stateMachine.init();
            stateMachine.reset();
        }

        // Read the 1st token
        advance();
    }

    @Override
    public Token getToken() {
        return currentToken;
    }

    @Override
    public void advance() throws IOException {
        // Skip any whitespaces
        while (Character.isWhitespace(reader.getChar())) {
            reader.advance();
        }
        currentToken = consumeToken();
    }

    @Override
    public void expect(TokenType expectedType) throws Exception {
        if (this.currentToken.getType() != expectedType) {
            throw new Exception("Unexpected token type!");
        }
        this.advance();
    }

    @Override
    public void expectOneOf(Set<TokenType> expectedTypes) throws Exception {
        if (!expectedTypes.contains(currentToken.getType())) {
            throw new Exception("Unexpected token type!");
        }
        advance();
    }

    @Override
    public boolean isEOF() {
        return this.currentToken.type == TokenType.TOK_EOF;
    }

    @Override
    public CodeLoc getCodeLoc() {
        return currentToken.getCodeLoc();
    }

    private Token consumeToken() throws IOException {

        if(reader.isEOF()){
            return new Token(TokenType.TOK_EOF, "EOF", reader.getCodeLoc());
        }

        for (StateMachine stateMachine : stateMachineList) {
            stateMachine.init();
            stateMachine.reset();
        }

        List<StateMachine> runningMachines = new ArrayList<>(stateMachineList);
        StateMachine acceptingMachine = null;
        StringBuilder currentToken = new StringBuilder();

        while(!runningMachines.isEmpty()){
            for(StateMachine stateMachine: runningMachines){
                currentToken.append(reader.getChar());
                try {
                    stateMachine.processInput(reader.getChar());
                }
                catch(IllegalStateException ex){
                    runningMachines.remove(stateMachine);
                }
                if(stateMachine.isInAcceptState()){
                    acceptingMachine = stateMachine;
                    runningMachines.remove(stateMachine);
                }
            }
            reader.advance();
        }

        if(acceptingMachine instanceof IntegerLiteralStateMachine){
            return new Token(TokenType.TOK_INTEGER, currentToken.toString(), reader.getCodeLoc());
        }
        else if(acceptingMachine instanceof DoubleLiteralStateMachine){
            return new Token(TokenType.TOK_DOUBLE, currentToken.toString(), reader.getCodeLoc());
        }
        else if(acceptingMachine instanceof StringLiteralStateMachine){
            return new Token(TokenType.TOK_STRING, currentToken.toString(), reader.getCodeLoc());
        }
        else if(acceptingMachine instanceof IdentifierStateMachine){
            return new Token(TokenType.TOK_IDENTIFIER, currentToken.toString(), reader.getCodeLoc());
        }
        else if(acceptingMachine instanceof KeywordStateMachine){
            return new Token(TokenType.TOK_KEYWORD, currentToken.toString(), reader.getCodeLoc());
        }

        return null;
    }
}
