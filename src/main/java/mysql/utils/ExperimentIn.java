package main.java.mysql.utils;

import main.java.dto.Analysis;
import main.java.dto.Experiment;
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
public class ExperimentIn {
    private int id;
    private String type;
    private Connection dbConnection;

    public ExperimentIn(String type, int id) throws Exception {
        this.id = id;
        this.type = type;

        try {
            dbConnection = ConnectionToDB.getInstance().getConnection();
        } catch (SQLException e) {
            SQLErrorHandling.sqlErrorHandling(e);
        }
    }

    public List<TransferObject> getRelatedExperiments() throws Exception {
        switch (type) {
            case "Read":
                return experimentsUsingRead();
            case "Project":
                return experimentsUnderProject();
            default:
                return Collections.emptyList();
        }
    }

    private List<TransferObject> experimentsUsingRead() throws Exception {
        String sqlStm = "SELECT * FROM Experiments WHERE ReadID=(?)";
        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, id);
        return createListFromResultSet(ps.executeQuery());
    }

    private List<TransferObject> experimentsUnderProject() throws Exception {
        String sqlStm = "SELECT * FROM Experiments WHERE ProjectID=(?)";
        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, id);
        return createListFromResultSet(ps.executeQuery());
    }

    private List<TransferObject> createListFromResultSet(ResultSet rs) throws Exception {
        List<TransferObject> transferObjectList = new ArrayList<>();
        AnalysisInExperiment analysisInExperiment = new AnalysisInExperiment();

        while (rs.next()){
            int id = rs.getInt("ID");
            int projectID = rs.getInt("ProjectID");
            int readID = rs.getInt("ReadID");
            Experiment experiment = new Experiment(id, projectID, readID);

            for (TransferObject a : analysisInExperiment.getExperimentAnalysisInfo(id)){
                experiment.analysis.add((Analysis) a);
            }
            transferObjectList.add(experiment);
        }
        return transferObjectList;
    }
}
