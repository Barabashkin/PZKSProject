package com.barabashkastuff.pzks.calculator.domain;

/**
 * TokenType Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
public enum TokenType {
    // 42
    INT("integer", TokenSubtype.OPERAND, false),
    // 3.14
    FLOAT("float", TokenSubtype.OPERAND, false),
    // e
    ID("variable", TokenSubtype.OPERAND, false),
    // (
    LEFT_BRACKET("left bracket", TokenSubtype.OTHER, true),
    // )
    RIGHT_BRACKET("right bracket", TokenSubtype.OTHER, true),
    // /
    DIV("division", TokenSubtype.OPERATOR, true),
    // *
    MULT("multiply", TokenSubtype.OPERATOR, true),
    // +
    ADD("addition", TokenSubtype.OPERATOR, true),
    // -
    SUB("submission", TokenSubtype.OPERATOR, true),
    //^
    POW("power",TokenSubtype.OPERATOR, true),
    //end
    EOE("end of expression", TokenSubtype.OTHER, false);

    TokenType(String name, TokenSubtype subtype, boolean flag) {
        this.name = name;
        this.subtype = subtype;
        this.flag = flag;
    }

    private String name;

    private TokenSubtype subtype;

    private boolean flag;

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

    public boolean hasFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
