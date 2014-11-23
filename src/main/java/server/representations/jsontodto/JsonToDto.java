package main.java.server.representations.jsontodto;

import main.java.dto.TransferObject;
import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;

import java.util.List;

/**
 * Created by oking on 22/11/14.
 */
public abstract class JsonToDto {
    private JsonRepresentation jsonRepresentation;

    public JsonToDto(JsonRepresentation jsonRepresentation){
        this.jsonRepresentation = jsonRepresentation;
    }

    public abstract TransferObject getDto() throws JSONException;

    public JsonRepresentation getJsonRepresentation() {
        return jsonRepresentation;
    }
}
