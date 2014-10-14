package main.java.server.resources.read;

import main.java.dto.TransferObject;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.ExperimentIn;
import main.java.server.util.ResourceExceptionHandling;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
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
public class ExperimentsUsingRead extends ServerResource {

    @Get
    public Representation experimentUsingRead() throws SQLException, ParserConfigurationException, IOException {

        int queryID = Integer.parseInt(this.getAttribute("id"));
        List<TransferObject> listOfExperiments = null;

        try {

            ExperimentIn experimentIn = new ExperimentIn("Read", queryID);
            listOfExperiments = experimentIn.getRelatedExperiments();

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

        DtoToXml dtoToXml = new DtoToXml(listOfExperiments);
        Document document = dtoToXml.createNewXMLDocument();

        DomRepresentation domRepresentation = new DomRepresentation();
        domRepresentation.setDocument(document);
        return domRepresentation;
    }
}
