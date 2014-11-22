package fileutilstests;

import main.java.fileutils.ExportToCSV;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by oking on 17/11/14.
 */
public class ExportToCSVTest {

    private String exportLocation =  new File("").getAbsolutePath() + "/tests/fileutilstests";



    @Test
    public void testExperimentExport() throws IOException {
        ExportToCSV exportToCSV = new ExportToCSV(exportLocation, new ArrayList<>());
        exportToCSV.createCSV("Experiment");
        assertTrue(new File(exportLocation + "/Experiments.csv").exists());
        assertTrue(readFile(new File(exportLocation + "/Experiments.csv")).length != 0);
    }

    @Test
    public void testProjectExport() throws IOException {
        ExportToCSV exportToCSV = new ExportToCSV(exportLocation, new ArrayList<>());
        exportToCSV.createCSV("Project");
        assertTrue(new File(exportLocation + "/Projects.csv").exists());
        assertTrue(readFile(new File(exportLocation + "/Projects.csv")).length != 0);
    }

    @Test
    public void testReadExport() throws IOException {
        ExportToCSV exportToCSV = new ExportToCSV(exportLocation, new ArrayList<>());
        exportToCSV.createCSV("Read");
        assertTrue(new File(exportLocation + "/Reads.csv").exists());
        assertTrue(readFile(new File(exportLocation + "/Reads.csv")).length != 0);
    }


    @AfterClass
    public static void tidyup(){
        String exportLocation =  new File("").getAbsolutePath() + "/tests/fileutilstests";
        File experimentExport = new File(exportLocation + "/Experiments.csv");
        File projectExport = new File(exportLocation + "/Projects.csv");
        File readExport = new File(exportLocation + "/Reads.csv");
        experimentExport.delete();
        projectExport.delete();
        readExport.delete();
    }

    private char[] readFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        char[] a = new char[100];
        fileReader.read(a);
        return a;
    }
}
