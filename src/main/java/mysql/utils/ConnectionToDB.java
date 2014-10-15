package main.java.mysql.utils;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oking on 22/09/14.
 */
public class ConnectionToDB {
    private static ConnectionToDB connection = null;
    private Connection dbConnection;

    private ConnectionToDB() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {

        Driver.class.newInstance();
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String connectionURL = "jdbc:mysql://localhost:3306/storall";
        SystemVariables systemVariables = new SystemVariables();
        dbConnection = (Connection) DriverManager.getConnection(connectionURL, systemVariables.getMysqlName(), systemVariables.getMysqlPassword());

    }

    public static ConnectionToDB getInstance() throws SQLException {

        if (connection == null){

            try {
                return new ConnectionToDB();

            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e ){
                System.out.println("Mysql Driver Not Found!");
                return null;
            }
        }
        else{
            return connection;
        }
    }

    public Connection getConnection(){
        return dbConnection;
    }


}
