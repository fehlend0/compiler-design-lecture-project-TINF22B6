package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class IntegerLiteralStateMachine extends StateMachine{
    @Override
    public void init() {
        State start = new State("start");
        State deny = new State("deny");
        State accept = new State("accept");

        start.setStartState(true);
        accept.setAcceptState(true);

        addState(start);
        addState(deny);
        addState(accept);

        addRangeTransition(start, accept, new Range('1', '9'));
        addRangeTransition(accept, accept, new Range('0', '9'));
        addElseTransition(start, deny);
        addElseTransition(accept, deny);
        addElseTransition(deny, deny);

    }

    @Override
    public TokenType getTokenType() {
        if(isInAcceptState()) {
            return TokenType.TOK_INTEGER;
        }
        return TokenType.TOK_INVALID;
    }
}
