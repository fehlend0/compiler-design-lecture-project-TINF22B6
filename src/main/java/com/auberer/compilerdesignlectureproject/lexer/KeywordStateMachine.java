package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.lexer.statemachine.StateMachine;

public class KeywordStateMachine extends StateMachine {
    private String keyword;

    public KeywordStateMachine(String keyword) {
        this.keyword = keyword;
    }
    @Override
    public void init() {

    }

    @Override
    public TokenType getTokenType() {
        return null;
    }
}
