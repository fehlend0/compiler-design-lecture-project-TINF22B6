package com.auberer.compilerdesignlectureproject.lexer.statemachine;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

public class DoubleLiteralStateMachine extends StateMachine{
    @Override
    public void init() {
        State start = new State("start");
        State d1 = new State("d1");
        State intermediate = new State ("intermediate");
        State accept = new State("accept");
        State deny = new State("deny");

        start.setStartState(true);
        accept.setAcceptState(true);

        addState(start);
        addState(intermediate);
        addState(deny);
        addState(accept);

        addRangeTransition(start, d1, new Range('0', '9'));
        addRangeTransition(d1, d1, new Range('0', '9'));
        addCharTransition(d1, intermediate, ',');
        addRangeTransition(intermediate, accept, new Range('0', '9'));
        addRangeTransition(accept, accept, new Range('0', '9'));

        addElseTransition(start, deny);
        addElseTransition(d1, deny);
        addElseTransition(intermediate, deny);
        addElseTransition(accept, deny);
        addElseTransition(deny, deny);

    }

    @Override
    public TokenType getTokenType() {
        if(isInAcceptState()){
            return TokenType.TOK_DOUBLE;
        }
        return TokenType.TOK_INVALID;
    }
}
