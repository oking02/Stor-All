package main.java.server.resources.system.forms;

import main.java.server.responce.AddResponceHeaders;
import main.java.server.responce.ResponseBuilder;
import org.restlet.data.MediaType;
import org.restlet.engine.header.Header;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.io.File;

/**
 * Created by oking on 13/11/14.
 */
public class ProjectFormsResource extends ServerResource{

    @Get("?addproject")
    public Representation getAddProjectForm(){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ProjectFiles/AddProjectForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?addprojectnotes")
    public Representation getAddProjectNotesForm(){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ProjectFiles/AddProjectNotesForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?findproject")
    public Representation findProjectForm(){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ProjectFiles/FindProjectForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?deleteproject")
    public Representation deleteProjectForm(){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ProjectFiles/DeleteProjectForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }
}
