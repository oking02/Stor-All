package main.java.server.resources.read;

import main.java.dto.TransferObject;
import main.java.fileutils.NoteController;
import main.java.mysql.presenter.ReadPresenter;
import main.java.mysql.remover.ReadRemover;
import main.java.mysql.utils.DtoToXml;
import main.java.server.representations.dtotojson.ReadJsonRepresentation;
import main.java.server.responce.ResponseBuilder;
import main.java.server.util.GenericExporter;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.w3c.dom.Document;

import java.util.List;

/**
 * Created by oking on 02/10/14.
 */
public class SingleReadResource extends ServerResource {

    @Get("?xml")
    public Representation getRead() {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        int queryID = Integer.parseInt(this.getAttribute("id"));
        List<TransferObject> list = null;

        DomRepresentation domRepresentation = null;
        try {

            ReadPresenter readPresenter = new ReadPresenter();
            list = readPresenter.getRead(queryID);

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
    public Representation getReadJson() {
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        ReadJsonRepresentation readJsonRepresentation = null;
        try {

            int queryExpID = Integer.parseInt(this.getAttribute("id"));
            ReadPresenter readPresenter = new ReadPresenter();
            List<TransferObject> listOfRead = readPresenter.getRead(queryExpID);

            readJsonRepresentation = new ReadJsonRepresentation(listOfRead);

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        return readJsonRepresentation.getJsonRepresentation();
    }

    @Post("?note")
    public void addNotes(JsonRepresentation representation) {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {
            int queryExpID = Integer.parseInt(this.getAttribute("id"));

            JSONObject jsonObject = representation.getJsonObject();
            String note = jsonObject.getString("Note");

            NoteController noteController = new NoteController(queryExpID, "Read");
            noteController.addNotes(note);
        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

    }

    @Put
    public void exportRead(Representation representation){

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        try {

            GenericExporter genericExporter = new GenericExporter();
            genericExporter.export(representation);
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }

    @Delete
    public void deleteRead(){

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        int queryID = Integer.parseInt(this.getAttribute("id"));

        try {

            ReadPresenter readPresenter = new ReadPresenter();
            List<TransferObject> list = readPresenter.getRead(queryID);

            for (TransferObject transferObject : list) {
                ReadRemover readRemover = new ReadRemover((main.java.dto.Read) transferObject);
                readRemover.remove();
            }

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }
}
