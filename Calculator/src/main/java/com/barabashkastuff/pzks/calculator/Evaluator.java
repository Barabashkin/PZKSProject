package com.barabashkastuff.pzks.calculator;

import com.barabashkastuff.pzks.calculator.analyzer.LexicalAnalyzer;
import com.barabashkastuff.pzks.calculator.analyzer.SyntaxAnalyzer;
import com.barabashkastuff.pzks.calculator.domain.Token;
import com.barabashkastuff.pzks.calculator.exception.LexicalException;

import java.util.ArrayList;
import java.util.List;

/**
 * Evaluator Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
public class Evaluator {
    private LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("1+2");
    private SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
    public static void main(String[] args) throws LexicalException {
        System.out.println((new Evaluator()).evalute());
    }

    public String evalute() throws LexicalException {
        List<Token> tokens = new ArrayList<Token>();
        for(;lexicalAnalyzer.hasNextChar();){
            tokens.add(lexicalAnalyzer.getNextToken());
        }
        for (Token token : tokens) {
            System.out.println(token);
        }
        return "";
    }
}
