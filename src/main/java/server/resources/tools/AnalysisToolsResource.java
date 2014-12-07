package main.java.server.resources.tools;

import main.java.dto.AnalysisTool;
import main.java.dto.TransferObject;
import main.java.mysql.analysistools.AddAnalysisTool;
import main.java.mysql.analysistools.GetAnalysisTools;
import main.java.server.representations.dtotojson.AnalysisToolToRepresentation;
import main.java.server.responce.ResponseBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runners.Parameterized;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by oking on 04/12/14.
 */
public class AnalysisToolsResource extends ServerResource {


    @Get("?json")
    public JsonRepresentation getToolsJson(){

        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        GetAnalysisTools getAnalysisTools;
        List<TransferObject> listOfTools;

        try {

            getAnalysisTools = new GetAnalysisTools();
            listOfTools = getAnalysisTools.getToolList();
            AnalysisToolToRepresentation analysisToolToRepresentation = new AnalysisToolToRepresentation(listOfTools);
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
            return analysisToolToRepresentation.getJsonRepresentation();

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }

    @Post("?json")
    public void addAnalysisToolJson(JsonRepresentation representation){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {

            JSONObject jsonObject = representation.getJsonObject();
            AnalysisTool analysisTool = new AnalysisTool(jsonObject.getString("name"), jsonObject.getString("outputLocation"));
            AddAnalysisTool addAnalysisTool = new AddAnalysisTool(analysisTool);
            addAnalysisTool.add();
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }
}
