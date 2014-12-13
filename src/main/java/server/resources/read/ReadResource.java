package main.java.server.resources.read;

import main.java.dto.Export;
import main.java.dto.Read;
import main.java.dto.TransferObject;
import main.java.fileutils.ExportToCSV;
import main.java.mysql.builder.ReadBuilder;
import main.java.mysql.presenter.ReadPresenter;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.representations.dtotojson.ReadJsonRepresentation;
import main.java.server.responce.ResponseBuilder;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by oking on 02/10/14.
 */
public class ReadResource extends ServerResource {

    @Get("?xml")
    public Representation getReads() throws IOException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        List<TransferObject> listOfReads = null;
        DomRepresentation domRepresentation = null;
        try {

            ReadPresenter readPresenter = new ReadPresenter();
            listOfReads = readPresenter.createListOfAllReads();

            DtoToXml dtoToXml = new DtoToXml(listOfReads);
            Document document = dtoToXml.createNewXMLDocument();

            domRepresentation = new DomRepresentation();
            domRepresentation.setDocument(document);

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }


        return domRepresentation;
    }

    @Get("?json")
    public Representation getReadJson() throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        ReadJsonRepresentation readJsonRepresentation = null;
        try {

            ReadPresenter readPresenter = new ReadPresenter();
            List<TransferObject> listOfProjects = readPresenter.createListOfAllReads();
            readJsonRepresentation = new ReadJsonRepresentation(listOfProjects);

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

        return readJsonRepresentation.getJsonRepresentation();
    }

    @Post("?xml")
    public String addRead(Representation representation) throws IOException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        DomRepresentation domRepresentation = new DomRepresentation(representation);

        String newReads = "";
        try {

            Document document = domRepresentation.getDocument();

            XMLToDto xmlToDto = new XMLToDto(document, Read.class);
            List<TransferObject> listOfRead = xmlToDto.convertToTransferObject();

            for (TransferObject transferObject : listOfRead) {
                Read read = (Read) transferObject;
                read.id = IDGenerator.getUniqueID("Read");
                ReadBuilder readBuilder = new ReadBuilder(read);
                readBuilder.build();
                newReads = newReads + "Read: " + read.id;
            }

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

        return newReads;
    }

    @Post("?json")
    public void addReadJson(JsonRepresentation representation) throws IOException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {

            JSONObject jsonObject = representation.getJsonObject();

            String locationOfReadData = jsonObject.getString("locationOfReadData");
            int id = IDGenerator.getUniqueID("Read");

            Read read = new Read(id, locationOfReadData);
            ReadBuilder readBuilder = new ReadBuilder(read);
            readBuilder.build();

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }


    @Post("?file")
    public void exportToFile(Representation representation) throws IOException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        DomRepresentation domRepresentation = new DomRepresentation(representation);
        try {
            Document document = domRepresentation.getDocument();

            XMLToDto xmlToDto = new XMLToDto(document, Export.class);
            List<TransferObject> listOfExportObjects = xmlToDto.convertToTransferObject();

            ReadPresenter readPresenter = new ReadPresenter();
            List<TransferObject> listOfExperiment = readPresenter.createListOfAllReads();

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
