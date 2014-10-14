package main.java.mysql.presenter;

import main.java.dto.Project;
import main.java.dto.TransferObject;
import main.java.mysql.errorhandling.SQLErrorHandling;
import main.java.mysql.utils.ConnectionToDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by oking on 23/09/14.
 */
public class ProjectPresenter {
    private Connection dbConnection;

    public ProjectPresenter() throws SQLException {
        dbConnection = ConnectionToDB.getInstance().getConnection();
    }

    public List<TransferObject> createListOfAllProjects() throws Exception {

        try {

            ResultSet rs = createResultSetUsingSQLQuery();
            return populateAListWithProjectsInfo(rs);

        } catch (SQLException e) {
            SQLErrorHandling.sqlErrorHandling(e);
            return Collections.emptyList();
        }

    }

    public List<TransferObject> getProject(int id) throws Exception {

        try {
            ResultSet rs = createResultSetUsingSQLQuery(id);
            return populateAListWithProjectsInfo(rs);

        } catch (SQLException e) {
            SQLErrorHandling.sqlErrorHandling(e);
            return Collections.emptyList();
        }


    }

    private ResultSet createResultSetUsingSQLQuery() throws SQLException {
        String sqlStm = "SELECT * FROM Projects";

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        return ps.executeQuery();
    }

    private ResultSet createResultSetUsingSQLQuery(int id) throws SQLException {

        String sqlStm = "SELECT * FROM Projects WHERE ID=(?)";
        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    private List<TransferObject> populateAListWithProjectsInfo(ResultSet resultSet) throws SQLException {
        List<TransferObject> listOfProjects = new ArrayList<>();

        while(resultSet.next()){
            int id = resultSet.getInt("ID");
            String owner = resultSet.getString("Owner");
            listOfProjects.add(new Project(id, owner));
        }
        return listOfProjects;
    }
}
