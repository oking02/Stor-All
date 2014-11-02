package main.java.server;

import main.java.dto.*;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.XMLToDto;
import org.json.JSONObject;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oking on 22/09/14.
 */
public class TestScript {

    public static void main(String[] args) throws SQLException, NoSuchFieldException, IllegalAccessException, IOException, ParserConfigurationException, ClassNotFoundException {


        Experiment experiment = new Experiment(1, 2, 3);
        String[] names = new String[]{"id", "projectID", "readID"};

        JSONObject newJson = new JSONObject(experiment, names);


        System.out.println(newJson.toString());

    }

}
