package main.java.server.representations.jsontodto;

import main.java.dto.Experiment;
import main.java.dto.TransferObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;

import java.util.List;

/**
 * Created by oking on 22/11/14.
 */
public class JsonToExperiment extends JsonToDto {


    public JsonToExperiment(JsonRepresentation jsonRepresentation) {
        super(jsonRepresentation);
    }

    @Override
    public TransferObject getDto() throws JSONException {
        JSONObject jsonObject = getJsonRepresentation().getJsonObject();
        String projectID = jsonObject.getString("projectID");
        String readID = jsonObject.getString("readID");
        Experiment experiment = new Experiment(getCorrectID(jsonObject), Integer.parseInt(projectID), Integer.parseInt(readID));
        return experiment;
    }

    private int getCorrectID(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("id")){
            return Integer.parseInt(jsonObject.getString("id"));
        }
        else {
            return 0;
        }
    }
}
