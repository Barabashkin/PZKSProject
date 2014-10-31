package com.barabashkastuff.pzks.calculator.tree;

import com.barabashkastuff.pzks.calculator.domain.Token;

import java.io.IOException;
import java.io.OutputStreamWriter;

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

    public void printTree(OutputStreamWriter out) throws IOException {
        if (right != null) {
            right.printTree(out, true, "");
        }
        printNodeValue(out);
        if (left != null) {
            left.printTree(out, false, "");
        }

    }

    private void printNodeValue(OutputStreamWriter out) throws IOException {
        if (element == null) {
            out.write("<null>");
        } else {
            out.write(element.getValue());
        }
        out.write('\n');
    }

    // use string and not stringbuffer on purpose as we need to change the indent at each recursion
    private void printTree(OutputStreamWriter out, boolean isRight, String indent) throws IOException {
        if (right != null) {
            right.printTree(out, true, indent + (isRight ? "        " : " |      "));
        }
        out.write(indent);
        if (isRight) {
            out.write(" /");
        } else {
            out.write(" \\");
        }
        out.write("----- ");
        printNodeValue(out);
        if (left != null) {
            left.printTree(out, false, indent + (isRight ? " |      " : "        "));
        }
    }
}
