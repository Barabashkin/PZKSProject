package com.barabashkastuff.pzks.calculator.rest;

import com.barabashkastuff.pzks.calculator.domain.Expression;
import com.barabashkastuff.pzks.calculator.exception.LexicalException;
import com.barabashkastuff.pzks.calculator.exception.SyntaxListException;
import com.barabashkastuff.pzks.calculator.exception.VariableException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.binary.Base64;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    private ObjectFactory<Expression> expressionFactory;

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
        Expression expression = expressionFactory.getObject();
        JsonElement jsonElement = (new Gson()).fromJson(jsonRequest, JsonElement.class).getAsJsonObject().get("request");

        if (jsonElement == null) {
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("No JSON!").build();
        }

        JsonObject jsonBody = jsonElement.getAsJsonObject();
        if (!jsonBody.has("expression")) {
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("No expression provided!").build();
        }
        String expressionBody = jsonBody.get("expression").getAsString();
        String varBody = "";
        if (jsonBody.has("variables")) {
            varBody = jsonBody.get("variables").getAsString();
        }
        expression.setBody(expressionBody);
        expression.setVarBody(varBody);
        try {
            expression.evaluate();
            jsonResponse = (new Gson()).fromJson("{\"expression\":\"" + expression.getBody() + "\"" +
                    ", \"variables\":\"" + expression.getVarBody() + "\"" +
                    ", \"result\":\"" + expression.getResult() + "\"" +
                    ", \"postfix\":\"" + expression.getPostfix() + "\"" +
//                    ", \"tree\":\"" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(expression.getTreePic().getBytes()))) + "\"" +
                    ", \"tree\":" + expression.getTree().toJson() + "" +
                    ", \"code\":\"" + 0 + "\"" +
                    "}", JsonElement.class).toString();
        } catch (LexicalException e) {
            jsonResponse = (new Gson()).fromJson("{\"exception\":\"" + e.toString() + "\"" +
                    ", \"expression\":\"" + expression.getBody() + "\"" +
                    ", \"code\":\"" + 1 + "\"" +
                    "}", JsonElement.class).toString();
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
        } catch (VariableException e) {
            jsonResponse = (new Gson()).fromJson("{\"exception\":\"" + e.toString() + "\"" +
                    ", \"expression\":\"" + expression.getBody() + "\"" +
                    ", \"code\":\"" + 2 + "\"" +
                    "}", JsonElement.class).toString();
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
        } catch (SyntaxListException e) {
            jsonResponse = (new Gson()).fromJson("{\"exception\":\"" + e.toString() + "\"" +
                    ", \"expression\":\"" + expression.getBody() + "\"" +
                    ", \"code\":\"" + 3 + "\"" +
                    "}", JsonElement.class).toString();
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            jsonResponse = (new Gson()).fromJson("{\"exception\":\"" + e.toString() + "\"" +
                    ", \"expression\":\"" + expression.getBody() + "\"" +
                    ", \"code\":\"" + 4 + "\"" +
                    "}", JsonElement.class).toString();
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
        }
        return Response.ok(jsonResponse).header("Access-Control-Allow-Origin", "*").type(MediaType.APPLICATION_JSON).build();
    }
}

//    /2xy4+*1,5.5.4)(-$a+()/