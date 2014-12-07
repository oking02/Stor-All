package main.java.server.resources.project;

import main.java.dto.Experiment;
import main.java.dto.TransferObject;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.ExperimentIn;
import main.java.server.representations.dtotojson.ExperimentJsonRepresentation;
import main.java.server.responce.ResponseBuilder;
import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;

import java.sql.SQLException;
import java.util.List;

import static main.java.server.responce.ResourceExceptionHandling.*;

/**
 * Created by oking on 02/10/14.
 */
public class ProjectExperimentsResource extends ServerResource {


    @Get("?xml")
    public Representation experimentUnderProject() {

        int queryID = Integer.parseInt(this.getAttribute("id"));
        List<TransferObject> listOfExperiments = null;

        DomRepresentation domRepresentation = null;
        try {

            ExperimentIn experimentIn = new ExperimentIn("Project", queryID);
            listOfExperiments = experimentIn.getRelatedExperiments();

            DtoToXml dtoToXml = new DtoToXml(listOfExperiments);
            Document document = dtoToXml.createNewXMLDocument();

            domRepresentation = new DomRepresentation();
            domRepresentation.setDocument(document);

        } catch (Exception e) {
            exceptionHandling(e, this);
        }


        return domRepresentation;
    }

    @Get("?json")
    public JsonRepresentation getExpInJson() throws JSONException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        int queryID = Integer.parseInt(this.getAttribute("id"));
        List<TransferObject> listOfExperiments = null;
        ExperimentIn experimentIn = null;
        ExperimentJsonRepresentation experimentJsonRepresentation;

        try {

            experimentIn = new ExperimentIn("Project", queryID);
            listOfExperiments = experimentIn.getRelatedExperiments();
            experimentJsonRepresentation = new ExperimentJsonRepresentation(listOfExperiments);


        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        return experimentJsonRepresentation.getJsonRepresentation();

    }
}
