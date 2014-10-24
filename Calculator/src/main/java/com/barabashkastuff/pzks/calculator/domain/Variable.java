package com.barabashkastuff.pzks.calculator.domain;

/**
 * Variable Class
 *
 * @author Andrew S. Slepakurov
 * @version 24/10/2014
 */
public class Variable {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
