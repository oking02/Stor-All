package main.java.server.resources.project;

import main.java.dto.Export;
import main.java.dto.Project;
import main.java.dto.TransferObject;
import main.java.fileutils.ExportToCSV;
import main.java.mysql.builder.ProjectBuilder;
import main.java.mysql.presenter.ProjectPresenter;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.util.AddResponceHeaders;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static main.java.server.util.ResourceExceptionHandling.exceptionHandling;

/**
 * Created by oking on 23/09/14.
 */
public class ProjectResource extends ServerResource {

    @Get("?xml")
    public Representation getProjects() throws IOException, ParserConfigurationException {

        DomRepresentation domRepresentation = new DomRepresentation();
        List<TransferObject> listOfExperiments = null;

        try {

            ProjectPresenter projectPresenter = new ProjectPresenter();
            listOfExperiments = projectPresenter.createListOfAllProjects();

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

        DtoToXml toXmlDocument = new DtoToXml(listOfExperiments);
        Document document = toXmlDocument.createNewXMLDocument();
        domRepresentation.setDocument(document);

        return domRepresentation;
    }

    @Get("?json")
    public Representation getProjectJson() throws Exception {
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        ProjectPresenter projectPresenter = new ProjectPresenter();
        List<TransferObject> listOfProjects = projectPresenter.createListOfAllProjects();

        JSONArray jsonArray = new JSONArray();
        String[] names = new String[]{"id", "owner"};

        for (TransferObject transferObject : listOfProjects){
            JSONObject jsonObject1 = new JSONObject(transferObject, names);
            jsonArray.put(jsonObject1);
        }
        return new JsonRepresentation(jsonArray);
    }

    @Post("?xml")
    public void addProject(Representation representation) throws ClassNotFoundException, IOException {

        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        XMLToDto xmlToDto = new XMLToDto(document, Project.class);
        List<TransferObject> list = xmlToDto.convertToTransferObject();

        try {

            for (TransferObject p : list) {
                Project project = (Project) p;
                project.id = IDGenerator.getUniqueID("Project");
                ProjectBuilder projectBuilder = new ProjectBuilder(project);
                projectBuilder.build();
            }

        }catch (Exception e){
            exceptionHandling(e, this);
        }
    }

    @Post("?json")
    public void addProjectJson(JsonRepresentation representation) throws Exception {

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        JSONObject jsonObject = representation.getJsonObject();

        String owner = jsonObject.getString("owner");
        int id = IDGenerator.getUniqueID("Project");

        Project project = new Project(id, owner);
        ProjectBuilder projectBuilder = new ProjectBuilder(project);
        projectBuilder.build();
    }

    @Post("?file")
    public void exportToFile(Representation representation) throws Exception {
        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        XMLToDto xmlToDto = new XMLToDto(document, Export.class);
        List<TransferObject> listOfExportObjects = xmlToDto.convertToTransferObject();

        ProjectPresenter projectPresenter = new ProjectPresenter();
        List<TransferObject> listOfExperiment = projectPresenter.createListOfAllProjects();

        for (TransferObject transferObject : listOfExportObjects){
            Export export = (Export) transferObject;
            ExportToCSV exportToCSV = new ExportToCSV(export.locationToExportTo, listOfExperiment);
            exportToCSV.createCSV(export.objectType);
        }
    }

}
