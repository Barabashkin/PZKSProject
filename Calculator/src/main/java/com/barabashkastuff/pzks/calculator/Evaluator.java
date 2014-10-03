package com.barabashkastuff.pzks.calculator;

import com.barabashkastuff.pzks.calculator.analyzer.LexicalAnalyzer;
import com.barabashkastuff.pzks.calculator.analyzer.SyntaxAnalyzer;
import com.barabashkastuff.pzks.calculator.domain.Token;
import com.barabashkastuff.pzks.calculator.domain.TokenType;
import com.barabashkastuff.pzks.calculator.exception.LexicalException;
import com.barabashkastuff.pzks.calculator.exception.SyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Evaluator Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
@Controller
public class Evaluator {
    @Autowired
    private LexicalAnalyzer lexicalAnalyzer;
    @Autowired
    private SyntaxAnalyzer syntaxAnalyzer;

    public static void main(String[] args) throws LexicalException, SyntaxException {
        System.out.println((new Evaluator()).evalute());
    }

    public String evalute() throws LexicalException, SyntaxException {
        lexicalAnalyzer = new LexicalAnalyzer();
        syntaxAnalyzer = new SyntaxAnalyzer();
        String code = "2 + 3 * 4 / 5 - (2*3 - (4 + 5) / 6)- 35 - (42* (17 /(2 + 10)) - 33.2 - 17.5) * 2.0 - 3.2 - 3 * (4 + 5) + 3 * ( 4 - (5+2))/(2+3)";
        List<Token> tokens = new ArrayList<Token>();
        lexicalAnalyzer.setExpression(code);
        for (;;) {
            Token token = lexicalAnalyzer.getNextToken();
            if (token.getTokenType() == TokenType.EOE) break;
            tokens.add(token);
        }
        for (Token token : tokens) {
            System.out.println(token);
        }
        System.out.println();
        syntaxAnalyzer.setTokens(tokens);
        syntaxAnalyzer.parse();
        return "";
    }
}
