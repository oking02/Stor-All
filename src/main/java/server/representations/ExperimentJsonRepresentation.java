package main.java.server.representations;

import main.java.dto.Analysis;
import main.java.dto.Experiment;
import main.java.dto.TransferObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oking on 22/11/14.
 */
public class ExperimentJsonRepresentation {
    private List<TransferObject> experimentList;
    String[] experimentKeyNames = new String[]{"id", "projectID", "readID", "analysis"};
    String[] analysisKeyNames = new String[]{"id", "expID", "info"};

    public ExperimentJsonRepresentation(List<TransferObject> experimentList){
        this.experimentList = experimentList;
    }

    public ExperimentJsonRepresentation(Experiment experiment){
        experimentList = new ArrayList<>();
        experimentList.add(experiment);
    }

    public JsonRepresentation getJsonRepresentation() throws JSONException {
        return new JsonRepresentation(getJsonArray());
    }

    public JSONArray getJsonArray() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (TransferObject transferObject : experimentList) {
            JSONObject jsonObject1 = new JSONObject(transferObject, experimentKeyNames);
            Experiment experiment = (Experiment) transferObject;
            jsonObject1.put("analysis", addAnalysis(experiment.analysis));
            jsonArray.put(jsonObject1);
        }
        return jsonArray;
    }

    private JSONArray addAnalysis(List<Analysis> analysisObjects){
        JSONArray analysisArray = new JSONArray();
        for (TransferObject transferObject1 : analysisObjects){
            Analysis analysis = (Analysis) transferObject1;
            JSONObject analysisObject = new JSONObject(analysis, analysisKeyNames);
            analysisArray.put(analysisObject);
        }
        return analysisArray;
    }

}
