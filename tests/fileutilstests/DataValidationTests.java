package fileutilstests;

import main.java.fileutils.DataValidation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by oking on 14/11/14.
 */
public class DataValidationTests {

    private String testDirectoryLocation = new File("").getAbsolutePath() + "/tests/fileutilstests/testfile";
    private String testFileLocation = new File("").getAbsolutePath() + "/tests/fileutilstests/testfile/testfile.txt";

    @Before
    public void setup() throws IOException {

        File testFile = new File(testDirectoryLocation);
        File testfile1 = new File(testFileLocation);
        testFile.mkdir();
        testfile1.createNewFile();

    }



    @After
    public void tidyUp(){

        File testFile = new File(testDirectoryLocation);
        File testfile1 = new File(testFileLocation);
        testfile1.delete();
        testFile.delete();

    }

    @Test
    public void testFileValiation() throws Exception {

        DataValidation.validateInputDataFolder(testDirectoryLocation);

    }

    @Test(expected = Exception.class)
    public void testFileValiationException() throws Exception {

        File testFile = new File(testDirectoryLocation);
        testFile.createNewFile();
        DataValidation.validateInputDataFolder(testDirectoryLocation + "s");

    }

}
