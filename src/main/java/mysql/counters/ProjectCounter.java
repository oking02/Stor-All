package main.java.mysql.counters;

import main.java.mysql.utils.ConnectionToDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oking on 09/12/14.
 */
public class ProjectCounter {
    private Connection dbConnection;

    public ProjectCounter() throws SQLException {
        dbConnection = ConnectionToDB.getInstance().getConnection();
    }

    public void incrementProjectCount(int id) throws SQLException {
        updateStatement(id);
    }

    private void updateStatement(int id) throws SQLException {
        String sqlStm = "UPDATE Projects SET Count = Count + 1 WHERE ID=(?)";

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    public int getProjectCount(int id) throws SQLException {
        return getProjectStatement(id);
    }

    private int getProjectStatement(int id) throws SQLException {
        String sqlStm = "SELECT * from Projects WHERE ID=(?)";

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();

        resultSet.next();
        return resultSet.getInt("Count");
    }

}
