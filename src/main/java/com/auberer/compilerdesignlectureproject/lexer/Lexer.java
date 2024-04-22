package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.*;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.auberer.compilerdesignlectureproject.reader.Reader;
import lombok.Getter;

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

        stateMachineList.add(new IdentifierStateMachine());
        stateMachineList.add(new StringLiteralStateMachine());
        stateMachineList.add(new IntegerLiteralStateMachine());
        stateMachineList.add(new DoubleLiteralStateMachine());
        stateMachineList.add(new KeywordStateMachine("int"));
        stateMachineList.add(new KeywordStateMachine("double"));
        stateMachineList.add(new KeywordStateMachine("String"));

        for (StateMachine stateMachine : stateMachineList) {
            stateMachine.init();
            stateMachine.reset();
        }

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
        Token token = consumeToken();

        currentToken = token;
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
        return this.reader.isEOF();
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
            stateMachine.reset();
        }

        List<StateMachine> runningMachines = new ArrayList<>(stateMachineList);
        StateMachine acceptingMachine = null;
        StringBuilder currentToken = new StringBuilder();

        CodeLoc loc = reader.getCodeLoc();
        loc = new CodeLoc(loc.getLine(), loc.getColumn());

        while(!runningMachines.isEmpty()){
            if(reader.isEOF()){
                break;
            }

            char currentChar = reader.getChar();
            if(currentChar == '\r' || currentChar == '\n'){
                break;
            }

            currentToken.append(currentChar);
            List<StateMachine> endedMachines = new ArrayList<>();
            for(StateMachine stateMachine: runningMachines){
                try {
                    stateMachine.processInput(currentChar);
                }
                catch(IllegalStateException ex){
                    endedMachines.add(stateMachine);
                }
                if(stateMachine.isInAcceptState()){
                    acceptingMachine = stateMachine;
                }
            }
            runningMachines.removeAll(endedMachines);
            endedMachines.clear();
            reader.advance();
        }

        if(acceptingMachine == null){
            return new Token(TokenType.TOK_INVALID, currentToken.toString(), loc);
        }

        return new Token(acceptingMachine.getTokenType(), currentToken.toString(), loc);
    }

}
