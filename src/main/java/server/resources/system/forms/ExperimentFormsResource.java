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
public class ExperimentFormsResource extends ServerResource {

    @Get("?addexperiment")
    public Representation getAddExperimentForm(){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ExperimentFiles/AddExperimentForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?addanalysis")
    public Representation getAddAnalysisForm(){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ExperimentFiles/AddAnalysisForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?addexperimentnotes")
    public Representation getAddExperimentNotesForm(){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ExperimentFiles/AddExperimentNotesForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?findexperiment")
    public Representation findExperimentForm(){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ExperimentFiles/FindExperimentForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

}
