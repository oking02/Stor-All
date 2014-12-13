package main.java.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by oking on 13/12/14.
 */
public class Logger {

    public void log(String message) throws IOException {

        try {

            File logFile = new File(new File("").getAbsolutePath()+ "/log.txt");

            FileWriter fw = new FileWriter(logFile, true);
            fw.write(System.getProperty("line.separator"));
            fw.write(System.getProperty("line.separator") + message);
            fw.write(System.getProperty("line.separator") + "------------------------------");
            fw.close();

        } catch (IOException e){
            throw new IOException("Error creating or writing to the Log file");
        }
    }
}
