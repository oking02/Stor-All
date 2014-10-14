package main.java.mysql.builder;

import main.java.mysql.utils.ConnectionToDB;
import main.java.dto.Project;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static main.java.mysql.errorhandling.SQLErrorHandling.*;

/**
 * Created by oking on 22/09/14.
 */
public class ProjectBuilder {
    private Connection dbConnection;
    private Project project;

    public ProjectBuilder(Project project) {
        try {

            dbConnection = ConnectionToDB.getInstance().getConnection();
            this.project = project;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void build() throws Exception {
        String sqlStm = "INSERT INTO Projects"
                + "(ID, Owner) VALUES "
                + "(?, ?)";

        PreparedStatement ps = null;
        try {

            ps = dbConnection.prepareStatement(sqlStm);
            ps.setInt(1, project.id);
            ps.setString(2, project.owner);
            ps.executeUpdate();
            createFolderForProject();

        } catch (SQLException e) {
            sqlErrorHandling(e);
        }
    }

    private void createFolderForProject(){
        String newFolderLocation = new File("").getAbsolutePath() + "/Projects/Project-" + project.id + "";
        File newFolder = new File(newFolderLocation);
        newFolder.mkdir();
    }
}
