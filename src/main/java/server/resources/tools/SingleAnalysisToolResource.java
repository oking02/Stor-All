package main.java.server.resources.tools;

import main.java.dto.AnalysisTool;
import main.java.dto.TransferObject;
import main.java.mysql.analysistools.*;
import main.java.server.representations.dtotojson.AnalysisToolToRepresentation;
import main.java.server.responce.ResponseBuilder;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.*;

import java.util.List;

/**
 * Created by oking on 05/12/14.
 */
public class SingleAnalysisToolResource extends ServerResource {

    @Get("?json")
    public JsonRepresentation getToolJson(){


        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());
        GetAnalysisTools getAnalysisTools;
        List<TransferObject> listOfTools;

        try {

            String name = this.getAttribute("name");
            getAnalysisTools = new GetAnalysisTools();
            listOfTools = getAnalysisTools.getSingleAnalysisToolInfo(name);
            AnalysisToolToRepresentation analysisToolToRepresentation = new AnalysisToolToRepresentation(listOfTools);
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
            return analysisToolToRepresentation.getJsonRepresentation();

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }

    @Get("?usecount")
    public JsonRepresentation getUseCount(JsonRepresentation representation){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {

            String name = this.getAttribute("name");
            GetAnalysisToolUseCount getAnalysisToolUseCount = new GetAnalysisToolUseCount();
            int count = getAnalysisToolUseCount.getSingleAnalysisToolInfo(name);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count", count);

            return new JsonRepresentation(jsonObject);

        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }

    @Post("?json")
    public void updateAnalysisToolJson(JsonRepresentation representation){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {

            JSONObject jsonObject = representation.getJsonObject();
            AnalysisTool analysisTool = new AnalysisTool(jsonObject.getString("name"), jsonObject.getString("outputLocation"));
            UpdateAnalysisTool updateAnalysisTool = new UpdateAnalysisTool(analysisTool);
            updateAnalysisTool.update();
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }

    }

    @Delete("?json")
    public void deleteAnalysisToolJson(JsonRepresentation representation){
        ResponseBuilder responseBuilder = new ResponseBuilder(getResponse());

        try {

            JSONObject jsonObject = representation.getJsonObject();
            AnalysisTool analysisTool = new AnalysisTool(jsonObject.getString("name"), jsonObject.getString("outputLocation"));
            DeleteAnalysisTool deleteAnalysisTool = new DeleteAnalysisTool(analysisTool);
            deleteAnalysisTool.delete();
            responseBuilder.addSuccessStatus(getRequest().getMethod().getName());
        } catch (Exception e) {
            throw new ResourceException(responseBuilder.addErrorStatus(e));
        }
    }
}
