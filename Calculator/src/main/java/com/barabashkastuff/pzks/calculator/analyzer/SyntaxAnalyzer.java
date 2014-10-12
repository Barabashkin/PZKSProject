package com.barabashkastuff.pzks.calculator.analyzer;

import com.barabashkastuff.pzks.calculator.domain.Token;
import com.barabashkastuff.pzks.calculator.domain.TokenType;
import com.barabashkastuff.pzks.calculator.domain.TokenUtils;
import com.barabashkastuff.pzks.calculator.exception.SyntaxException;
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
public class SyntaxAnalyzer {
    private List<Token> tokens;

    private Map<String, String> variables;

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

    public List<Token> getPostfix() {
        return postfix;
    }

    public void setPostfix(List<Token> postfix) {
        this.postfix = postfix;
    }

    public double parse() throws SyntaxException {
        check();
        variablesSwap();
        postfix = TokenUtils.convertToPostfix(tokens);
        return TokenUtils.evaluate(postfix);
    }

    private void variablesSwap() throws SyntaxException {
        for (Token token : tokens) {
            if ((token.getTokenType() == TokenType.ID)) {
                if (!variables.containsKey(token.getValue())) {
                    throw new SyntaxException("Expression contains non-initialized variable at " + token.getPosition());
                }
                token.setTokenType(TokenType.FLOAT);
                token.setValue(variables.get(token.getValue()));
            }
        }
    }

    private void check() throws SyntaxException {
        if (tokens.get(0).getTokenType().equals(TokenType.RIGHT_BRACKET)) {
            throw new SyntaxException("Expression is non-valid: cannot start with ')'!");
        }
        if(tokens.get(0).getTokenType().equals(TokenType.DIV) || tokens.get(0).getTokenType().equals(TokenType.MULT)){
            throw new SyntaxException("Expression is non-valid: cannot start with '"+tokens.get(0).getValue()+"' !");
        }
        if(tokens.get(tokens.size()-1).getTokenType().equals(TokenType.DIV) || tokens.get(tokens.size()-1).getTokenType().equals(TokenType.MULT)
                || tokens.get(tokens.size()-1).getTokenType().equals(TokenType.ADD) || tokens.get(tokens.size()-1).getTokenType().equals(TokenType.SUB)){
            throw new SyntaxException("Expression is non-valid: cannot end with '"+tokens.get(tokens.size()-1).getValue()+"' !");
        }
//        if (tokens.get(tokens.size() - 1).getTokenType().getSubtype().equals(TokenSubtype.OPERATOR)) {
//            throw new SyntaxException("Expression is non-valid: cannot end with '" + tokens.get(tokens.size() - 1).getValue() + "'!");
//        }
        if (tokens.get(tokens.size() - 1).getTokenType().equals(TokenType.LEFT_BRACKET)) {
            throw new SyntaxException("Expression is non-valid: cannot end with '('!");
        }
        int leftB = 0;
        int rightB = 0;
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
//            if (token.getTokenType().getSubtype() == TokenSubtype.OPERATOR && tokens.get(i + 1).getTokenType().getSubtype() == TokenSubtype.OPERATOR) {
//                throw new SyntaxException("Expression is non-valid: cannot contain two operations in a row!");
//            }
            if (token.getTokenType() == TokenType.LEFT_BRACKET) leftB++;
            if (token.getTokenType() == TokenType.RIGHT_BRACKET) rightB++;
        }
        if (leftB != rightB) {
            throw new SyntaxException("Expression is non-valid: brackets number doesn't match!");
        }
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.getTokenType() == TokenType.LEFT_BRACKET)
            if (tokens.get(i+1).getTokenType() == TokenType.RIGHT_BRACKET)
                throw new SyntaxException("Empty brackets at "+token.getPosition());
        }
        int left =0;
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.getTokenType() == TokenType.LEFT_BRACKET) left++;
                if (tokens.get(i).getTokenType() == TokenType.RIGHT_BRACKET)
                    if(left==0){
                        throw new SyntaxException("Expression is non-valid: non-mathing ')' at "+token.getPosition());
                    }else{
                        left--;
                    }
        }
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).getTokenType()==TokenType.ADD || tokens.get(i).getTokenType()==TokenType.SUB || tokens.get(i).getTokenType()==TokenType.MULT || tokens.get(i).getTokenType()==TokenType.DIV){
                if(tokens.get(i+1).getTokenType()==TokenType.ADD || tokens.get(i+1).getTokenType()==TokenType.SUB || tokens.get(i+1).getTokenType()==TokenType.MULT || tokens.get(i+1).getTokenType()==TokenType.DIV){
                    throw new SyntaxException("Expression is non-valid: '"+tokens.get(i).getValue()+"' cannot be followed by '"+tokens.get(i+1).getValue()+"' !");
                }
            }
        }


    }
}
