package main.java.mysql.remover;

import main.java.dto.Experiment;
import main.java.mysql.utils.ConnectionToDB;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static main.java.mysql.errorhandling.SQLErrorHandling.sqlErrorHandling;

/**
 * Created by oking on 26/09/14.
 */
public class ExperimentRemover implements Remover {
    private Experiment experiment;
    private Connection dbConnection;

    public ExperimentRemover(Experiment experiment) throws Exception {
        this.experiment = experiment;

        try {
            dbConnection = ConnectionToDB.getInstance().getConnection();
        } catch (SQLException e) {
            sqlErrorHandling(e);
        }
    }

    public void remove() throws Exception {

        String sqlStm = "DELETE FROM Experiments WHERE ID = (?)";

        try {

            runDeleteStatement(sqlStm);
            deleteFiles();

        } catch (SQLException e) {
            sqlErrorHandling(e);
        }
    }

    private void runDeleteStatement(String sqlStm) throws SQLException {

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, experiment.id);
        ps.execute();
        ps.close();
    }

    private void deleteFiles(){

        String folderPath = new File("").getAbsolutePath() + "/Experiments/Experiment-" + experiment.id;
        File containingFolder = new File(folderPath);
        containingFolder.delete();

    }
}
