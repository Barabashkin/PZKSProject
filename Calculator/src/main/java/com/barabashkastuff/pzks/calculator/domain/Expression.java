package com.barabashkastuff.pzks.calculator.domain;

import com.barabashkastuff.pzks.calculator.analyzer.LexicalAnalyzer;
import com.barabashkastuff.pzks.calculator.analyzer.SyntaxAnalyzer;
import com.barabashkastuff.pzks.calculator.analyzer.VariableAnalyzer;
import com.barabashkastuff.pzks.calculator.exception.LexicalException;
import com.barabashkastuff.pzks.calculator.exception.SyntaxException;
import com.barabashkastuff.pzks.calculator.exception.VariableException;
import org.springframework.beans.factory.annotation.Autowired;

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
    private String varBody;
    private List<Token> tokens;
    private Map<String, String> variables;
    private List<Token> postfix;
    private String result;
    private String exception;

    @Autowired
    private VariableAnalyzer variableAnalyzer;
    @Autowired
    private LexicalAnalyzer lexicalAnalyzer;
    @Autowired
    private SyntaxAnalyzer syntaxAnalyzer;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getVarBody() {
        return varBody;
    }

    public void setVarBody(String varBody) {
        this.varBody = varBody;
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

    public void evaluate() throws VariableException, LexicalException, SyntaxException {
        variableAnalyzer.setBody(getVarBody());
        variableAnalyzer.process();
        setVariables(variableAnalyzer.getVariables());
        lexicalAnalyzer.setExpression(getBody());
        lexicalAnalyzer.process();
        setTokens(lexicalAnalyzer.getTokens());
        syntaxAnalyzer.setVariables(variables);
        syntaxAnalyzer.setTokens(tokens);
        syntaxAnalyzer.process();
        setResult(syntaxAnalyzer.getResult());
    }
}
