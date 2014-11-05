package main.java.fileutils;

import main.java.dto.TransferObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oking on 05/11/14.
 */
public class NoteController {
    private int id;
    private String type;

    public NoteController(int id, String type) throws NoSuchFieldException {
        this.id = id;
        this.type = type;
    }

    public void addNotes(String message) throws IOException {

        File notesFile = new File(new File("").getAbsolutePath() +"/" + type + "s/" + type + "-" + id +"/notes.txt");

        if (!doesNotFileExist(notesFile)){
            notesFile.createNewFile();
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        FileWriter fw = new FileWriter(notesFile, true);
        fw.write(System.getProperty("line.separator") + "Note Entry -- " + dateFormat.format(date));
        fw.write(System.getProperty("line.separator"));
        fw.write(System.getProperty("line.separator") + message);
        fw.write(System.getProperty("line.separator") + "------------------------------");
        fw.write(System.getProperty("line.separator"));
        fw.close();
    }

    private boolean doesNotFileExist(File file){
        return file.exists();
    }


}
