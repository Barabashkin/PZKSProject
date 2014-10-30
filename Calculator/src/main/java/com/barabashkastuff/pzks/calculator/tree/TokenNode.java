package com.barabashkastuff.pzks.calculator.tree;


import com.barabashkastuff.pzks.calculator.domain.Token;

/**
 * Created by insane on 26.10.14.
 * https://gitlab.com/insanejdm7/pzks
 */
public class TokenNode extends BinaryNode<Token> {
    @Override
    public String toString() {
        return element.getValue();
    }
}
