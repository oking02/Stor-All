package main.java.server.representations.dtotojson;

import main.java.dto.TransferObject;
import main.java.dtoadapters.dtoupdate.DtoUpdater;
import main.java.mysql.utils.DtoToXml;
import org.json.JSONArray;
import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;

import java.util.List;

/**
 * Created by oking on 22/11/14.
 */
public abstract class DtoToRepresentation {
    private List<TransferObject> dtoList;

    public DtoToRepresentation(List<TransferObject> dtoList){
        this.dtoList = dtoList;
    }

    public abstract JsonRepresentation getJsonRepresentation() throws JSONException;

    public abstract JSONArray getJsonArray() throws JSONException;

    public List<TransferObject> getDtoList() {
        return dtoList;
    }
}
