package main.java.server;

import main.java.dto.*;
import main.java.fileutils.NoteController;
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


        String message = "Think back to the heady days of October 2013 and you might recall the excitement we had over LG's curving LG G Flex handset. Well, it appears LG hasn't given up on the bendy phone idea just yet.\n" +
                "According to an Israeli reporter, the Korean company is working on a mini version of the phone called, appropriately enough, the LG G Flex Mini.\n" +
                "The reporter claims to have seen a prototype during a visit to the company's factory in Korea. And, when he questioned LG about it, wasn't offered a denial.";

        NoteController noteController = new NoteController(21, "Experiment");
        noteController.addNotes(message);

    }

}
