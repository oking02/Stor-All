package main.java.server.resources.system;

import jdk.internal.util.xml.impl.XMLWriter;
import main.java.dto.TransferObject;
import main.java.fileutils.ExportToCSV;
import main.java.mysql.presenter.ExperimentPresenter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.json.JsonpRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by oking on 06/11/14.
 */
public class FileServerResource extends ServerResource {

    @Get("?CSV")
    public Representation getCSV() throws Exception {

        /*
        JSONObject jsonObject = representation.getJsonObject();
        String id = jsonObject.getString("id");
        String type = jsonObject.getString("type");
        */
        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> list = experimentPresenter.createListOfAllExperiments();

        ExportToCSV exportToCSV = new ExportToCSV(new File("").getAbsolutePath(), list);
        exportToCSV.createCSV("Experiment");

        File toExport = new File(new File("").getAbsolutePath() + "/" + "Experiment" + "s.csv");
        FileRepresentation fileRepresentation = new FileRepresentation(new File("").getAbsolutePath() + "/" + "Experiment" + "s.csv", MediaType.TEXT_CSV);


        return fileRepresentation;
    }

    @Get("?JSON")
    public Representation getJSON() throws Exception {

        JSONArray jsonArray = new JSONArray();

        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> list = experimentPresenter.createListOfAllExperiments();

        String[] names = new String[]{"id", "projectID", "readID", "analysis"};
        for (TransferObject transferObject : list){
            JSONObject jsonObject = new JSONObject(transferObject, names);
            jsonArray.put(jsonObject);
        }

        File jsonFile = new File(new File("").getAbsolutePath() + "/jsonFile.json");
        jsonFile.createNewFile();

        FileWriter fileWriter = new FileWriter(jsonFile, true);
        fileWriter.write(jsonArray.toString());
        fileWriter.close();

        FileRepresentation fileRepresentation = new FileRepresentation(jsonFile, MediaType.APPLICATION_ALL_JSON);
        return fileRepresentation;
    }

    public Representation getXML(){

        return null;
    }

    public Representation getNotes(){

        return null;
    }

}
