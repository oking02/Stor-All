package main.java.server.resources.experiment;

import main.java.dto.Experiment;
import main.java.dto.Export;
import main.java.dto.TransferObject;
import main.java.fileutils.ExportToCSV;
import main.java.mysql.builder.ExperimentBuilder;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.remover.ExperimentRemover;
import main.java.mysql.utils.DtoToXml;

import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.util.ResourceExceptionHandling;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by oking on 22/09/14.
 */
public class ExperimentResource extends ServerResource {

    @Get
    public Representation getExperiments() throws IOException, SQLException, ParserConfigurationException {

        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> listOfExperiments = null;

        try {

            listOfExperiments = experimentPresenter.createListOfAllExperiments();

        } catch (Exception e) {
            ResourceExceptionHandling.exceptionHandling(e, this);
        }

        DtoToXml toXmlDocument = new DtoToXml(listOfExperiments);
        Document document = toXmlDocument.createNewXMLDocument();

        DomRepresentation domRepresentation = new DomRepresentation();
        domRepresentation.setDocument(document);
        return domRepresentation;
    }

    @Put
    public void addExperiment(Representation representation) throws IOException, ClassNotFoundException {

        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        XMLToDto xmlToDto = new XMLToDto(document, Experiment.class);
        List<TransferObject> list = xmlToDto.convertToTransferObject();

        try {

            for (TransferObject experiment : list) {
                Experiment experiment1 = (Experiment) experiment;
                experiment1.id = IDGenerator.getUniqueID("Experiment");
                ExperimentBuilder experimentBuilder = new ExperimentBuilder(experiment1);
                experimentBuilder.build();
            }

        }catch (Exception e){
            ResourceExceptionHandling.exceptionHandling(e, this);
        }
    }

    @Post
    public void exportCSV(Representation representation) throws Exception {

        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        XMLToDto xmlToDto = new XMLToDto(document, Export.class);
        List<TransferObject> listOfExportObjects = xmlToDto.convertToTransferObject();

        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> listOfExperiment = experimentPresenter.createListOfAllExperiments();

        for (TransferObject transferObject : listOfExportObjects){
            Export export = (Export) transferObject;
            ExportToCSV exportToCSV = new ExportToCSV(export.locationToExportTo, listOfExperiment);
            exportToCSV.createCSV(export.objectType);
        }

    }






}
