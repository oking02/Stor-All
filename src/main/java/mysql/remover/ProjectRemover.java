package main.java.mysql.remover;

import main.java.dto.Project;
import main.java.mysql.errorhandling.SQLErrorHandling;
import main.java.mysql.utils.ConnectionToDB;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLInput;

import static main.java.mysql.errorhandling.SQLErrorHandling.*;

/**
 * Created by oking on 24/09/14.
 */
public class ProjectRemover implements Remover {
    private Project project;
    private Connection dbConnection;

    public ProjectRemover(Project project) throws Exception {
        this.project = project;

        try {
            dbConnection = ConnectionToDB.getInstance().getConnection();
        } catch (SQLException e) {
            sqlErrorHandling(e);
        }
    }

    public void remove() throws Exception {

        String sqlStm = "DELETE FROM Projects WHERE ID = (?)";

        try {

            runDeleteStatement(sqlStm);
            deleteFiles();

        } catch (SQLException e) {
            sqlErrorHandling(e);
        }
    }

    private void runDeleteStatement(String sqlStm) throws SQLException {

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, project.id);
        ps.execute();
        ps.close();
    }

    private void deleteFiles(){

        String folderPath = new File("").getAbsolutePath() + "/Projects/Project-" + project.id;
        File containingFolder = new File(folderPath);
        containingFolder.delete();

    }

}
