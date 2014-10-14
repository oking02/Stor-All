package main.java.server;

import main.java.dto.*;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.XMLToDto;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oking on 22/09/14.
 */
public class TestScript {

    public static void main(String[] args) throws SQLException, NoSuchFieldException, IllegalAccessException, IOException, ParserConfigurationException, ClassNotFoundException {


//        ExperimentResource experimentResource = new ExperimentResource();
//        experimentResource.getExperiments();
//
        ClientResource clientResource = new ClientResource("http://localhost:27777/storall/Project/1/Experiments");

        DomRepresentation domRepresentation = new DomRepresentation(clientResource.get());

        XMLToDto xmlToDto = new XMLToDto(domRepresentation.getDocument(), Experiment.class);
        List<TransferObject> list = xmlToDto.convertToTransferObject();
        list.forEach(System.out::println);

    }

}
