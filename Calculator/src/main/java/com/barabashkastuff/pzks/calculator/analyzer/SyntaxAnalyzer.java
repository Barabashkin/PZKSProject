package com.barabashkastuff.pzks.calculator.analyzer;

import com.barabashkastuff.pzks.calculator.domain.Token;
import com.barabashkastuff.pzks.calculator.domain.TokenSubtype;
import com.barabashkastuff.pzks.calculator.domain.TokenType;
import com.barabashkastuff.pzks.calculator.domain.TokenUtils;
import com.barabashkastuff.pzks.calculator.exception.SyntaxException;
import com.barabashkastuff.pzks.calculator.exception.SyntaxListException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * SyntaxAnalyzer Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
@Component
@Scope("prototype")
public class SyntaxAnalyzer implements IProcessor {
    //  Consume
    private List<Token> tokens;
    private Map<String, String> variables;
    //  Produce
    private String result;
    private List<Token> postfix;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Token> getPostfix() {
        return postfix;
    }

    public void setPostfix(List<Token> postfix) {
        this.postfix = postfix;
    }

    public void process() throws SyntaxListException {
        preProcessing();
        varSwap();
        List<Token> postfix = TokenUtils.convertToPostfix(tokens);
        setResult(TokenUtils.evaluate(postfix));
        setPostfix(postfix);
    }

    private void varSwap() {
        for (int i = 0; i < tokens.size(); i++) {
            Token currToken = tokens.get(i);
            if ((currToken.getTokenType() == TokenType.ID)) {
                currToken.setTokenType(TokenType.FLOAT);
                currToken.setValue(variables.get(currToken.getValue()));
                tokens.set(i, currToken);
            }
        }
    }

    private void preProcessing() throws SyntaxListException {
        SyntaxListException listException = new SyntaxListException();
        if (tokens.get(0).getTokenType().equals(TokenType.RIGHT_BRACKET) || tokens.get(0).getTokenType().equals(TokenType.DIV) || tokens.get(0).getTokenType().equals(TokenType.MULT)) {
            listException.add(new SyntaxException("Expression is non-valid: cannot start with '" + tokens.get(0).getValue() + "' !"));
        }
        if (tokens.get(tokens.size() - 1).getTokenType().getSubtype().equals(TokenSubtype.OPERATOR) || tokens.get(tokens.size() - 1).getTokenType().equals(TokenType.LEFT_BRACKET)) {
            listException.add(new SyntaxException("Expression is non-valid: cannot end with '" + tokens.get(tokens.size() - 1).getValue() + "'!"));
        }
        int leftB = 0;
        int rightB = 0;
        int bracketBalance = 0;
        for (int i = 0; i < tokens.size(); i++) {
            Token currToken = tokens.get(i);
            Token nextToken = (i + 1 != tokens.size()) ? tokens.get(i + 1) : null;
            if (currToken.getTokenType().getSubtype().equals(TokenSubtype.OPERAND) || currToken.getTokenType().equals(TokenType.RIGHT_BRACKET)) {
                if (nextToken != null && (nextToken.getTokenType().getSubtype().equals(TokenSubtype.OPERAND) || nextToken.getTokenType().equals(TokenType.LEFT_BRACKET))) {
                    listException.add(new SyntaxException("Expression is non-valid: '" + currToken.getValue() + "' cannot be followed by '" + nextToken.getValue() + "' !"));
                }
            }
            if (currToken.getTokenType() == TokenType.LEFT_BRACKET) {
                if (nextToken != null && (nextToken.getTokenType() == TokenType.RIGHT_BRACKET)) {
                    listException.add(new SyntaxException("Empty brackets at " + currToken.getPosition()));
                }
            }
            if (currToken.getTokenType() == TokenType.LEFT_BRACKET) {
                bracketBalance++;
            }
            if (currToken.getTokenType() == TokenType.RIGHT_BRACKET) {
                if (bracketBalance == 0) {
                    listException.add(new SyntaxException("Expression is non-valid: non-mathing ')' at " + currToken.getPosition()));
                } else {
                    bracketBalance--;
                }
            }
            if ((currToken.getTokenType() == TokenType.ID)) {
                if (!variables.containsKey(currToken.getValue())) {
                    listException.add(new SyntaxException("Expression contains non-initialized variable at " + currToken.getPosition()));
                }
            }
            if (nextToken != null && currToken.getTokenType().getSubtype() == TokenSubtype.OPERATOR && nextToken.getTokenType().getSubtype() == TokenSubtype.OPERATOR) {
                listException.add(new SyntaxException("Expression is non-valid: '" + currToken.getValue() + "' cannot be followed by '" + nextToken.getValue() + "' !"));
            }
            if (currToken.getTokenType() == TokenType.LEFT_BRACKET) {
                leftB++;
            }
            if (currToken.getTokenType() == TokenType.RIGHT_BRACKET) {
                rightB++;
            }
        }

        if (leftB != rightB) {
            listException.add(new SyntaxException("Expression is non-valid: brackets number doesn't match!"));
        }

        if (listException.hasException()) {
            throw listException;
        }
    }
}
