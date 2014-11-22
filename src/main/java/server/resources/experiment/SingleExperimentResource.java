package main.java.server.resources.experiment;

import main.java.dto.*;
import main.java.fileutils.NoteController;
import main.java.mysql.builder.AnalysisBuilder;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.remover.ExperimentRemover;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.representations.ExperimentJsonRepresentation;
import main.java.server.util.AddResponceHeaders;
import main.java.server.util.GenericExporter;
import main.java.server.responce.ResourceExceptionHandling;
import main.java.server.responce.ResponseBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.util.Series;
import org.w3c.dom.Document;

import java.util.List;

import static main.java.server.responce.ResourceExceptionHandling.*;

/**
 * Created by oking on 02/10/14.
 */
public class SingleExperimentResource extends ServerResource {

    @Get("?xml")
    public Representation getExperiment() {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        int queryExpID = Integer.parseInt(this.getAttribute("id"));

        DomRepresentation domRepresentation = null;

        try {

            ExperimentPresenter experimentPresenter = new ExperimentPresenter();
            List<TransferObject> listOfExperiment = experimentPresenter.getExperiment(queryExpID);

            DtoToXml dtoToXml = new DtoToXml(listOfExperiment);
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
    public Representation getExperimentJson() throws JSONException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        ExperimentJsonRepresentation experimentJsonRepresentation;
        JsonRepresentation jsonRepresentation = null;

        try {

            int queryExpID = Integer.parseInt(this.getAttribute("id"));
            ExperimentPresenter experimentPresenter = new ExperimentPresenter();
            List<TransferObject> listOfExperiment = experimentPresenter.getExperiment(queryExpID);

            experimentJsonRepresentation = new ExperimentJsonRepresentation(listOfExperiment);
            jsonRepresentation = experimentJsonRepresentation.getJsonRepresentation();

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        return jsonRepresentation;
    }

    @Post("?xml")
    public void addAnalysis(Representation representation) {

        DomRepresentation domRepresentation = new DomRepresentation(representation);
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {

            Document document = domRepresentation.getDocument();

            XMLToDto xmlToDto = new XMLToDto(document, Analysis.class);
            List<TransferObject> listOfAnalysis = xmlToDto.convertToTransferObject();

            for (TransferObject transferObject : listOfAnalysis) {
                Analysis analysis = (Analysis) transferObject;
                analysis.id = IDGenerator.getUniqueID("Analysis");
                AnalysisBuilder analysisBuilder = new AnalysisBuilder(analysis);
                analysisBuilder.build();
            }

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
    }

    @Post("?json")
    public void addAnalysisJson(JsonRepresentation representation)  {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {

            JSONObject jsonObject = representation.getJsonObject();
            String expID = jsonObject.getString("expID");
            String info = jsonObject.getString("info");
            String dataLocation = jsonObject.getString("dataLocation");
            int id = IDGenerator.getUniqueID("Analysis");

            Analysis analysis = new Analysis(id, Integer.parseInt(expID), info, dataLocation);
            AnalysisBuilder analysisBuilder = new AnalysisBuilder(analysis);
            analysisBuilder.build();
        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
    }

    @Post("?note")
    public void addNotes(JsonRepresentation representation){

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        int queryExpID = Integer.parseInt(this.getAttribute("id"));

        try {

            JSONObject jsonObject = representation.getJsonObject();
            String note = jsonObject.getString("Note");

            NoteController noteController = new NoteController(queryExpID, "Experiment");
            noteController.addNotes(note);

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
        responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
    }

    @Put
    public void exportExperiment(Representation representation) {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try{

            GenericExporter genericExporter = new GenericExporter();
            genericExporter.export(representation);

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }

    @Delete
    public void deleteExperiment() {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        int queryExpID = Integer.parseInt(this.getAttribute("id"));

        try {

            ExperimentPresenter experimentPresenter = new ExperimentPresenter();
            List<TransferObject> listOfExperiment = experimentPresenter.getExperiment(queryExpID);

            for (TransferObject experiment : listOfExperiment) {
                ExperimentRemover experimentRemover = new ExperimentRemover((Experiment) experiment);
                experimentRemover.remove();
            }

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }

}
