package main.java.server.resources.experiment;

import main.java.dto.Experiment;
import main.java.dto.Export;
import main.java.dto.TransferObject;
import main.java.fileutils.ExportToCSV;
import main.java.mysql.builder.ExperimentBuilder;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.utils.DtoToXml;

import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.representations.ExperimentJsonRepresentation;
import main.java.server.util.AddResponceHeaders;
import main.java.server.responce.ResourceExceptionHandling;
import main.java.server.responce.ResponseBuilder;
import org.json.JSONObject;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.util.Series;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by oking on 22/09/14.
 */
public class ExperimentResource extends ServerResource {



    @Get("?xml")
    public Representation getExperiments() throws IOException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        DomRepresentation domRepresentation = new DomRepresentation();

        try {

            ExperimentPresenter experimentPresenter = new ExperimentPresenter();
            List<TransferObject> listOfExperiments = experimentPresenter.createListOfAllExperiments();

            DtoToXml toXmlDocument = new DtoToXml(listOfExperiments);
            Document document = toXmlDocument.createNewXMLDocument();


            domRepresentation.setDocument(document);
            domRepresentation.setIndenting(true);

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
            return domRepresentation;

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }

    @Get ("?json")
    public JsonRepresentation getExpJson() throws Exception {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        ExperimentJsonRepresentation experimentJsonRepresentation = null;

        try {

            ExperimentPresenter experimentPresenter = new ExperimentPresenter();
            List<TransferObject> listOfExperiments = experimentPresenter.createListOfAllExperiments();
            experimentJsonRepresentation = new ExperimentJsonRepresentation(listOfExperiments);

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        return experimentJsonRepresentation.getJsonRepresentation();
    }

    @Post("?xml")
    public void addExperiment(Representation representation) throws IOException, ClassNotFoundException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        XMLToDto xmlToDto = new XMLToDto(document, Experiment.class);
        List<TransferObject> list = xmlToDto.convertToTransferObject();

        try {

            for (TransferObject experiment : list) {
                Experiment experiment1 = (Experiment) experiment;
                experiment1.id = IDGenerator.getUniqueID("Experiment");
                ExperimentBuilder experimentBuilder = new ExperimentBuilder(experiment1);
                experimentBuilder.build();
            }
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }

    @Post("?json")
    public void addExperimentUsingJson(JsonRepresentation representation) throws Exception {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        JSONObject jsonObject = representation.getJsonObject();

        String projectID = jsonObject.getString("projectID");
        String readID = jsonObject.getString("readID");
        int id = IDGenerator.getUniqueID("Experiment");

        try {

            Experiment experiment = new Experiment(id, Integer.parseInt(projectID), Integer.parseInt(readID));
            ExperimentBuilder experimentBuilder = new ExperimentBuilder(experiment);
            experimentBuilder.build();

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

    }

    @Post("?csv")
    public void exportCSV(Representation representation) throws Exception {


        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        try {

            XMLToDto xmlToDto = new XMLToDto(document, Export.class);
            List<TransferObject> listOfExportObjects = xmlToDto.convertToTransferObject();

            ExperimentPresenter experimentPresenter = new ExperimentPresenter();
            List<TransferObject> listOfExperiment = experimentPresenter.createListOfAllExperiments();

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
