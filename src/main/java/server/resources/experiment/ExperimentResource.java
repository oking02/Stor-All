package main.java.server.resources.experiment;

import main.java.dto.Experiment;
import main.java.dto.Export;
import main.java.dto.TransferObject;
import main.java.fileutils.ExportToCSV;
import main.java.mysql.builder.ExperimentBuilder;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.remover.ExperimentRemover;
import main.java.mysql.utils.DtoToXml;

import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.util.AddResponceHeaders;
import main.java.server.util.ResourceExceptionHandling;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.json.JsonpRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.util.Series;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by oking on 22/09/14.
 */
public class ExperimentResource extends ServerResource {



    @Get("?xml")
    public Representation getExperiments() throws IOException, SQLException, ParserConfigurationException {

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> listOfExperiments = null;

        try {

            listOfExperiments = experimentPresenter.createListOfAllExperiments();

        } catch (Exception e) {
            ResourceExceptionHandling.exceptionHandling(e, this);
        }

        DtoToXml toXmlDocument = new DtoToXml(listOfExperiments);
        Document document = toXmlDocument.createNewXMLDocument();

        DomRepresentation domRepresentation = new DomRepresentation();
        domRepresentation.setDocument(document);
        domRepresentation.setIndenting(true);
        return domRepresentation;
    }

    @Get ("?json")
    public JsonRepresentation getExpJson() throws Exception {

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> listOfExperiments = experimentPresenter.createListOfAllExperiments();

        JSONArray jsonArray = new JSONArray();
        String[] names = new String[]{"id", "projectID", "readID", "analysis"};

        for (TransferObject transferObject : listOfExperiments){
            JSONObject jsonObject1 = new JSONObject(transferObject, names);
            jsonArray.put(jsonObject1);
        }
        return new JsonRepresentation(jsonArray);
    }

    @Post("?xml")
    public void addExperiment(Representation representation) throws IOException, ClassNotFoundException {

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

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

        }catch (Exception e){
            ResourceExceptionHandling.exceptionHandling(e, this);
        }
    }

    @Post("?json")
    public void addExperimentUsingJson(JsonRepresentation representation) throws Exception {

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        JSONObject jsonObject = representation.getJsonObject();

        String projectID = jsonObject.getString("projectID");
        String readID = jsonObject.getString("readID");
        int id = IDGenerator.getUniqueID("Experiment");
        
        Experiment experiment = new Experiment(id, Integer.parseInt(projectID), Integer.parseInt(readID));
        ExperimentBuilder experimentBuilder = new ExperimentBuilder(experiment);
        experimentBuilder.build();

    }

    @Post("?csv")
    public void exportCSV(Representation representation) throws Exception {

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        XMLToDto xmlToDto = new XMLToDto(document, Export.class);
        List<TransferObject> listOfExportObjects = xmlToDto.convertToTransferObject();

        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> listOfExperiment = experimentPresenter.createListOfAllExperiments();

        for (TransferObject transferObject : listOfExportObjects){
            Export export = (Export) transferObject;
            ExportToCSV exportToCSV = new ExportToCSV(export.locationToExportTo, listOfExperiment);
            exportToCSV.createCSV(export.objectType);
        }

    }

}
