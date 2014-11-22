package main.java.mysql.utils;

import main.java.dto.Analysis;
import main.java.dto.TransferObject;
import main.java.mysql.errorhandling.SQLErrorHandling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static main.java.mysql.errorhandling.SQLErrorHandling.sqlErrorHandling;

/**
 * Created by oking on 02/10/14.
 */
public class AnalysisInExperiment {
    private Connection dbConnection;

    public AnalysisInExperiment() throws SQLException {

        this.dbConnection = ConnectionToDB.getInstance().getConnection();

    }

    public List<TransferObject> getExperimentAnalysisInfo(int expID) throws SQLException {

        ResultSet rs = createAnalysisResultSet(expID);
        return createListOfAnalysis(rs, expID);

    }

    private ResultSet createAnalysisResultSet(int expID) throws SQLException {
        String sqlStm = "SELECT * FROM Analysis"
                + " WHERE ExpID=(?)";
        PreparedStatement ps = null;

        ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, expID);
        return ps.executeQuery();

    }

    private List<TransferObject> createListOfAnalysis(ResultSet resultSet, int expID) throws SQLException {
        List<TransferObject> analysisList = new ArrayList<>();

            while (resultSet.next()){
                Analysis analysis = new Analysis(resultSet.getInt("ID"), expID, resultSet.getString("Info"), "");
                analysisList.add(analysis);
            }
        return analysisList;
    }
}
