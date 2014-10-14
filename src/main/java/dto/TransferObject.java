package main.java.dto;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by oking on 23/09/14.
 */
public abstract class TransferObject {


    public Document convertToXml() throws ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.newDocument();

        Element rootElement = doc.createElement(this.getClass().getSimpleName());

        doc.appendChild(rootElement);
        System.out.println(this.getClass().getSimpleName());


        //Iterates through a array of the field object taken from the calling class.
        for (Field f : this.getClass().getDeclaredFields()){
            System.out.println(f.getName());

            try {

                if (f.getGenericType().getTypeName().equals("java.util.ArrayList<java.lang.String>")){

                    int x = 1;
                    List list = (List) f.get(this);
                    for (Object obj : list){
                        System.out.println(obj.toString());
                        attachElement(doc, rootElement, f.getName() + x, obj.toString());
                        x++;
                    }
                } else{

                    System.out.println(String.valueOf(f.get(this)));
                    //attach to the xml the name of the field and its value.
                    attachElement(doc, rootElement, f.getName(), String.valueOf(f.get(this)));

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        System.out.println(rootElement.toString());

        return doc;
    }

    /**
     * Used to add a element to a document. Example of parent element would be <Experiment><Experiment/>.
     * Used method of add a child element inside parent. <Experiment> <_id>24<_id/> <Experiment/>.
     * @param document Document to add info too.
     * @param parent Parent Element
     * @param childName Name of element to add.
     * @param childValue Value of the element.
     */
    protected void attachElement(Document document, Element parent, String childName, String childValue) {

        Element childElement = document.createElement(childName);
        childElement.setTextContent(childValue);
        parent.appendChild(childElement);

    }

    /**
     * Method to get specific values out of a document.
     * Used to build a object out of a document.
     * @param document document containing the infomation.
     * @param elementName Name of element containing the desired value.
     * @return
     */
    protected String getContent(Document document, String elementName) {
        Element element = (Element) document.getElementsByTagName(elementName).item(0);
        return element.getTextContent();
    }



}
