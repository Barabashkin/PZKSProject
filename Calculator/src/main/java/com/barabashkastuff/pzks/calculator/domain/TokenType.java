package com.barabashkastuff.pzks.calculator.domain;

/**
 * TokenType Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
public enum TokenType {
    // 42
    INT("integer"),
    // 3.14
    FLOAT("float"),
    // e
    ID("variable"),
    // (
    LEFT_BRACKET("left bracket"),
    // )
    RIGHT_BRACKET("right bracket"),
    // /
    DIV("division"),
    // *
    MULT("multiply"),
    // +
    ADD("addition"),
    // -
    SUB("submission"),

    EOE("end of expression");

    TokenType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
