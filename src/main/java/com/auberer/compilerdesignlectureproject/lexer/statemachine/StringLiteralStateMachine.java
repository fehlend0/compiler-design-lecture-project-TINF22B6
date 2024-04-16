package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class StringLiteralStateMachine extends StateMachine {
    @Override
    public void init() {
        State start = new State("start");
        State intermediate = new State("intermediate");
        State accept = new State("accept");

        start.setStartState(true);
        accept.setAcceptState(true);

        addState(start);
        addState(intermediate);
        addState(accept);

        addCharTransition(start, intermediate, '"');
        addRangeTransition(intermediate, intermediate, new Range(' ', '!'));
        addRangeTransition(intermediate, intermediate, new Range('#', '~'));
        addCharTransition(intermediate, accept, '"');
    }

    @Override
    public TokenType getTokenType() {
        if(isInAcceptState()) {
            return TokenType.TOK_STRING;
        }
        return TokenType.TOK_INVALID;
    }
}
