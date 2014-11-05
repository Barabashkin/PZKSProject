package com.barabashkastuff.pzks.calculator.tree;

import com.barabashkastuff.pzks.calculator.domain.Token;

/**
 * Created by insane on 26.10.14.
 * https://gitlab.com/insanejdm7/pzks
 */
public class BinaryNode {
    protected Token element;

    protected BinaryNode parent;

    protected BinaryNode left;

    protected BinaryNode right;

    public Token getElement() {
        return element;
    }

    public void setElement(Token element) {
        this.element = element;
    }

    public BinaryNode getParent() {
        return parent;
    }

    public void setParent(BinaryNode parent) {
        this.parent = parent;
    }

    public BinaryNode getLeft() {
        return left;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public BinaryNode getRight() {
        return right;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }

    public String toJson() {
        return "{\"" + left + "\":" + ((left == null) ? "null" : left.toJson()) + ", \"" + right + "\":" + ((right == null) ? "null" : right.toJson()) + "}";
    }
}
