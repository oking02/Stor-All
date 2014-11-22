package main.java.server.resources.project;

import main.java.dto.Export;
import main.java.dto.Project;
import main.java.dto.TransferObject;
import main.java.fileutils.ExportToCSV;
import main.java.mysql.builder.ProjectBuilder;
import main.java.mysql.presenter.ProjectPresenter;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.representations.ProjectJsonResource;
import main.java.server.responce.ResponseBuilder;
import main.java.server.util.AddResponceHeaders;
import org.json.JSONObject;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;
import org.w3c.dom.Document;

import java.util.List;

import static main.java.server.responce.ResourceExceptionHandling.exceptionHandling;

/**
 * Created by oking on 23/09/14.
 */
public class ProjectResource extends ServerResource {

    @Get("?xml")
    public Representation getProjects() {


        List<TransferObject> listOfExperiments = null;

        DomRepresentation domRepresentation = null;
        try {

            domRepresentation = new DomRepresentation();
            ProjectPresenter projectPresenter = new ProjectPresenter();
            listOfExperiments = projectPresenter.createListOfAllProjects();
            DtoToXml toXmlDocument = new DtoToXml(listOfExperiments);
            Document document = toXmlDocument.createNewXMLDocument();
            domRepresentation.setDocument(document);

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

        return domRepresentation;
    }

    @Get("?json")
    public Representation getProjectJson() {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        ProjectJsonResource projectJsonResource = null;

        try {

            ProjectPresenter projectPresenter = new ProjectPresenter();
            List<TransferObject> listOfProjects = projectPresenter.createListOfAllProjects();
            projectJsonResource = new ProjectJsonResource(listOfProjects);

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        return projectJsonResource.getJsonRepresentation();
    }

    @Post("?xml")
    public void addProject(Representation representation){

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        DomRepresentation domRepresentation = new DomRepresentation(representation);

        try {

            Document document = domRepresentation.getDocument();

            XMLToDto xmlToDto = new XMLToDto(document, Project.class);
            List<TransferObject> list = xmlToDto.convertToTransferObject();

            for (TransferObject p : list) {
                Project project = (Project) p;
                project.id = IDGenerator.getUniqueID("Project");
                ProjectBuilder projectBuilder = new ProjectBuilder(project);
                projectBuilder.build();
            }
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }

    @Post("?json")
    public void addProjectJson(JsonRepresentation representation) {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {

            JSONObject jsonObject = representation.getJsonObject();

            String owner = jsonObject.getString("owner");
            int id = IDGenerator.getUniqueID("Project");

            Project project = new Project(id, owner);
            ProjectBuilder projectBuilder = new ProjectBuilder(project);
            projectBuilder.build();

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }

    @Post("?file")
    public void exportToFile(Representation representation) {
        DomRepresentation domRepresentation = new DomRepresentation(representation);
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {

            Document document = domRepresentation.getDocument();
            XMLToDto xmlToDto = new XMLToDto(document, Export.class);
            List<TransferObject> listOfExportObjects = xmlToDto.convertToTransferObject();

            ProjectPresenter projectPresenter = new ProjectPresenter();
            List<TransferObject> listOfExperiment = projectPresenter.createListOfAllProjects();

            for (TransferObject transferObject : listOfExportObjects) {
                Export export = (Export) transferObject;
                ExportToCSV exportToCSV = new ExportToCSV(export.locationToExportTo, listOfExperiment);
                exportToCSV.createCSV(export.objectType);
            }
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }

}
