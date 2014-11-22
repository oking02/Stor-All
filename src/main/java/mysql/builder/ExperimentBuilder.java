package main.java.mysql.builder;

import main.java.mysql.utils.ConnectionToDB;
import main.java.dto.Experiment;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static main.java.mysql.errorhandling.SQLErrorHandling.*;

/**
 * Created by oking on 22/09/14.
 */
public class ExperimentBuilder {
    private Connection dbConnection;
    private Experiment experiment;

    public ExperimentBuilder(Experiment exp) throws SQLException {

         dbConnection = ConnectionToDB.getInstance().getConnection();
         this.experiment = exp;

    }


    public void build() throws SQLException {

        String sqlStm = "INSERT INTO Experiments"
                + "(ID, ProjectID, ReadID) VALUES "
                + "(?, ?, ?)";

        PreparedStatement ps;

        ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, experiment.id);
        ps.setInt(2, experiment.projectID);
        ps.setInt(3, experiment.readID);
        ps.executeUpdate();
        createFolderForExperiment();

    }

    private void createFolderForExperiment(){
        String newFolderLocation = new File("").getAbsolutePath() + "/Experiments/Experiment-" + experiment.id + "";
        File newFolder = new File(newFolderLocation);
        newFolder.mkdir();
    }
}
