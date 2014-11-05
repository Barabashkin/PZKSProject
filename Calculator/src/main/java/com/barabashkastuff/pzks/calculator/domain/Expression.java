package com.barabashkastuff.pzks.calculator.domain;

import com.barabashkastuff.pzks.calculator.analyzer.LexicalAnalyzer;
import com.barabashkastuff.pzks.calculator.analyzer.SyntaxAnalyzer;
import com.barabashkastuff.pzks.calculator.analyzer.VariableAnalyzer;
import com.barabashkastuff.pzks.calculator.exception.LexicalException;
import com.barabashkastuff.pzks.calculator.exception.SyntaxListException;
import com.barabashkastuff.pzks.calculator.exception.VariableException;
import com.barabashkastuff.pzks.calculator.tree.BinaryNode;
import com.barabashkastuff.pzks.calculator.tree.ExpressionTreeBuilder;
import com.barabashkastuff.pzks.calculator.tree.TokenNode;
import com.barabashkastuff.pzks.calculator.tree.TreePrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Expression Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
@Component
@Scope("prototype")
public class Expression {
    private String body;
    private String varBody;
    private List<Token> tokens;
    private Map<String, String> variables;
    private List<Token> postfix;
    private String result;
    private String exception;
    private BinaryNode tree;
    private String treePic;

    @Autowired
    private VariableAnalyzer variableAnalyzer;
    @Autowired
    private LexicalAnalyzer lexicalAnalyzer;
    @Autowired
    private SyntaxAnalyzer syntaxAnalyzer;
    @Autowired
    private ExpressionTreeBuilder expressionTreeBuilder;
    @Autowired
    private TreePrinter treePrinter;

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

    public BinaryNode getTree() {
        return tree;
    }

    public void setTree(BinaryNode tree) {
        this.tree = tree;
    }

    public String getTreePic() {
        return treePic;
    }

    public void setTreePic(String treePic) {
        this.treePic = treePic;
    }

    public void evaluate() throws VariableException, LexicalException, SyntaxListException, IOException {
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
        setPostfix(syntaxAnalyzer.getPostfix());
        expressionTreeBuilder.setTokens(syntaxAnalyzer.getTokens());
        setTree(expressionTreeBuilder.build());
        setTreePic(treePrinter.printNode(getTree()));
    }
}
