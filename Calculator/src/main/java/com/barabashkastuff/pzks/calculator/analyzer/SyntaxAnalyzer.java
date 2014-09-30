package com.barabashkastuff.pzks.calculator.analyzer;

import com.barabashkastuff.pzks.calculator.domain.Token;
import com.barabashkastuff.pzks.calculator.domain.TokenType;
import com.barabashkastuff.pzks.calculator.exception.SyntaxException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SyntaxAnalyzer Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
@Component
public class SyntaxAnalyzer {
    private List<Token> tokens;

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() throws SyntaxException {
        check();
    }

    private void check() throws SyntaxException {
        int leftB = 0;
        int rightB = 0;
        for (Token token : tokens) {
            if (token.getTokenType() == TokenType.LEFT_BRACKET) leftB++;
            if (token.getTokenType() == TokenType.RIGHT_BRACKET) rightB++;
        }
        if (leftB != rightB) {
            throw new SyntaxException("Expression is non-valid: brackets number doesn't match!");
        }
    }
}
