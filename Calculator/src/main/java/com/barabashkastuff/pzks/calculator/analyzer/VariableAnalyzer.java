package com.barabashkastuff.pzks.calculator.analyzer;

import com.barabashkastuff.pzks.calculator.exception.VariableException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * VariableAnalyzer Class
 *
 * @author Andrew S. Slepakurov
 * @version 24/10/2014
 */
@Component
@Scope("prototype")
public class VariableAnalyzer implements IProcessor {
//  Consume
    private String body;
//  Produce
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

    public void process() throws VariableException {
        try {
            Map<String, String> variables = new HashMap<String, String>();
            if (getBody().trim().replace(" ", "").equals("")) {
                variables = Collections.emptyMap();
            } else {
                for (String variablePair : body.split(";")) {
                    variables.put(variablePair.split("=")[0], variablePair.split("=")[1]);
                }
            }
            setVariables(variables);
        }catch (Exception e){
            throw new VariableException(e.getMessage());
        }
    }
}
