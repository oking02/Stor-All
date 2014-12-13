package main.java.mysql.counters;

import main.java.mysql.utils.ConnectionToDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oking on 09/12/14.
 */
public class ReadCounter {
    private Connection dbConnection;

    public ReadCounter() throws SQLException {
        dbConnection = ConnectionToDB.getInstance().getConnection();
    }

    public void incrementProjectCount(int id) throws SQLException {
        updateStatement(id);
    }

    private void updateStatement(int id) throws SQLException {
        String sqlStm = "UPDATE RawReads SET Count = Count + 1 WHERE ID=(?)";

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    public int getReadCount(int id) throws SQLException {
        return getReadStatement(id);
    }

    private int getReadStatement(int id) throws SQLException {
        String sqlStm = "SELECT Count from RawReads WHERE ID=(?)";

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();
        return resultSet.getInt("Count");
    }
}
