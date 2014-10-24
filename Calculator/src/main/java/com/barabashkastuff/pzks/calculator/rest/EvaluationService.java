package com.barabashkastuff.pzks.calculator.rest;

import com.barabashkastuff.pzks.calculator.analyzer.LexicalAnalyzer;
import com.barabashkastuff.pzks.calculator.analyzer.SyntaxAnalyzer;
import com.barabashkastuff.pzks.calculator.domain.Expression;
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
        Expression expression = new Expression();
        JsonElement jsonElement = (new Gson()).fromJson(jsonRequest, JsonElement.class).getAsJsonObject().get("request");

        if (jsonElement == null) {
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("No JSON!").build();
        }

        JsonObject jsonBody = jsonElement.getAsJsonObject();
        if (!jsonBody.has("expression")) {
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("No expression provided!").build();
        }

        String expressionBody = jsonBody.get("expression").getAsString();

        Map<String, String> variables = new HashMap<String, String>();
        String variablesString = "";
        if(jsonBody.has("variables") && jsonBody.get("variables").getAsString()!=""){
            variablesString = jsonBody.get("variables").getAsString();
            for (String variablePair : variablesString.split(";")) {
                variables.put(variablePair.split("=")[0], variablePair.split("=")[1]);
            }
        }

            jsonResponse = (new Gson()).fromJson("{\"expression\":\"" + expression + "\"" +
                    ", \"variables\":\"" + variablesString + "\"" +
                    ", \"result\":\"" + result + "\"" +
                    ", \"postfix\":\"" + sb.toString() + "\"" +
                    ", \"code\":\"" + 0 + "\"" +
                    "}", JsonElement.class).toString();
            return Response.ok(jsonResponse).header("Access-Control-Allow-Origin", "*").type(MediaType.APPLICATION_JSON).build();
//        } catch (Exception e) {
//            jsonResponse = (new Gson()).fromJson("{\"exception\":\"" + e.toString() + "\"" +
//                    ", \"code\":\"" + 1 + "\"" +
//                    "}", JsonElement.class).toString();
//            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
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
