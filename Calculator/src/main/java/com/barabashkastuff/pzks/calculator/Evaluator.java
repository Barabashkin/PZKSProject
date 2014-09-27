package com.barabashkastuff.pzks.calculator;

import com.barabashkastuff.pzks.calculator.analyzer.LexicalAnalyzer;
import com.barabashkastuff.pzks.calculator.analyzer.SyntaxAnalyzer;

/**
 * Evaluator Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
public class Evaluator {
    private LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
    private SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
    public static void main(String[] args) {
        System.out.println(evalute("1+2"));
    }

    public static String evalute(String expression) {

        return expression;
    }
}
