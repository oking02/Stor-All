package main.java.server;

import main.java.dto.*;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.XMLToDto;
import main.java.server.resources.experiment.ExperimentResource;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
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

    public static void main(String[] args) throws Exception {


        Experiment experiment = new Experiment(0, 1, 1);
        String[] names = new String[]{"id", "projectID", "readID"};

        JSONObject newJson = new JSONObject(experiment, names);

        JsonRepresentation jsonRepresentation = new JsonRepresentation(newJson);

        ExperimentResource experimentResource = new ExperimentResource();
        experimentResource.addExperimentUsingJson(jsonRepresentation);


        System.out.println(newJson.toString());

    }

}
