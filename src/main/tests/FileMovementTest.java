import main.java.fileutils.FileTranfers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created by oking on 14/10/14.
 */
public class FileMovementTest {

    @BeforeClass
    public static void setup() throws IOException {

        File source = new File("Source");
        File testSourceDir = new File("Source/testSourceDir");
        File testSourceDirFile = new File("Source/testSourceDir/test.txt");
        File testSourceFile = new File("Source/test.txt");
        File destination = new File("Destination");

        source.mkdir();
        testSourceDir.mkdir();
        testSourceDirFile.createNewFile();
        testSourceFile.createNewFile();
        destination.mkdir();

    }

    @Test
    public void testMovement() throws IOException, InterruptedException {

        FileTranfers fileTranfers = new FileTranfers("Source", "Destination");
        fileTranfers.transfer();

        assertTrue(new File("Destination/testSourceDir").exists());
        assertTrue(new File("Destination/testSourceDir").isDirectory());

        assertTrue(new File("Destination/testSourceDir/test.txt").exists());
        assertTrue(new File("Destination/testSourceDir/test.txt").isFile());

        assertTrue(new File("Destination/test.txt").exists());
        assertTrue(new File("Destination/test.txt").isFile());

        assertTrue(new File("Source").exists());
        assertTrue(new File("Source").isDirectory());

    }

    @AfterClass
    public static void tidyup(){

        File source = new File("Source");
        File testSourceDir = new File("Source/testSourceDir");
        File testSourceDirFile = new File("Source/testSourceDir/test.txt");
        File testSourceFile = new File("Source/test.txt");

        File destination = new File("Destination");
        File testDestinationDir = new File("Destination/testSourceDir");
        File testDestinationDirFile = new File("Destination/testSourceDir/test.txt");
        File testDestinationFile = new File("Destination/test.txt");

        testSourceFile.delete();
        testSourceDirFile.delete();
        testSourceDir.delete();
        source.deleteOnExit();

        testDestinationDirFile.delete();
        testDestinationFile.delete();
        testDestinationDir.delete();
        destination.deleteOnExit();

    }
}
