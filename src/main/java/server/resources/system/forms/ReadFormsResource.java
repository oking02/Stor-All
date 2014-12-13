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
import java.io.IOException;

/**
 * Created by oking on 13/11/14.
 */
public class ReadFormsResource extends ServerResource {

    @Get("?addread")
    public Representation getAddReadForm() throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ReadFiles/AddReadForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?addreadnotes")
    public Representation getAddReadNotesForm() throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ReadFiles/AddReadNotesForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?findread")
    public Representation findReadForm() throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ReadFiles/FindReadForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?deleteread")
    public Representation deleteReadForm() throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/ReadFiles/DeleteReadForm.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }
}
