package com.barabashkastuff.pzks.calculator.tree;

/**
 * Created by insane on 26.10.14.
 * https://gitlab.com/insanejdm7/pzks
 */
public class BinaryNode<T> {
    protected T element;

    protected BinaryNode<T> parent;

    protected BinaryNode<T> left;

    protected BinaryNode<T> right;

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public BinaryNode<T> getParent() {
        return parent;
    }

    public void setParent(BinaryNode<T> parent) {
        this.parent = parent;
    }

    public BinaryNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryNode<T> left) {
        this.left = left;
    }

    public BinaryNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }
}
