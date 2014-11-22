package main.java.mysql.utils;

import com.mysql.jdbc.Connection;
import main.java.mysql.errorhandling.SQLErrorHandling;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static main.java.mysql.errorhandling.SQLErrorHandling.*;

/**
 * Created by oking on 01/10/14.
 */
public class IDGenerator {

    public static synchronized int getUniqueID(String type) throws SQLException {

        Connection dbConnection =  ConnectionToDB.getInstance().getConnection();
        int newId = getID(type, dbConnection);
        updateTable(type, newId + 1, dbConnection);
        return newId;

    }

    private static int getID(String type, Connection dbConnection) throws SQLException {

        String sqlStm = "SELECT IDNum FROM IDGen WHERE Type=(?)";
        PreparedStatement smt = dbConnection.prepareStatement(sqlStm);
        smt.setString(1, type);
        ResultSet result = smt.executeQuery();
        result.next();
        return result.getInt("IDNum");

    }

    private static void updateTable(String type, int id, Connection dbConnection) throws SQLException {

        String sqlStm = "UPDATE IDGen SET IDNum=(?) WHERE Type=(?)";
        PreparedStatement smt = dbConnection.prepareStatement(sqlStm);
        smt.setInt(1, id);
        smt.setString(2, type);
        smt.executeUpdate();

    }



}
