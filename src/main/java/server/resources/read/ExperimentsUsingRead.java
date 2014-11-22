package main.java.server.resources.read;

import main.java.dto.TransferObject;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.ExperimentIn;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;

import java.util.List;

import static main.java.server.responce.ResourceExceptionHandling.*;

/**
 * Created by oking on 02/10/14.
 */
public class ExperimentsUsingRead extends ServerResource {

    @Get
    public Representation experimentUsingRead() {

        int queryID = Integer.parseInt(this.getAttribute("id"));
        List<TransferObject> listOfExperiments = null;

        DomRepresentation domRepresentation = null;
        try {

            ExperimentIn experimentIn = new ExperimentIn("Read", queryID);
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
}
