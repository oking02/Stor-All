package resourcetest;

import main.java.server.resources.experiment.ExperimentResource;
import org.json.JSONArray;
import org.junit.Test;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.w3c.dom.Document;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by oking on 18/11/14.
 */
public class ExperimentResourceTest {


    private Context context = mock(Context.class);
    private Request request = mock(Request.class);
    private Response response = mock(Response.class);
    private ExperimentResource experimentResource = new ExperimentResource();

    @Test
    public void getExperimentJsonTest() throws Exception {

        when(response.getAttributes()).thenCallRealMethod();


        experimentResource.init(context, request, response);
        JsonRepresentation jsonRepresentation = experimentResource.getExpJson();
        JSONArray jsonArray = jsonRepresentation.getJsonArray();
        assertTrue(jsonArray.length() != 0);
    }

    @Test
    public void getExperimentsXML() throws IOException {

        when(response.getAttributes()).thenCallRealMethod();
        experimentResource.init(context, request, response);

        DomRepresentation representation = (DomRepresentation) experimentResource.getExperiments();
        Document document = representation.getDocument();

        assertTrue(document.getDocumentElement().getTagName().equals("Experiments"));

    }
}
