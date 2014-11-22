package main.java.mysql.remover;

import main.java.dto.Project;
import main.java.dto.Read;
import main.java.mysql.utils.ConnectionToDB;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static main.java.mysql.errorhandling.SQLErrorHandling.sqlErrorHandling;

/**
 * Created by oking on 26/09/14.
 */
public class ReadRemover implements Remover {

    private Read read;
    private Connection dbConnection;

    public ReadRemover(Read read) throws SQLException {
        this.read = read;

        dbConnection = ConnectionToDB.getInstance().getConnection();

    }

    public void remove() throws SQLException {

        String sqlStm = "DELETE FROM RawReads"
                + " WHERE ID = ? ";

        runDeleteStatement(sqlStm);
        deleteFiles();

    }

    private void runDeleteStatement(String sqlStm) throws SQLException {
        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, read.id);
        ps.execute();
        ps.close();
    }

    private void deleteFiles(){

        String folderPath = new File("").getAbsolutePath() + "/Reads/Read-" + read.id;
        File containingFolder = new File(folderPath);
        containingFolder.delete();

    }

}
