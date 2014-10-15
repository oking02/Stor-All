package main.java.server.util;

import main.java.dto.Export;
import main.java.dto.TransferObject;
import main.java.fileutils.FileTranfers;
import main.java.mysql.utils.XMLToDto;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by oking on 15/10/14.
 */
public class GenericExporter {

    public void export(Representation representation) throws IOException, InterruptedException, ClassNotFoundException {
        DomRepresentation domRepresentation = new DomRepresentation(representation);
        Document document = domRepresentation.getDocument();

        XMLToDto xmlToDto = new XMLToDto(document, Export.class);
        List<TransferObject> listOfExportObjects = xmlToDto.convertToTransferObject();

        for (TransferObject transferObject : listOfExportObjects){
            Export export = (Export) transferObject;
            String sourceLocation = new File("").getAbsolutePath() + "/" + export.objectType + "s/" + export.objectType + "-" + export.idOfObjectToExport;
            String destination = export.locationToExportTo + "/" + export.objectType + "-" + export.idOfObjectToExport;
            System.out.println(sourceLocation);
            System.out.println(destination);

            FileTranfers fileTranfers = new FileTranfers(sourceLocation, destination);
            fileTranfers.transfer();
          }
    }


}
