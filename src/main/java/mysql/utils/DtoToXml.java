package main.java.mysql.utils;

import main.java.dto.TransferObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Class for converting Stor-Alls DTO (Data Transfer Objects) into a XML format.
 * DTO must extend TransferObject to be used.
 * An examples using the Experiment DTO.
 * <Experiments>
 *     <Experiment>
 *         <id></id>
 *         <projectID></projectID>
 *         <readID></readID>
 *         <analysis></analysis>
 *     </Experiment>
 * </Experiments>
 * Created by oking on 29/09/14.
 */
public class DtoToXml {
    private List<TransferObject> list;
    private String dtoType;
    private Document doc;

    public DtoToXml(List<TransferObject> list) throws ParserConfigurationException {
        this.list = list;
        this.dtoType = list.get(0).getClass().getSimpleName();
        this.doc = createDocument();
    }

    private Document createDocument() throws ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        return documentBuilder.newDocument();

    }

    public Document createNewXMLDocument() throws ParserConfigurationException {

        Element rootElement = doc.createElement(dtoType + "s");
        doc.appendChild(rootElement);

        for (TransferObject to : list){
            try {

                createTransferObjectElement(rootElement, to);

            } catch (IllegalAccessException e) {
               e.printStackTrace();
            }
        }
         return doc;
    }

    private void createTransferObjectElement(Element root, TransferObject transferObject) throws IllegalAccessException {

        Element transferObjectElement = doc.createElement(dtoType);
        addTransferObjectDataElements(transferObjectElement, transferObject);
        root.appendChild(transferObjectElement);

    }

    private void addTransferObjectDataElements(Element parent, TransferObject to) throws IllegalAccessException {

        for (Field f : to.getClass().getDeclaredFields()){

            Element node = doc.createElement(f.getName());
            node.setTextContent(String.valueOf(f.get(to)));
            parent.appendChild(node);

        }
    }
}
