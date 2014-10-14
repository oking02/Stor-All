package main.java.mysql.errorhandling;

import java.sql.SQLException;

/**
 * Created by oking on 23/09/14.
 */
public class SQLErrorHandling {

    public static void sqlErrorHandling(SQLException e) throws Exception {
        throw new Exception("SQLException: " + e.getMessage() + "SQLState: " + e.getSQLState() + "VendorError: " + e.getErrorCode());
    }
}
