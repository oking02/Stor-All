package main.java.mysql.builder;

import main.java.fileutils.DataValidation;
import main.java.fileutils.FileTranfers;
import main.java.mysql.utils.ConnectionToDB;
import main.java.dto.Analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static main.java.fileutils.DataValidation.*;
import static main.java.mysql.errorhandling.SQLErrorHandling.*;

/**
 * Created by oking on 22/09/14.
 */
public class AnalysisBuilder{
    private Connection dbConnection;
    private Analysis analysis;

    public AnalysisBuilder(Analysis analysis) throws Exception {
        try {

            dbConnection = ConnectionToDB.getInstance().getConnection();
            this.analysis = analysis;

        } catch (SQLException e) {
            sqlErrorHandling(e);
        }
    }

    public void build() throws Exception {

        validateInputDataFolder(analysis.dataLocation);
        executeSQLInsert();
        createFolderForAnalysis();
        moveDataFilesToNewFolder();

    }

    private void executeSQLInsert() throws SQLException {
        String sqlStm = "INSERT INTO Analysis"
                + "(ID, ExpID, Info) VALUES "
                + "(?, ?, ?)";

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, analysis.id);
        ps.setInt(2, analysis.expID);
        ps.setString(3, analysis.info);
        ps.executeUpdate();
    }

    private void createFolderForAnalysis(){
        String newFolderLocation = new File("").getAbsolutePath() +
                "/Experiments/Experiment-" + analysis.expID + "/Analysis-" + analysis.id + "-" + analysis.info;
        File newFolder = new File(newFolderLocation);
        newFolder.mkdir();
    }

    private void moveDataFilesToNewFolder() throws IOException, InterruptedException {
        FileTranfers fileTranfers = new FileTranfers(analysis.dataLocation, new File("").getAbsolutePath() +
                "/Experiments/Experiment-" + analysis.expID + "/Analysis-" + analysis.id + "-" + analysis.info);
        fileTranfers.transfer();
    }
}
