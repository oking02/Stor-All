package main.java.mysql.utils;

import main.java.dto.TransferObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by oking on 30/09/14.
 */
public class XMLToDto {
    private Document document;
    private Class dto;

    public XMLToDto(Document document, Class dto) throws ClassNotFoundException {
        this.document = document;
        this.dto = dto;
    }

    public List<TransferObject> convertToTransferObject() throws ClassNotFoundException {

        Element rootElement = document.getDocumentElement();
        NodeList dtoList = rootElement.getChildNodes();
        try {

            return listOfTransferObjectsFromNodes(dtoList);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    private List<TransferObject> listOfTransferObjectsFromNodes(NodeList nodeList) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<TransferObject> transferObjectList = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength() ; i++) {

            NodeList transferObjectsChildren = nodeList.item(i).getChildNodes();
            Constructor transferObjectsConstructor = getCorrectConstructor();
            List<Object> values = getFieldValues(transferObjectsChildren, transferObjectsConstructor.getParameterCount());
            List<Object> constructorArguments = castValuesToRightTypes(values, transferObjectsConstructor);

            Object transferObject =  transferObjectsConstructor.newInstance(constructorArguments.toArray());
            transferObjectList.add((TransferObject) transferObject) ;

        }
        return transferObjectList;
    }

    private Constructor getCorrectConstructor() throws ClassNotFoundException {
        Class<?> newTranferObject = Class.forName(dto.getName());
        Constructor[] constructors = newTranferObject.getDeclaredConstructors();
        return constructors[0];
    }

    private List<Object> getFieldValues(NodeList nodeList, int numberOfFields){
        List<Object> values = new ArrayList<>();

        for (int j = 0; j < numberOfFields ; j++) {
            values.add(nodeList.item(j).getTextContent());
        }
        return values;
    }

    private List<Object> castValuesToRightTypes(List<Object> values, Constructor constructor) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Type[] argumentTypes = constructor.getGenericParameterTypes();
        List<Object> constructorArguments = new ArrayList<>();

        for (int j = 0; j < values.size() ; j++) {
            Class ac = Class.forName(argumentTypes[j].getTypeName());
            Constructor[] con = ac.getDeclaredConstructors();

            if (ac.toString().equals("class java.lang.String")){
                constructorArguments.add(values.get(j));
            }
            else{
                constructorArguments.add(con[1].newInstance(values.get(j)));
            }
        }
        return constructorArguments;
    }




}
