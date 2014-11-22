package main.java.server.resources;

import main.java.dto.Experiment;
import main.java.server.util.AddResponceHeaders;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.json.JsonpRepresentation;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oking on 20/10/14.
 */
public class TestHTMLResource extends ServerResource {

    @Post("form")
    public void formTest(Form form){

    }

    @Get("?txt")
    public Representation get(){



        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");


        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        responseHeaders.add(new Header("Access-Control-Allow-Methods", "POST,OPTIONS"));
        responseHeaders.add(new Header("Access-Control-Allow-Headers", "Content-Type"));
        responseHeaders.add(new Header("Access-Control-Allow-Credentials", "false"));
        responseHeaders.add(new Header("Access-Control-Max-Age", "60"));


        Representation representation = new StringRepresentation("Hello World");


       return representation;

    }

    @Get("json:json")
    public Representation getJson(){

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        Experiment experiment = new Experiment(1,1,1);

        String[] names = new String[]{"id", "projectID", "readID"};

        Map<String, String> expMap = new HashMap<>();
        expMap.put("id", "1");
        expMap.put("projectID", "1");
        expMap.put("readID", "1");

        JSONObject newJson = new JSONObject(experiment, names);

        JsonRepresentation jsonRepresentation = new JsonRepresentation(newJson);
        return jsonRepresentation;
    }

    @Options
    public Representation options(){
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        return null;
    }
}
