package main.java.server.resources.project;

import main.java.dto.Project;
import main.java.dto.TransferObject;
import main.java.fileutils.NoteController;
import main.java.mysql.presenter.ProjectPresenter;
import main.java.mysql.remover.ProjectRemover;
import main.java.mysql.utils.DtoToXml;
import main.java.server.representations.ProjectJsonResource;
import main.java.server.responce.ResponseBuilder;
import main.java.server.util.AddResponceHeaders;
import main.java.server.util.GenericExporter;
import org.json.JSONObject;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.util.Series;
import org.w3c.dom.Document;

import java.util.List;

import static main.java.server.responce.ResourceExceptionHandling.*;

/**
 * Created by oking on 02/10/14.
 */
public class SingleProjectResource extends ServerResource {

    @Get("?xml")
    public Representation getProject() {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        int queryID = Integer.parseInt(this.getAttribute("id"));

        List<TransferObject> list = null;
        DomRepresentation domRepresentation = null;
        try {

            ProjectPresenter projectPresenter = new ProjectPresenter();
            list = projectPresenter.getProject(queryID);

            DtoToXml dtoToXml = new DtoToXml(list);
            Document document = dtoToXml.createNewXMLDocument();

            domRepresentation = new DomRepresentation();
            domRepresentation.setDocument(document);

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        return domRepresentation;
    }

    @Get("?json")
    public Representation getProjectJson() throws Exception {
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        ProjectJsonResource projectJsonResource = null;
        try {

            int queryExpID = Integer.parseInt(this.getAttribute("id"));
            ProjectPresenter projectPresenter = new ProjectPresenter();
            List<TransferObject> listOfProject = projectPresenter.getProject(queryExpID);

            projectJsonResource = new ProjectJsonResource(listOfProject);

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        return projectJsonResource.getJsonRepresentation();
    }

    @Post("?note")
    public void addNotes(JsonRepresentation representation) {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        int queryExpID = Integer.parseInt(this.getAttribute("id"));

        try {

            JSONObject jsonObject = representation.getJsonObject();
            String note = jsonObject.getString("Note");

            NoteController noteController = new NoteController(queryExpID, "Project");
            noteController.addNotes(note);

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }

    @Put
    public void exportProject(Representation representation){

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        try {

            GenericExporter genericExporter = new GenericExporter();
            genericExporter.export(representation);
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }


    @Delete
    public void deleteProject(){

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        int queryID = Integer.parseInt(this.getAttribute("id"));

        try {

            ProjectPresenter projectPresenter = new ProjectPresenter();
            List<TransferObject> list = projectPresenter.getProject(queryID);

            for (TransferObject transferObject : list) {
                ProjectRemover projectRemover = new ProjectRemover((Project) transferObject);
                projectRemover.remove();
            }
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        }catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }
}
