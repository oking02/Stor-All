package main.java.server.resources.system.info.counter;

import main.java.mysql.counters.ProjectCounter;
import main.java.mysql.counters.ReadCounter;
import main.java.server.responce.ResponseBuilder;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * Created by oking on 09/12/14.
 */
public class ReadCounterResource extends ServerResource{

    @Get("?json")
    public JsonRepresentation getReadCounter(){
        int queryID = Integer.parseInt(this.getAttribute("id"));
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {
            ReadCounter readCounter = new ReadCounter();
            int count = readCounter.getReadCount(queryID);
            JSONObject jsonObject = new JSONObject(count, new String[]{"Count"});
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
            return new JsonRepresentation(jsonObject);
        }catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }
}
