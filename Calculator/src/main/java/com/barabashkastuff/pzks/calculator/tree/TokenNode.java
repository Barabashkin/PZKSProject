package com.barabashkastuff.pzks.calculator.tree;

/**
 * Created by insane on 26.10.14.
 * https://gitlab.com/insanejdm7/pzks
 */
public class TokenNode extends BinaryNode {
    @Override
    public String toString() {
        return element.getValue();
    }
}
