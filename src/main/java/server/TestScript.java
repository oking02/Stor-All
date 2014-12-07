package main.java.server;

import main.java.dto.*;
import main.java.fileutils.NoteController;
import main.java.mysql.utils.AnalysisInExperiment;
import main.java.mysql.utils.DtoToXml;
import main.java.mysql.utils.XMLToDto;
import main.java.server.resources.experiment.ExperimentResource;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oking on 22/09/14.
 */
public class TestScript {

    public static void main(String[] args) throws Exception {

        int[] a = new int[]{1,5,2,3,8,7,5,6,8,9,7,5,2,4,6,7,2,3,5,4,7,6};
        System.out.println(solution(a));
    }

    public static int solution(int[] a){
        int N = a.length;

        Map<Integer, Integer> differences = new HashMap<>();
        int total = 0;
        for (int i = 0; i < N ; i++) {
            total = total + a[i];
            differences.put(i, total);
        }

        List<Integer> list = (List<Integer>) differences.values();



        return total;
    }

}
