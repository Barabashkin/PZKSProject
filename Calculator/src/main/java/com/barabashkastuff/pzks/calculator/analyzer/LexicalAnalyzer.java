package com.barabashkastuff.pzks.calculator.analyzer;

import com.barabashkastuff.pzks.calculator.domain.Token;
import com.barabashkastuff.pzks.calculator.domain.TokenType;
import com.barabashkastuff.pzks.calculator.exception.LexicalException;

/**
 * LexicalAnalyzer Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/09/2014
 */
public class LexicalAnalyzer {
    private int currLinePosition = 1;
    private int currAbsolutePosition = 0;
    private String expression = null;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public int getLength() {
        return expression.length();
    }

    public Token lexicProcessor() throws LexicalException {
        StringBuilder buffer = new StringBuilder();
        for (; hasNextChar(); currAbsolutePosition++) {
            char currChar = expression.charAt(currAbsolutePosition);
            switch (currAbsolutePosition) {
                case ' ':
                    currLinePosition++;
                    continue;

                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    boolean isFloat = false;
                    TokenType type = TokenType.INT;
                    for (int tmpPos = currAbsolutePosition; tmpPos < getLength(); tmpPos++) {
                        currChar = expression.charAt(tmpPos);
                        if (currChar == '.') {
                            if (!isFloat) {
                                isFloat = true;
                                type = TokenType.FLOAT;
                                buffer.append((buffer.length() == 0) ? 0 + currChar : currChar);
                            } else {
                                throw new LexicalException("Wrong float expression at " + currLinePosition);
                            }
                        } else if (currChar >= '0' && currChar <= '9') {
                            buffer.append(currChar);
                        } else {
                            break;
                        }
                    }
                    if (buffer.toString().endsWith(".")) {
                        throw new LexicalException("Wrong float expression at " + currLinePosition);
                    }
                    return new Token(currLinePosition, buffer.toString(), type);

                case '+':
                    return new Token(currLinePosition, currChar + "", TokenType.ADD);
                case '-':
                    return new Token(currLinePosition, currChar + "", TokenType.SUB);
                case '*':
                    return new Token(currLinePosition, currChar + "", TokenType.MULT);
                case '/':
                    return new Token(currLinePosition, currChar + "", TokenType.DIV);
                case '(':
                    return new Token(currLinePosition, currChar + "", TokenType.LEFT_BRACKET);
                case ')':
                    return new Token(currLinePosition, currChar + "", TokenType.RIGHT_BRACKET);
                default:

                    continue;
            }
        }
        return new Token(currLinePosition, "", TokenType.EOE);
    }

    public Token getNextToken() throws LexicalException {
        Token token = lexicProcessor();
        currLinePosition += token.getValue().length();
        return token;
    }

    public boolean hasNextChar() {
        return currAbsolutePosition + 1 != getLength();
    }

}
