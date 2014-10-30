package com.barabashkastuff.pzks.calculator.util;

import com.barabashkastuff.pzks.calculator.domain.Token;
import com.barabashkastuff.pzks.calculator.domain.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
//                return op2.getTokenType().equals(TokenType.POW) || op2.getTokenType().equals(TokenType.LEFT_BRACKET);
                return op2.getTokenType().equals(TokenType.LEFT_BRACKET);

//            case POW:
//                return op2.getTokenType().equals(TokenType.LEFT_BRACKET);

            case LEFT_BRACKET:
                return true;

            default:
                return false;
        }
    }

    public static List<Token> convertToPostfix(List<Token> infix) {
        convert(infix);
        Stack<Token> operatorStack = new Stack<Token>();
        List<Token> postfix = new ArrayList<Token>();
        for (Token token : infix) {
            if ((token.getValue().length() == 1) && token.getTokenType().hasFlag()) {
                while (!operatorStack.empty() &&
                        !lowerPrecedence(operatorStack.peek(), token)) {
                    postfix.add(operatorStack.pop());
                }

                if (token.getTokenType().equals(TokenType.RIGHT_BRACKET)) {
                    Token operator = operatorStack.pop();
                    while (!operator.getTokenType().equals(TokenType.LEFT_BRACKET)) {
                        postfix.add(operator);
                        operator = operatorStack.pop();
                    }
                } else {
                    operatorStack.push(token);
                }
            } else {
                postfix.add(token);
            }
        }
        while (!operatorStack.empty()) {
            postfix.add(operatorStack.pop());
        }
        return postfix;
    }

    private static void convert(List<Token> infix) {
        if (infix.get(0).getTokenType().equals(TokenType.SUB)) {
            infix.add(0, new Token(0, "0", TokenType.INT));
            for (int i = 1; i < infix.size(); i++) {
                infix.get(i).setPosition(infix.get(i).getPosition() + 1);
            }
        }
        for (int i = 0; i < infix.size(); i++) {
            if (infix.get(i).getTokenType().equals(TokenType.LEFT_BRACKET)) {
                if (infix.get(i + 1).getTokenType().equals(TokenType.SUB)) {
                    infix.add((i + 1), new Token(i + 2, "0", TokenType.INT));
                    for (int j = i + 2; j < infix.size(); j++) {
                        infix.get(j).setPosition(infix.get(j).getPosition() + 1);
                    }
                }
            }
        }
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
        return new Token(-1, result + "", TokenType.FLOAT);
    }

    public static String evaluate(List<Token> tokens) {
        Stack<Token> stack = new Stack<Token>();
        Token op1;
        Token op2;
        Token result;
        for (Token token : tokens) {
            if (token.getTokenType().hasFlag()) {
                op2 = stack.pop();
                op1 = stack.pop();
                result = evalSingleOperation(token.getTokenType(), op1, op2);
                stack.push(result);
            } else {
                stack.push(token);
            }
        }
        return stack.pop().getValue();
    }
}
