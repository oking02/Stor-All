package main.java.mysql.remover;

import main.java.dto.Project;
import main.java.dto.Read;
import main.java.mysql.utils.ConnectionToDB;

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

    public ReadRemover(Read read) throws Exception {
        this.read = read;

        try {
            dbConnection = ConnectionToDB.getInstance().getConnection();
        } catch (SQLException e) {
            sqlErrorHandling(e);
        }
    }

    public void remove() throws Exception {

        String sqlStm = "DELETE FROM RawReads"
                + " WHERE ID = ? ";
        try {

            runDeleteStatement(sqlStm);

        } catch (SQLException e) {
            sqlErrorHandling(e);
        }
    }

    private void runDeleteStatement(String sqlStm) throws SQLException {
        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, read.id);
        ps.execute();
        ps.close();
    }

}
