package main.java.server.resources.project;

import main.java.dto.Project;
import main.java.dto.TransferObject;
import main.java.mysql.presenter.ProjectPresenter;
import main.java.mysql.remover.ProjectRemover;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.XMLToDto;
import main.java.server.util.GenericExporter;
import main.java.server.util.ResourceExceptionHandling;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
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
public class SingleProjectResource extends ServerResource {

    @Get
    public Representation getProject() throws SQLException, ParserConfigurationException, IOException {

        int queryID = Integer.parseInt(this.getAttribute("id"));
        ProjectPresenter projectPresenter = new ProjectPresenter();

        List<TransferObject> list = null;
        try {

            list = projectPresenter.getProject(queryID);

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

        DtoToXml dtoToXml = new DtoToXml(list);
        Document document = dtoToXml.createNewXMLDocument();

        DomRepresentation domRepresentation = new DomRepresentation();
        domRepresentation.setDocument(document);
        return domRepresentation;
    }

    @Put
    public void exportProject(Representation representation){

        try {

            GenericExporter genericExporter = new GenericExporter();
            genericExporter.export(representation);

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

    }


    @Delete
    public void deleteProject() throws IOException, ClassNotFoundException, SQLException {

        int queryID = Integer.parseInt(this.getAttribute("id"));
        ProjectPresenter projectPresenter = new ProjectPresenter();

        try {

            List<TransferObject> list = projectPresenter.getProject(queryID);

            for (TransferObject transferObject : list) {
                ProjectRemover projectRemover = new ProjectRemover((Project) transferObject);
                projectRemover.remove();
            }

        }catch (Exception e){
            exceptionHandling(e, this);
        }
    }
}
