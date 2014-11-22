package resourcetest;

import main.java.server.resources.experiment.SingleExperimentResource;
import org.json.JSONArray;
import org.junit.Test;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ServerResource;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by oking on 18/11/14.
 */
public class SingleExperimentResourceTests {

    private Context context = mock(Context.class);
    private Request request = mock(Request.class);
    private Response response = mock(Response.class);
    private SingleExperimentResource singleExperimentResource = new SingleExperimentResource();

    @Test
    public void getExperimentJsonTest() throws Exception {

        when(response.getAttributes()).thenCallRealMethod();
        singleExperimentResource.init(context, request, response);
        System.out.println(singleExperimentResource.toString());
        //singleExperimentResource.setAttribute("id", "1");



        JsonRepresentation jsonRepresentation = (JsonRepresentation) singleExperimentResource.getExperimentJson();
        JSONArray jsonArray = jsonRepresentation.getJsonArray();
        assertTrue(jsonArray.length() != 0);

    }

    @Test
    public void getExperimentXML(){
        when(response.getAttributes()).thenCallRealMethod();
        singleExperimentResource.init(context, request, response);
    }
}
