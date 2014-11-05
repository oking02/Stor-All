package main.java.server.resources.system;

import main.java.dto.TransferObject;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.presenter.ProjectPresenter;
import main.java.mysql.presenter.ReadPresenter;
import main.java.server.util.AddResponceHeaders;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.json.JsonpRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oking on 05/11/14.
 */
public class SystemResource extends ServerResource {


    @Get("?size")
    public JsonRepresentation getSizeInformation(){

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        File experimentDirectory = new File(new File("").getAbsolutePath() +"/Experiments");
        File projectDirectory = new File(new File("").getAbsolutePath() +"/Projects");
        File readDirectory = new File(new File("").getAbsolutePath() +"/Reads");

        long expSize = FileUtils.sizeOfDirectory(experimentDirectory) / 1000000;
        long proSize = FileUtils.sizeOfDirectory(projectDirectory) / 1000000;
        long readSize = FileUtils.sizeOfDirectory(readDirectory) / 1000000;
        long total = expSize + proSize + readSize / 1000000;

        Map<String, Long> sizeInfo = new HashMap<>();
        sizeInfo.put("expSize", expSize);
        sizeInfo.put("proSize", proSize);
        sizeInfo.put("readSize", readSize);
        sizeInfo.put("totalSize", total);

        JSONObject jsonObject = new JSONObject(sizeInfo);
        JsonRepresentation jsonRepresentation = new JsonRepresentation(jsonObject);

        return jsonRepresentation;
    }

    @Get("?count")
    public Representation getCountInformation() throws Exception {

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> listOfExperiments = experimentPresenter.createListOfAllExperiments();
        int numberOfExperiments = listOfExperiments.size();

        ProjectPresenter projectPresenter = new ProjectPresenter();
        List<TransferObject> listOfProjects = projectPresenter.createListOfAllProjects();
        int numberOfProjects = listOfProjects.size();

        ReadPresenter readPresenter = new ReadPresenter();
        List<TransferObject> listOfReads = readPresenter.createListOfAllReads();
        int numberOfReads = listOfReads.size();

        int total = numberOfExperiments + numberOfProjects + numberOfReads;

        Map<String, Integer> countInfo = new HashMap<>();
        countInfo.put("numberOfExperiments", numberOfExperiments);
        countInfo.put("numberOfProjects", numberOfProjects);
        countInfo.put("numberOfReads", numberOfReads);
        countInfo.put("total", total);

        JSONObject jsonObject = new JSONObject(countInfo);
        JsonRepresentation jsonRepresentation = new JsonRepresentation(jsonObject);

        return jsonRepresentation;
    }
}
