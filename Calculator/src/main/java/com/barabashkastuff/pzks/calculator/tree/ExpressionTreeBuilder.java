package com.barabashkastuff.pzks.calculator.tree;


import com.barabashkastuff.pzks.calculator.domain.Token;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by insane on 26.10.14.
 * https://gitlab.com/insanejdm7/pzks
 */
@Component
@Scope("prototype")
public class ExpressionTreeBuilder {

    private List<Token> tokens;

    public ExpressionTreeBuilder(){}

    public ExpressionTreeBuilder(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> input) {
        this.tokens = input;
    }

    @SuppressWarnings("unchecked")
    public TokenNode build(){
        BinaryNode currentNode = new TokenNode();
        for (int index = 0; index < tokens.size(); index++) {
            final Token currentToken = tokens.get(index);
            switch (currentToken.getTokenType()) {
                case INT:
                case FLOAT:
                case ID:
                    createSubNode(currentToken, currentNode);
                    break;
                case LEFT_BRACKET:
                    currentNode = createSubNode(null, currentNode);
                    break;
                case RIGHT_BRACKET:
                    currentNode = currentNode.getParent();
                    break;
                case ADD:
                case SUB:
                case MULT:
                case DIV:
                    if (currentNode.getElement() == null) {
                        currentNode.setElement(currentToken);
                    } else {
                        BinaryNode newParent = new TokenNode();
                        newParent.setElement(currentToken);
                        if (currentNode.getParent() == null) {
                            currentNode.setParent(newParent);
                            newParent.setLeft(currentNode);
                        } else {
                            newParent.setLeft(currentNode);
                            if (currentNode.getParent().getLeft() == currentNode) {
                                currentNode.getParent().setLeft(newParent);
                            } else {
                                currentNode.getParent().setRight(newParent);
                            }
                            newParent.setParent(currentNode.getParent());
                            currentNode.setParent(newParent);
                        }
                        currentNode = newParent;
                    }
                    break;
            }
        }
        /*find root*/
        for (; currentNode.getParent() != null; ) {
            currentNode = currentNode.getParent();
        }
        return (TokenNode) currentNode;
    }

    @SuppressWarnings("unchecked")
    private BinaryNode createSubNode(Token token, BinaryNode parent) {
        final BinaryNode subNode = new TokenNode();
        subNode.setElement(token);
        subNode.setParent(parent);
        if (parent.getLeft() == null) {
            parent.setLeft(subNode);
        } else {
            parent.setRight(subNode);
        }
        return subNode;
    }
}
