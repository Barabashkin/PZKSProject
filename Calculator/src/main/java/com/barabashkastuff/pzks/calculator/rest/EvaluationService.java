package com.barabashkastuff.pzks.calculator.rest;

import com.barabashkastuff.pzks.calculator.analyzer.LexicalAnalyzer;
import com.barabashkastuff.pzks.calculator.analyzer.SyntaxAnalyzer;
import com.barabashkastuff.pzks.calculator.domain.Token;
import com.barabashkastuff.pzks.calculator.domain.TokenType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EvaluationService Class
 *
 * @author barabashka
 * @version 03-Oct-14
 */
@Path("/pzks")
@Component
public class EvaluationService {
    @Autowired
    private LexicalAnalyzer lexicalAnalyzer;
//    private LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
    @Autowired
    private SyntaxAnalyzer syntaxAnalyzer;
//    private SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();


    @GET
    @Path("/status")
    public Response getServiceStatus() {
        String result = "PZKS Rest service is working!\n";
        return Response.status(200).entity(result).build();
    }


    @POST
    @Path("/calculate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculate(String jsonRequest) {
        String jsonResponse;
        JsonElement jsonElement = (new Gson()).fromJson(jsonRequest, JsonElement.class).getAsJsonObject().get("request");
        if (jsonElement == null) {
            return Response.status(500).entity("No JSON!").build();
        }
        JsonObject json = jsonElement.getAsJsonObject();
        if (!json.has("expression")) {
            return Response.status(500).entity("No expression provided!").build();
        }
        String expression = json.get("expression").getAsString();
        // TODO: separate class
        Map<String, String> variables = new HashMap<String, String>();
        String variablesString = "";
        if(json.has("variables") && json.get("variables").getAsString()!=""){
            variablesString = json.get("variables").getAsString();
            for (String variablePair : variablesString.split(";")) {
                variables.put(variablePair.split("=")[0], variablePair.split("=")[1]);
            }
        }
        // /TODO: separate class
        lexicalAnalyzer.setExpression(expression);
        syntaxAnalyzer.setVariables(variables);
        List<Token> tokens = new ArrayList<Token>();
        try {
            for (; ; ) {
                Token token = lexicalAnalyzer.getNextToken();
                if (token.getTokenType() == TokenType.EOE) break;
                tokens.add(token);
            }
            syntaxAnalyzer.setTokens(tokens);
            double result = syntaxAnalyzer.parse();
            StringBuilder sb = new StringBuilder();
            for (Token token : syntaxAnalyzer.getPostfix()) {
                sb.append(token.getValue() + " ");
            }
            jsonResponse = (new Gson()).fromJson("{\"expression\":\"" + expression + "\"" +
                    ", \"variables\":\"" + variablesString + "\"" +
                    ", \"result\":\"" + result + "\"" +
                    ", \"postfix\":\"" + sb.toString() + "\"" +
                    ", \"code\":\"" + 0 + "\"" +
                    "}", JsonElement.class).toString();
            return Response.ok(jsonResponse).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            jsonResponse = (new Gson()).fromJson("{\"exception\":\"" + e.toString() + "\"" +
                    ", \"code\":\"" + 1 + "\"" +
                    "}", JsonElement.class).toString();
            return Response.status(500).entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
        }
    }

//    public static void main(String[] args) {
//        EvaluationService evaluationService = new EvaluationService();
//        String expression = "{\"request\":{\"expression\":\"(-2xy4+15.34)(-a+(-3))\",\"variables\":\"a=4\"}}";
//        Response calculate = evaluationService.calculate(expression);
//        System.out.println(calculate.getEntity().toString());
//    }
//    /2xy4+*1,5.5.4)(-$a+()/
}
