package main.java.fileutils;

import main.java.dto.Experiment;
import main.java.dto.Project;
import main.java.dto.Read;
import main.java.dto.TransferObject;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.presenter.ProjectPresenter;
import main.java.mysql.presenter.ReadPresenter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oking on 15/10/14.
 */
public class ExportToCSV {
    private String exportLocation;
    private List<TransferObject> transferObjectList;

    public ExportToCSV(String exportLocation, List<TransferObject> transferObjectList){
        this.exportLocation = exportLocation;
        this.transferObjectList = transferObjectList;
    }

    public void createCSV(String type) throws IOException {

        File newCSVFile = new File(exportLocation + "/" + type + "s.csv");
        newCSVFile.createNewFile();

        FileWriter fw = new FileWriter(newCSVFile.getAbsoluteFile(), true);

        switch (type) {
            case "Experiment":
                experiment(fw);
                break;
            case "Project":
                project(fw);
                break;
            case "Read":
                read(fw);
                break;
        }

        fw.close();
    }

    private void experiment(FileWriter fw) throws IOException {

        fw.write(System.getProperty("line.separator") + "ExperimentID, ProjectID, ReadID, Analysis");

        for (TransferObject transferObject : transferObjectList){
            Experiment experiment = (Experiment) transferObject;
            fw.write(System.getProperty("line.separator") + experiment.id + "," + experiment.projectID + "," + experiment.readID +
                    ",\"" + experiment.analysis.toString() + "\"");
        }
    }

    private void project(FileWriter fw) throws IOException {

        fw.write(System.getProperty("line.separator") + "ProjectID, Owner");

        for (TransferObject transferObject : transferObjectList){
            Project project = (Project) transferObject;
            fw.write(System.getProperty("line.separator") + project.id + "," + project.owner);
        }

    }

    private void read(FileWriter fw) throws IOException {

        fw.write(System.getProperty("line.separator") + "ReadID");

        for (TransferObject transferObject : transferObjectList){
            Read read = (Read) transferObject;
            fw.write(System.getProperty("line.separator") + read.id);
        }
    }

}
