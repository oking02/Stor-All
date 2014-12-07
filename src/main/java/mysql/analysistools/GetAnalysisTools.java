package main.java.mysql.analysistools;

import main.java.dto.Analysis;
import main.java.dto.AnalysisTool;
import main.java.dto.Experiment;
import main.java.dto.TransferObject;
import main.java.mysql.utils.ConnectionToDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static main.java.mysql.utils.ConnectionToDB.*;

/**
 * Created by oking on 04/12/14.
 */
public class GetAnalysisTools {
    private Connection dbConnection;

    public GetAnalysisTools() throws SQLException {
        dbConnection = getInstance().getConnection();
    }

    public List<TransferObject> getToolList() throws SQLException {
        List<TransferObject> listOfTools = new ArrayList<>();

        ResultSet resultSet = createResultSetUsingSQLQuery();

        while(resultSet.next()){

            String name = resultSet.getString("Name");
            String outputLocation = resultSet.getString("OutputLocation");
            AnalysisTool analysisTool = new AnalysisTool(name, outputLocation);
            analysisTool.setUseCount(resultSet.getInt("UseCount"));

            listOfTools.add(analysisTool);

        }

        if (listOfTools.isEmpty()){
            throw new NoSuchElementException("No Tools Found");
        }

        return listOfTools;
    }

    public List<TransferObject> getSingleAnalysisToolInfo(String name) throws SQLException {
        List<TransferObject> listOfTools = new ArrayList<>();

        ResultSet resultSet = createResultSetUsingSQLQuery(name);

        while(resultSet.next()){

            String name1 = resultSet.getString("Name");
            String outputLocation = resultSet.getString("OutputLocation");
            AnalysisTool analysisTool = new AnalysisTool(name1, outputLocation);


            listOfTools.add(analysisTool);

        }

        if (listOfTools.isEmpty()){
            throw new NoSuchElementException("No Tools Found");
        }

        return listOfTools;
    }

    private ResultSet createResultSetUsingSQLQuery() throws SQLException {

        String sqlStm = "SELECT * FROM AnalysisInfo";
        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        return ps.executeQuery();

    }

    private ResultSet createResultSetUsingSQLQuery(String name) throws SQLException {

        String sqlStm = "SELECT * FROM AnalysisInfo WHERE Name=(?)";
        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setString(1, name);
        return ps.executeQuery();

    }

}
