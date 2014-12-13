package main.java.server.resources.experiment;

import main.java.dto.*;
import main.java.dtoadapters.dtodeleter.DtoDeleter;
import main.java.dtoadapters.dtodeleter.ExperimentDeleter;
import main.java.dtoadapters.dtofinders.DtoFinder;
import main.java.dtoadapters.dtofinders.ExperimentFinder;
import main.java.dtoadapters.dtoupdate.DtoUpdater;
import main.java.dtoadapters.dtoupdate.ExperimentUpdate;
import main.java.fileutils.NoteController;
import main.java.mysql.analysistools.UpdateAnalysisTool;
import main.java.mysql.builder.AnalysisBuilder;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.representations.dtotojson.ExperimentJsonRepresentation;
import main.java.server.util.GenericExporter;
import main.java.server.responce.ResponseBuilder;
import org.json.JSONException;
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
public class SingleExperimentResource extends ServerResource {

    @Get("?xml")
    public Representation getExperiment() throws IOException {

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

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

        return domRepresentation;
    }

    @Get("?json")
    public Representation getExperimentJson() throws JSONException, IOException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        ExperimentJsonRepresentation experimentJsonRepresentation;
        JsonRepresentation jsonRepresentation;
        DtoFinder experimentFinder = new ExperimentFinder();
        int queryExpID = Integer.parseInt(this.getAttribute("id"));

        try {

            List<TransferObject> listOfExperiment = experimentFinder.findOne(queryExpID);
            experimentJsonRepresentation = new ExperimentJsonRepresentation(listOfExperiment);
            jsonRepresentation = experimentJsonRepresentation.getJsonRepresentation();

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
        return jsonRepresentation;
    }

    @Post("?xml")
    public void addAnalysis(Representation representation) throws IOException {

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
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }

    @Post("?json")
    public void addAnalysisJson(JsonRepresentation representation) throws IOException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        DtoUpdater experimentUpdate = new ExperimentUpdate();

        try {

            JSONObject jsonObject = representation.getJsonObject();
            String expID = jsonObject.getString("expID");
            String info = jsonObject.getString("info");
            String dataLocation = jsonObject.getString("dataLocation");
            int id = IDGenerator.getUniqueID("Analysis");

            AnalysisTool analysisTool = new AnalysisTool("toolname", dataLocation);
            UpdateAnalysisTool updateAnalysisTool = new UpdateAnalysisTool(analysisTool);
            updateAnalysisTool.updateCount();

            Analysis analysis = new Analysis(id, Integer.parseInt(expID), info, dataLocation);
            experimentUpdate.update(analysis);

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }

    @Post("?note")
    public void addNotes(JsonRepresentation representation) throws IOException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        int queryExpID = Integer.parseInt(this.getAttribute("id"));

        try {

            JSONObject jsonObject = representation.getJsonObject();
            String note = jsonObject.getString("Note");

            NoteController noteController = new NoteController(queryExpID, "Experiment");
            noteController.addNotes(note);

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }

    @Put
    public void exportExperiment(Representation representation) throws IOException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try{

            GenericExporter genericExporter = new GenericExporter();
            genericExporter.export(representation);

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }

    @Delete
    public void deleteExperiment() throws IOException {

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        DtoDeleter dtoDeleter = new ExperimentDeleter();
        int queryExpID = Integer.parseInt(this.getAttribute("id"));

        try {

            ExperimentPresenter experimentPresenter = new ExperimentPresenter();
            List<TransferObject> listOfExperiment = experimentPresenter.getExperiment(queryExpID);

            for (TransferObject experiment : listOfExperiment) {
                dtoDeleter.delete(experiment);
            }

            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());

        } catch (Exception e){
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }

}
