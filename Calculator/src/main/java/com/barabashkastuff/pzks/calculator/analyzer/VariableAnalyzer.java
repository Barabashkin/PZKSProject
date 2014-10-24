package com.barabashkastuff.pzks.calculator.analyzer;

import java.util.Map;

/**
 * VariableAnalyzer Class
 *
 * @author Andrew S. Slepakurov
 * @version 24/10/2014
 */
public class VariableAnalyzer {
    private String body;
    private Map<String, String> variables;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public void parse(){

    }
}
