package main.java.server.representations.dtotojson;

import main.java.dto.TransferObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;

import java.util.List;

/**
 * Created by oking on 22/11/14.
 */
public class ProjectJsonResource extends DtoToRepresentation {
    private String[] projectKeyValues = new String[]{"id", "owner"};

    public ProjectJsonResource(List<TransferObject> projectList){
        super(projectList);
    }

    public JsonRepresentation getJsonRepresentation(){
        return new JsonRepresentation(getJsonArray());
    }

    public JSONArray getJsonArray(){
        JSONArray jsonArray = new JSONArray();

        for (TransferObject transferObject : getDtoList()) {
            JSONObject jsonObject1 = new JSONObject(transferObject, projectKeyValues);
            jsonArray.put(jsonObject1);
        }
        return jsonArray;
    }
}