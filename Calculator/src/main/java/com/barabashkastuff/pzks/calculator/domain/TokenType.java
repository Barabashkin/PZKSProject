package com.barabashkastuff.pzks.calculator.domain;

/**
 * TokenType Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
public enum TokenType {
    // 42
    INT("integer", TokenSubtype.OPERAND),
    // 3.14
    FLOAT("float", TokenSubtype.OPERAND),
    // e
    ID("variable", TokenSubtype.OPERAND),
    // (
    LEFT_BRACKET("left bracket", TokenSubtype.OPERATOR),
    // )
    RIGHT_BRACKET("right bracket", TokenSubtype.OPERATOR),
    // /
    DIV("division", TokenSubtype.OPERATOR),
    // *
    MULT("multiply", TokenSubtype.OPERATOR),
    // +
    ADD("addition", TokenSubtype.OPERATOR),
    // -
    SUB("submission", TokenSubtype.OPERATOR),
    // ^
    POW("power", TokenSubtype.OPERATOR),

    EOE("end of expression", TokenSubtype.OTHER);

    TokenType(String name, TokenSubtype subtype) {
        this.name = name;
        this.subtype = subtype;
    }

    private String name;

    private TokenSubtype subtype;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TokenSubtype getSubtype() {
        return subtype;
    }

    public void setSubtype(TokenSubtype subtype) {
        this.subtype = subtype;
    }
}
