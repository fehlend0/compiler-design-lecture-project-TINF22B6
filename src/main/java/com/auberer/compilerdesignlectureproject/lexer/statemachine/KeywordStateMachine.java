package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class KeywordStateMachine extends StateMachine{

    String keyword;

    public KeywordStateMachine(String keyword){
        this.keyword = keyword;
    }

    @Override
    public void init() {
        State s = new State("start");
        s.setStartState(true);

        for(char c: keyword.toCharArray()){
            State newS = new State(String.valueOf(c));
            addState(s);
            addCharTransition(s, newS, c);
            s = newS;
        }
        s.setAcceptState(true);

    }

    @Override
    public TokenType getTokenType() {
        if(isInAcceptState()){
            return TokenType.TOK_KEYWORD;
        }
        return TokenType.TOK_INVALID;
    }
}
