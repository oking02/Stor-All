package main.java.server.resources.system.info;

import main.java.dto.TransferObject;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.presenter.ProjectPresenter;
import main.java.mysql.presenter.ReadPresenter;
import main.java.server.util.AddResponceHeaders;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.java.server.responce.ResourceExceptionHandling.exceptionHandling;

/**
 * Created by oking on 05/11/14.
 */
public class SystemResource extends ServerResource {


    @Get("?page")
    public Representation getSystemInfoPage(){
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        FileRepresentation fileRepresentation = new FileRepresentation(new File(new File("").getAbsolutePath() + "/web/StaticFiles/InfoFiles/SystemInfo.html"), MediaType.TEXT_HTML);
        return fileRepresentation;
    }

    @Get("?size")
    public JsonRepresentation getSizeInformation(){

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        Map<String, Long> sizeInfo = buildSizeInfoJson();

        JSONObject jsonObject = new JSONObject(sizeInfo);
        JsonRepresentation jsonRepresentation = new JsonRepresentation(jsonObject);

        return jsonRepresentation;
    }

    @Get("?count")
    public Representation getCountInformation() {

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        JsonRepresentation jsonRepresentation = null;
        try {

            Map<String, Long> countInfo = buildCountInfoJson();
            JSONObject jsonObject = new JSONObject(countInfo);
            jsonRepresentation = new JsonRepresentation(jsonObject);

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

        return jsonRepresentation;
    }

    @Get("?allinfo")
    public Representation getSystemInfo() {
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        JsonRepresentation jsonRepresentation = null;
        try {

            Map<String, Long> systemInfo = buildCountInfoJson();
            systemInfo.putAll(buildSizeInfoJson());

            JSONObject jsonObject = new JSONObject(systemInfo);
            jsonRepresentation = new JsonRepresentation(jsonObject);

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

        return jsonRepresentation;
    }

    private Map<String, Long> buildCountInfoJson() {
        Map<String, Long> countInfo = Collections.EMPTY_MAP;

        try {
            ExperimentPresenter experimentPresenter = new ExperimentPresenter();
            List<TransferObject> listOfExperiments = experimentPresenter.createListOfAllExperiments();
            long numberOfExperiments = listOfExperiments.size();

            ProjectPresenter projectPresenter = new ProjectPresenter();
            List<TransferObject> listOfProjects = projectPresenter.createListOfAllProjects();
            long numberOfProjects = listOfProjects.size();

            ReadPresenter readPresenter = new ReadPresenter();
            List<TransferObject> listOfReads = readPresenter.createListOfAllReads();
            long numberOfReads = listOfReads.size();

            long total = numberOfExperiments + numberOfProjects + numberOfReads;

            countInfo = new HashMap<>();
            countInfo.put("numberOfExperiments", numberOfExperiments);
            countInfo.put("numberOfProjects", numberOfProjects);
            countInfo.put("numberOfReads", numberOfReads);

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

        return countInfo;
    }

    private Map<String, Long> buildSizeInfoJson(){
        File experimentDirectory = new File(new File("").getAbsolutePath() +"/Experiments");
        File projectDirectory = new File(new File("").getAbsolutePath() +"/Projects");
        File readDirectory = new File(new File("").getAbsolutePath() +"/Reads");

        long expSize = FileUtils.sizeOfDirectory(experimentDirectory) / 1000000;
        long proSize = FileUtils.sizeOfDirectory(projectDirectory) / 1000000;
        long readSize = FileUtils.sizeOfDirectory(readDirectory) / 1000000;
        long totalSize = expSize + proSize + readSize / 1000000;

        Map<String, Long> countInfo = new HashMap<>();
        countInfo.put("expSize", expSize);
        countInfo.put("proSize", proSize);
        countInfo.put("readSize", readSize);
        countInfo.put("totalSize", totalSize);

        return countInfo;
    }
}
