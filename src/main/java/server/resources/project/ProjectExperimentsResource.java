package main.java.server.resources.project;

import main.java.dto.TransferObject;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.ExperimentIn;
import main.java.server.util.ResourceExceptionHandling;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.server.util.ResourceExceptionHandling.*;

/**
 * Created by oking on 02/10/14.
 */
public class ProjectExperimentsResource extends ServerResource {


    @Get
    public Representation experimentUnderProject() throws ParserConfigurationException, IOException {

        int queryID = Integer.parseInt(this.getAttribute("id"));
        List<TransferObject> listOfExperiments = null;

        try{

            ExperimentIn experimentIn = new ExperimentIn("Project", queryID);
            listOfExperiments = experimentIn.getRelatedExperiments();

        }catch (Exception e){
            exceptionHandling(e, this);
        }

        DtoToXml dtoToXml = new DtoToXml(listOfExperiments);
        Document document = dtoToXml.createNewXMLDocument();

        DomRepresentation domRepresentation = new DomRepresentation();
        domRepresentation.setDocument(document);
        return domRepresentation;
    }
}
