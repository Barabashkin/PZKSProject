package com.barabashkastuff.pzks.calculator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * TokenUtils Class
 *
 * @author Andrew S. Slepakurov
 * @version 03/10/2014
 */
public class TokenUtils {
    private TokenUtils() {
    }

    public static boolean lowerPrecedence(Token op1, Token op2) {
        switch (op1.getTokenType()) {

            case ADD:
            case SUB:
                return !(op2.getTokenType().equals(TokenType.ADD) || op2.getTokenType().equals(TokenType.SUB));

            case MULT:
            case DIV:
                return op2.getTokenType().equals(TokenType.POW) || op2.getTokenType().equals(TokenType.LEFT_BRACKET);

            case POW:
                return op2.getTokenType().equals(TokenType.LEFT_BRACKET);

            case LEFT_BRACKET:
                return true;

            default:
                return false;
        }
    }

    public static List<Token> convertToPostfix(List<Token> infix) {
        Stack<Token> operatorStack = new Stack<>();
        List<Token> postfix = new ArrayList<>();
        for (Token token : infix) {
            if ((token.getValue().length() == 1) && token.getTokenType().getSubtype().equals(TokenSubtype.OPERATOR)) {
                while (!operatorStack.empty() &&
                        !lowerPrecedence(operatorStack.peek(), token))
                    postfix.add(operatorStack.pop());

                if (token.getTokenType().equals(TokenType.RIGHT_BRACKET)) {
                    Token operator = operatorStack.pop();
                    while (!operator.getTokenType().equals(TokenType.LEFT_BRACKET)) {
                        postfix.add(operator);
                        operator = operatorStack.pop();
                    }
                } else
                    operatorStack.push(token);
            } else {
                postfix.add(token);
            }
        }
        while (!operatorStack.empty())
            postfix.add(operatorStack.pop());
        return postfix;
    }

    public static Token evalSingleOperation(TokenType operation, Token op1, Token op2) {
        double result = 0.;
        double opValue1 = Double.parseDouble(op1.getValue());
        double opValue2 = Double.parseDouble(op2.getValue());
        switch (operation) {
            case ADD:
                result = opValue1 + opValue2;
                break;
            case SUB:
                result = opValue1 - opValue2;
                break;
            case MULT:
                result = opValue1 * opValue2;
                break;
            case DIV:
                result = opValue1 / opValue2;
        }
        return new Token(-1, result+"", TokenType.FLOAT);
    }

    public static double evaluate(List<Token> tokens) {
        Stack<Token> stack = new Stack<>();
        Token op1, op2, result;
        for (Token token : tokens) {
            if (token.getTokenType().getSubtype().equals(TokenSubtype.OPERATOR)) {
                op2 = stack.pop();
                op1 = stack.pop();
                result = evalSingleOperation(token.getTokenType(), op1, op2);
                stack.push(result);
            } else
                stack.push(token);
        }
        return Float.parseFloat(stack.pop().getValue());
    }
}
