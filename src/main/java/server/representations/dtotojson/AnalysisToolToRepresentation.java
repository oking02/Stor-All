package main.java.server.representations.dtotojson;

import main.java.dto.AnalysisTool;
import main.java.dto.Experiment;
import main.java.dto.TransferObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;

import java.util.List;

/**
 * Created by oking on 04/12/14.
 */
public class AnalysisToolToRepresentation extends DtoToRepresentation {
    String[] analysisToolKeys = new String[]{"name", "outputLocation", "useCount"};

    public AnalysisToolToRepresentation(List<TransferObject> dtoList) {
        super(dtoList);
    }

    @Override
    public JsonRepresentation getJsonRepresentation() throws JSONException {
        return new JsonRepresentation(getJsonArray());
    }

    @Override
    public JSONArray getJsonArray() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (TransferObject transferObject : getDtoList()) {
            JSONObject jsonObject1 = new JSONObject(transferObject, analysisToolKeys);
            jsonArray.put(jsonObject1);
            System.out.println(jsonObject1);
        }
        return jsonArray;
    }
}
