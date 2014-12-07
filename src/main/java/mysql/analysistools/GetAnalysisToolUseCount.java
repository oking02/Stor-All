package main.java.mysql.analysistools;

import main.java.dto.AnalysisTool;
import main.java.dto.TransferObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static main.java.mysql.utils.ConnectionToDB.getInstance;

/**
 * Created by oking on 06/12/14.
 */
public class GetAnalysisToolUseCount {
    private Connection dbConnection;

    public GetAnalysisToolUseCount() throws SQLException {
        dbConnection = getInstance().getConnection();
    }


    public int getSingleAnalysisToolInfo(String name) throws SQLException {

        ResultSet resultSet = createResultSetUsingSQLQuery(name);
        int count = 0;

        while(resultSet.next()){
            count = resultSet.getInt("UseCount");
        }
        return count;
    }


    private ResultSet createResultSetUsingSQLQuery(String name) throws SQLException {

        String sqlStm = "SELECT * FROM AnalysisInfo WHERE Name=(?)";
        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setString(1, name);
        return ps.executeQuery();

    }
}
