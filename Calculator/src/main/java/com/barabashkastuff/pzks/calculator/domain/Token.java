package com.barabashkastuff.pzks.calculator.domain;

import lombok.ToString;

/**
 * Token Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
@ToString
public class Token {
    private int position;
    private String value;
    private TokenType tokenType;

    public Token(int position, String value, TokenType tokenType) {
        this.position = position;
        this.value = value;
        this.tokenType = tokenType;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}
