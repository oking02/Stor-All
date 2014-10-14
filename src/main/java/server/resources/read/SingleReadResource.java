package main.java.server.resources.read;

import main.java.dto.TransferObject;
import main.java.mysql.presenter.ReadPresenter;
import main.java.mysql.remover.ReadRemover;
import main.java.mysql.utils.DtoToXml;
import main.java.server.util.ResourceExceptionHandling;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static main.java.server.util.ResourceExceptionHandling.*;

/**
 * Created by oking on 02/10/14.
 */
public class SingleReadResource extends ServerResource {

    @Get
    public Representation getRead() throws ParserConfigurationException, IOException {

        int queryID = Integer.parseInt(this.getAttribute("id"));
        List<TransferObject> list = null;

        try {

            ReadPresenter readPresenter = new ReadPresenter();
            list = readPresenter.getRead(queryID);

        } catch (Exception e) {
            exceptionHandling(e, this);
        }

        DtoToXml dtoToXml = new DtoToXml(list);
        Document document = dtoToXml.createNewXMLDocument();
        DomRepresentation domRepresentation = new DomRepresentation();
        domRepresentation.setDocument(document);
        return domRepresentation;

    }

    @Delete
    public void deleteRead(){

        int queryID = Integer.parseInt(this.getAttribute("id"));

        try {

            ReadPresenter readPresenter = new ReadPresenter();
            List<TransferObject> list = readPresenter.getRead(queryID);

            for (TransferObject transferObject : list) {
                ReadRemover readRemover = new ReadRemover((main.java.dto.Read) transferObject);
                readRemover.remove();
            }

        } catch (Exception e){
            exceptionHandling(e, this);
        }

    }
}
