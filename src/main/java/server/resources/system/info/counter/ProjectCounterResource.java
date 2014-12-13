package main.java.server.resources.system.info.counter;

import main.java.mysql.counters.ProjectCounter;
import main.java.server.responce.ResponseBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oking on 09/12/14.
 */
public class ProjectCounterResource extends ServerResource {

    @Get("?json")
    public JsonRepresentation getProjectCounter(){
        int queryID = Integer.parseInt(this.getAttribute("id"));
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {
            ProjectCounter projectCounter = new ProjectCounter();
            int count = projectCounter.getProjectCount(queryID);

            Map<String, Integer> countMap = new HashMap<>();
            countMap.put("count", count);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(new JSONObject(countMap));
            //JSONObject jsonObject = new JSONObject(countMap);

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
            return new JsonRepresentation(jsonArray);
        }catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }
}
