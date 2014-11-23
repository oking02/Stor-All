package main.java.server.resources.system.help;

import main.java.server.responce.AddResponceHeaders;
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
public class HelpResource extends ServerResource {

    @Get("?howto")
    public Representation getHowToPage(){
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/HelpFiles/HowTo.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?colltype")
    public Representation getCollTypePage(){
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/HelpFiles/CollectionTypes.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

}
