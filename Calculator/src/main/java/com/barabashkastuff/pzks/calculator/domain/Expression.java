package com.barabashkastuff.pzks.calculator.domain;

import java.util.List;

/**
 * Expression Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
public class Expression {
    private String body;
    private List<Token> tokens;
    private String result;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
