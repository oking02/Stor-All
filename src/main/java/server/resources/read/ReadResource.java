package main.java.server.resources.read;

import main.java.dto.Read;
import main.java.dto.TransferObject;
import main.java.mysql.builder.ReadBuilder;
import main.java.mysql.presenter.ReadPresenter;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.IDGenerator;
import main.java.mysql.utils.XMLToDto;
import main.java.server.util.ResourceExceptionHandling;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by oking on 02/10/14.
 */
public class ReadResource extends ServerResource {

    @Get
    public Representation getReads() throws ParserConfigurationException, IOException {

        List<TransferObject> listOfReads = null;
        try {

            ReadPresenter readPresenter = new ReadPresenter();
            listOfReads = readPresenter.createListOfAllReads();

        } catch (Exception e) {
            ResourceExceptionHandling.exceptionHandling(e, this);
        }

        DtoToXml dtoToXml = new DtoToXml(listOfReads);
        Document document = dtoToXml.createNewXMLDocument();

        DomRepresentation domRepresentation = new DomRepresentation();
        domRepresentation.setDocument(document);
        return domRepresentation;
    }

    @Put
    public String addRead(Representation representation) throws IOException, ClassNotFoundException {

        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        XMLToDto xmlToDto = new XMLToDto(document, Read.class);
        List<TransferObject> listOfRead = xmlToDto.convertToTransferObject();

        String newReads = "";
        try {

            for (TransferObject transferObject : listOfRead) {
                Read read = (Read) transferObject;
                read.id = IDGenerator.getUniqueID("Read");
                ReadBuilder readBuilder = new ReadBuilder(read);
                readBuilder.build();
                newReads = newReads + "Read: " + read.id;
            }

        }catch (Exception e){
            ResourceExceptionHandling.exceptionHandling(e, this);
        }

        return newReads;
    }
}
