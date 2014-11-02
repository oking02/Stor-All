package main.java.server.resources.experiment;

import main.java.dto.*;
import main.java.fileutils.FileTranfers;
import main.java.mysql.builder.AnalysisBuilder;
import main.java.mysql.builder.ReadBuilder;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.presenter.ReadPresenter;
import main.java.mysql.remover.ExperimentRemover;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.util.AddResponceHeaders;
import main.java.server.util.GenericExporter;
import main.java.server.util.ResourceExceptionHandling;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.util.Series;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.server.util.ResourceExceptionHandling.*;

/**
 * Created by oking on 02/10/14.
 */
public class SingleExperimentResource extends ServerResource {

    @Get("?xml")
    public Representation getExperiment() throws SQLException, ParserConfigurationException, IOException {

        int queryExpID = Integer.parseInt(this.getAttribute("id"));
        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> listOfExperiment = null;

        try {

            listOfExperiment = experimentPresenter.getExperiment(queryExpID);

        }catch (Exception e){
            exceptionHandling(e, this);
        }

        DtoToXml dtoToXml = new DtoToXml(listOfExperiment);
        Document document = dtoToXml.createNewXMLDocument();

        DomRepresentation domRepresentation = new DomRepresentation();
        domRepresentation.setDocument(document);
        return domRepresentation;
    }

    @Get("?json")
    public Representation getExperimentJson() throws Exception {
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        int queryExpID = Integer.parseInt(this.getAttribute("id"));
        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> listOfExperiment = experimentPresenter.getExperiment(queryExpID);

        JSONArray jsonArray = new JSONArray();
        String[] names = new String[]{"id", "projectID", "readID", "analysis"};

        for (TransferObject transferObject : listOfExperiment){
            JSONObject jsonObject1 = new JSONObject(transferObject, names);
            jsonArray.put(jsonObject1);
        }
        return new JsonRepresentation(jsonArray);
    }

    @Post("?xml")
    public void addAnalysis(Representation representation) throws IOException, ClassNotFoundException {

        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        XMLToDto xmlToDto = new XMLToDto(document, Analysis.class);
        List<TransferObject> listOfAnalysis = xmlToDto.convertToTransferObject();

        try {

            for (TransferObject transferObject : listOfAnalysis) {
                Analysis analysis = (Analysis) transferObject;
                analysis.id = IDGenerator.getUniqueID("Analysis");
                AnalysisBuilder analysisBuilder = new AnalysisBuilder(analysis);
                analysisBuilder.build();
            }

        } catch (Exception e){
            exceptionHandling(e, this);
        }
    }

    @Post("?json")
    public void addAnalysisJson(JsonRepresentation representation) throws Exception {

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        JSONObject jsonObject = representation.getJsonObject();

        String expID = jsonObject.getString("expID");
        String info = jsonObject.getString("info");
        String dataLocation = jsonObject.getString("dataLocation");
        int id = IDGenerator.getUniqueID("Analysis");

        Analysis analysis = new Analysis(id, Integer.parseInt(expID), info, dataLocation);
        AnalysisBuilder analysisBuilder = new AnalysisBuilder(analysis);
        analysisBuilder.build();
    }

    @Put
    public void exportExperiment(Representation representation) throws IOException, ClassNotFoundException {

//        DomRepresentation domRepresentation = new DomRepresentation(representation);
//        Document document = domRepresentation.getDocument();
//
//        XMLToDto xmlToDto = new XMLToDto(document, Export.class);
//        List<TransferObject> listOfExportObjects = xmlToDto.convertToTransferObject();

        try{

            GenericExporter genericExporter = new GenericExporter();
            genericExporter.export(representation);

        } catch (Exception e){
            exceptionHandling(e, this);
        }

    }

    @Delete
    public void deleteExperiment() throws IOException, ClassNotFoundException, SQLException {

        int queryExpID = Integer.parseInt(this.getAttribute("id"));
        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> listOfExperiment;

        try {

            listOfExperiment = experimentPresenter.getExperiment(queryExpID);
            for (TransferObject experiment : listOfExperiment) {
                ExperimentRemover experimentRemover = new ExperimentRemover((Experiment) experiment);
                experimentRemover.remove();
            }

        } catch (Exception e){
            exceptionHandling(e, this);
        }

    }

}
