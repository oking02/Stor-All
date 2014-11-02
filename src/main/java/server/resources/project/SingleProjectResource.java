package main.java.server.resources.project;

import main.java.dto.Analysis;
import main.java.dto.Project;
import main.java.dto.TransferObject;
import main.java.mysql.builder.AnalysisBuilder;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.presenter.ProjectPresenter;
import main.java.mysql.remover.ProjectRemover;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.util.AddResponceHeaders;
import main.java.server.util.GenericExporter;
import main.java.server.util.ResourceExceptionHandling;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.server.util.ResourceExceptionHandling.*;

/**
 * Created by oking on 02/10/14.
 */
public class SingleProjectResource extends ServerResource {

    @Get("?xml")
    public Representation getProject() throws SQLException, ParserConfigurationException, IOException {

        int queryID = Integer.parseInt(this.getAttribute("id"));
        ProjectPresenter projectPresenter = new ProjectPresenter();

        List<TransferObject> list = null;
        try {

            list = projectPresenter.getProject(queryID);

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

        DtoToXml dtoToXml = new DtoToXml(list);
        Document document = dtoToXml.createNewXMLDocument();

        DomRepresentation domRepresentation = new DomRepresentation();
        domRepresentation.setDocument(document);
        return domRepresentation;
    }

    @Get("?json")
    public Representation getProjectJson() throws Exception {
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, getResponse());

        int queryExpID = Integer.parseInt(this.getAttribute("id"));
        ProjectPresenter projectPresenter = new ProjectPresenter();
        List<TransferObject> listOfProject = projectPresenter.getProject(queryExpID);

        JSONArray jsonArray = new JSONArray();
        String[] names = new String[]{"id", "owner"};

        for (TransferObject transferObject : listOfProject){
            JSONObject jsonObject1 = new JSONObject(transferObject, names);
            jsonArray.put(jsonObject1);
        }
        return new JsonRepresentation(jsonArray);
    }

    @Put
    public void exportProject(Representation representation){

        try {

            GenericExporter genericExporter = new GenericExporter();
            genericExporter.export(representation);

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

    }


    @Delete
    public void deleteProject() throws IOException, ClassNotFoundException, SQLException {

        int queryID = Integer.parseInt(this.getAttribute("id"));
        ProjectPresenter projectPresenter = new ProjectPresenter();

        try {

            List<TransferObject> list = projectPresenter.getProject(queryID);

            for (TransferObject transferObject : list) {
                ProjectRemover projectRemover = new ProjectRemover((Project) transferObject);
                projectRemover.remove();
            }

        }catch (Exception e){
            exceptionHandling(e, this);
        }
    }
}
