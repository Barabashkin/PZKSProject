package com.barabashkastuff.pzks.calculator.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * SyntaxListException Class
 *
 * @author Andrew S. Slepakurov
 * @version 27/10/2014
 */
public class SyntaxListException extends Exception {
    private List<SyntaxException> exceptions;

    public SyntaxListException() {
        exceptions = new ArrayList<SyntaxException>();
    }

    public SyntaxListException(SyntaxException exception) {
        this();
        add(exception);
    }

    public List<SyntaxException> getExceptions() {
        return exceptions;
    }

    public void add(SyntaxException exception) {
        exceptions.add(exception);
    }

    public boolean hasException() {
        return exceptions.size() > 0;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Exception exception : exceptions) {
            sb.append(exception.getMessage()).append("; ");
        }
        return sb.toString();
    }
}
