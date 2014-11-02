package integration_tests;

import main.java.mysql.utils.ConnectionToDB;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by oking on 24/10/14.
 */
public class ConnectionTests {

    @Test
    public void testThatTheCorrectDatabaseIsBeingUsed() throws SQLException {

        Connection connection = ConnectionToDB.getInstance().getConnection();
        assertEquals("storall", connection.getCatalog());

    }





}
