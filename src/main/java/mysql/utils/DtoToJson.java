package main.java.mysql.utils;

import main.java.dto.TransferObject;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by oking on 02/11/14.
 */
public class DtoToJson {
    private List<TransferObject> dtoList;
    private String dtoType;

    public DtoToJson(List<TransferObject> dtoList){
        this.dtoList = dtoList;
        this.dtoType = dtoList.get(0).getClass().getSimpleName();
    }

    public JSONObject dtoToJson(){



        return null;
    }
}
