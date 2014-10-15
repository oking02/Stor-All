package main.java.server.resources.project;

import main.java.dto.Experiment;
import main.java.dto.Export;
import main.java.dto.Project;
import main.java.dto.TransferObject;
import main.java.fileutils.ExportToCSV;
import main.java.mysql.builder.ExperimentBuilder;
import main.java.mysql.builder.ProjectBuilder;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.presenter.ProjectPresenter;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.util.ResourceExceptionHandling;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.server.util.ResourceExceptionHandling.*;

/**
 * Created by oking on 23/09/14.
 */
public class ProjectResource extends ServerResource {

    @Get
    public Representation getProjects() throws IOException, ParserConfigurationException {

        DomRepresentation domRepresentation = new DomRepresentation();
        List<TransferObject> listOfExperiments = null;

        try {

            ProjectPresenter projectPresenter = new ProjectPresenter();
            listOfExperiments = projectPresenter.createListOfAllProjects();

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

        DtoToXml toXmlDocument = new DtoToXml(listOfExperiments);
        Document document = toXmlDocument.createNewXMLDocument();
        domRepresentation.setDocument(document);

        return domRepresentation;
    }

    @Put
    public void addProject(Representation representation) throws ClassNotFoundException, IOException {

        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        XMLToDto xmlToDto = new XMLToDto(document, Project.class);
        List<TransferObject> list = xmlToDto.convertToTransferObject();

        try {

            for (TransferObject p : list) {
                Project project = (Project) p;
                project.id = IDGenerator.getUniqueID("Project");
                ProjectBuilder projectBuilder = new ProjectBuilder(project);
                projectBuilder.build();
            }

        }catch (Exception e){
            exceptionHandling(e, this);
        }
    }

    @Post
    public void exportToFile(Representation representation) throws Exception {
        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        XMLToDto xmlToDto = new XMLToDto(document, Export.class);
        List<TransferObject> listOfExportObjects = xmlToDto.convertToTransferObject();

        ProjectPresenter projectPresenter = new ProjectPresenter();
        List<TransferObject> listOfExperiment = projectPresenter.createListOfAllProjects();

        for (TransferObject transferObject : listOfExportObjects){
            Export export = (Export) transferObject;
            ExportToCSV exportToCSV = new ExportToCSV(export.locationToExportTo, listOfExperiment);
            exportToCSV.createCSV(export.objectType);
        }
    }

}
