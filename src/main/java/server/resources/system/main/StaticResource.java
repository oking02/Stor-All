package main.java.server.resources.system.main;

import main.java.server.util.AddResponceHeaders;
import org.restlet.data.MediaType;
import org.restlet.engine.header.Header;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.io.File;

/**
 * Created by oking on 05/11/14.
 */
public class StaticResource extends ServerResource {

    @Get
    public Representation getIndexPage(){

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/index.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?js")
    public Representation getJSPage(){

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/scripts.js"), MediaType.APPLICATION_JAVASCRIPT);
        return fileRepresentation;
    }

    @Get("?home")
    public Representation getHomeScreen(){
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/home.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }


}
