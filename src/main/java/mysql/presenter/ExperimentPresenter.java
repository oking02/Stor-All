package main.java.mysql.presenter;

import main.java.dto.Analysis;
import main.java.dto.Experiment;
import main.java.dto.TransferObject;
import main.java.mysql.utils.AnalysisInExperiment;
import main.java.mysql.utils.ConnectionToDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static main.java.mysql.errorhandling.SQLErrorHandling.sqlErrorHandling;

/**
 * Created by oking on 22/09/14.
 */
public class ExperimentPresenter {
    private Connection dbConnection;

    public ExperimentPresenter() throws SQLException {
        this.dbConnection = ConnectionToDB.getInstance().getConnection();
    }

    public List<TransferObject> createListOfAllExperiments() throws SQLException {

        ResultSet rs = createResultSetUsingSQLQuery();
        return populateAListWithExperimentsInfo(rs);

    }

    public List<TransferObject> getExperiment(int id) throws SQLException {

        ResultSet rs = createResultSetUsingSQLQuery(id);
        return populateAListWithExperimentsInfo(rs);

    }

    private ResultSet createResultSetUsingSQLQuery() throws SQLException {

        String sqlStm = "SELECT * FROM Experiments";
        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        return ps.executeQuery();

    }

    private ResultSet createResultSetUsingSQLQuery(int id) throws SQLException {

        String sqlStm = "SELECT * FROM Experiments WHERE ID=(?)";
        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    private List<TransferObject> populateAListWithExperimentsInfo(ResultSet resultSet) throws SQLException, NoSuchElementException {
        List<TransferObject> listOfExperiments = new ArrayList<>();
        AnalysisInExperiment analysisInExperiment = new AnalysisInExperiment();

            while(resultSet.next()){

                int id = resultSet.getInt("ID");
                int projectID = resultSet.getInt("ProjectID");
                int readID = resultSet.getInt("ReadID");
                Experiment experiment = new Experiment(id, projectID, readID);


                for (TransferObject a : analysisInExperiment.getExperimentAnalysisInfo(id)){
                    experiment.analysis.add((Analysis) a);
                }
                listOfExperiments.add(experiment);

            }
        if (listOfExperiments.isEmpty()){
            throw new NoSuchElementException("Experiment Not Found");
        }

        return listOfExperiments;
    }
}


