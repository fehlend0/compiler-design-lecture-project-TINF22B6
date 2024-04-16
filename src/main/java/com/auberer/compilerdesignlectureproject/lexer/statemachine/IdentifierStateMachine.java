package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class IdentifierStateMachine extends StateMachine{
    @Override
    public void init() {
        State start = new State("start");
        State accept = new State("accept");

        start.setStartState(true);
        accept.setAcceptState(true);

        addState(start);
        addState(accept);

        addRangeTransition(start, accept, new Range('A', 'z'));
        addCharTransition(start, accept, '_');
        addRangeTransition(accept, accept, new Range('A', 'z'));
        addRangeTransition(accept, accept, new Range('0', '9'));
        addCharTransition(accept, accept, '_');
    }

    @Override
    public TokenType getTokenType() {
        if(isInAcceptState()){
            return TokenType.TOK_IDENTIFIER;
        }
        return TokenType.TOK_INVALID;
    }
}
