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










    public void test() throws SQLException {

        ResultSet rs = null;
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String connectionURL = "jdbc:mysql://localhost:3306/storall";
            Connection connection = (Connection) DriverManager.getConnection(connectionURL, "root", "thorax123");

            Statement smt = (Statement) connection.createStatement();
            rs = smt.executeQuery("SELECT * FROM Person");

            while (rs.next()){
                System.out.println(rs.getInt("PersonID"));
                System.out.println(rs.getObject("PersonName"));
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            assert rs != null;
            rs.close();
        }
    }

    public static void main(String[] args) throws SQLException {
        ConnectionToDB connectionToDB = ConnectionToDB.getInstance();
        connectionToDB.test();

    }



}
