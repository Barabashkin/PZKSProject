package com.barabashkastuff.pzks.calculator.domain;

import java.util.List;
import java.util.Map;

/**
 * Expression Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
public class Expression {
    private String body;
    private List<Token> tokens;
    private Map<String, String> variables;
    private List<Token> postfix;
    private String result;
    private String exception;

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

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public List<Token> getPostfix() {
        return postfix;
    }

    public void setPostfix(List<Token> postfix) {
        this.postfix = postfix;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public void evaluate(){
//        lexicalAnalyzer.setExpression(expression);
//                syntaxAnalyzer.setVariables(variables);
//                List<Token> tokens = new ArrayList<Token>();
//                try {
//                    for (; ; ) {
//                        Token token = lexicalAnalyzer.getNextToken();
//                        if (token.getTokenType() == TokenType.EOE) break;
//                        tokens.add(token);
//                    }
//                    syntaxAnalyzer.setTokens(tokens);
//                    double result = syntaxAnalyzer.parse();
//                    StringBuilder sb = new StringBuilder();
//                    for (Token token : syntaxAnalyzer.getPostfix()) {
//                        sb.append(token.getValue() + " ");
//                    }
    }
}
