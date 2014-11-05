package main.java.server.resources.read;

import main.java.dto.TransferObject;
import main.java.fileutils.NoteController;
import main.java.mysql.presenter.ProjectPresenter;
import main.java.mysql.presenter.ReadPresenter;
import main.java.mysql.remover.ReadRemover;
import main.java.mysql.utils.DtoToXml;
import main.java.server.util.AddResponceHeaders;
import main.java.server.util.GenericExporter;
import main.java.server.util.ResourceExceptionHandling;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.util.Series;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static main.java.server.util.ResourceExceptionHandling.*;

/**
 * Created by oking on 02/10/14.
 */
public class SingleReadResource extends ServerResource {

    @Get("?xml")
    public Representation getRead() throws ParserConfigurationException, IOException {

        int queryID = Integer.parseInt(this.getAttribute("id"));
        List<TransferObject> list = null;

        try {

            ReadPresenter readPresenter = new ReadPresenter();
            list = readPresenter.getRead(queryID);

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

        DtoToXml dtoToXml = new DtoToXml(list);
        Document document = dtoToXml.createNewXMLDocument();
        DomRepresentation domRepresentation = new DomRepresentation();
        domRepresentation.setDocument(document);
        return domRepresentation;

    }

    @Get("?json")
    public Representation getReadJson() throws Exception {
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        int queryExpID = Integer.parseInt(this.getAttribute("id"));
        ReadPresenter readPresenter = new ReadPresenter();
        List<TransferObject> listOfRead = readPresenter.getRead(queryExpID);

        JSONArray jsonArray = new JSONArray();
        String[] names = new String[]{"id"};

        for (TransferObject transferObject : listOfRead){
            JSONObject jsonObject1 = new JSONObject(transferObject, names);
            jsonArray.put(jsonObject1);
        }
        return new JsonRepresentation(jsonArray);
    }

    @Post("?note")
    public void addNotes(JsonRepresentation representation) throws JSONException, NoSuchFieldException, IOException {

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        int queryExpID = Integer.parseInt(this.getAttribute("id"));

        JSONObject jsonObject = representation.getJsonObject();
        String note = jsonObject.getString("Note");


        NoteController noteController = new NoteController(queryExpID, "Read");
        noteController.addNotes(note);

    }

    @Put
    public void exportRead(Representation representation){

        try {

            GenericExporter genericExporter = new GenericExporter();
            genericExporter.export(representation);

        } catch (Exception e){
            exceptionHandling(e, this);
        }
    }

    @Delete
    public void deleteRead(){

        int queryID = Integer.parseInt(this.getAttribute("id"));

        try {

            ReadPresenter readPresenter = new ReadPresenter();
            List<TransferObject> list = readPresenter.getRead(queryID);

            for (TransferObject transferObject : list) {
                ReadRemover readRemover = new ReadRemover((main.java.dto.Read) transferObject);
                readRemover.remove();
            }

        } catch (Exception e){
            exceptionHandling(e, this);
        }

    }
}
