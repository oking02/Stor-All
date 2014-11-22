package main.java.mysql.presenter;

import main.java.dto.Read;
import main.java.dto.TransferObject;
import main.java.mysql.errorhandling.SQLErrorHandling;
import main.java.mysql.utils.ConnectionToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by oking on 26/09/14.
 */
public class ReadPresenter {
    private Connection dbConnection;

    public ReadPresenter() throws SQLException {

        dbConnection = ConnectionToDB.getInstance().getConnection();

    }


    public List<TransferObject> createListOfAllReads() throws SQLException {

        ResultSet rs = createResultSetUsingSQLQuery();
        return populateAListWithProjectsInfo(rs);

    }

    public List<TransferObject> getRead(int id) throws SQLException {

        ResultSet rs = createResultSetUsingSQLQuery(id);
        return populateAListWithProjectsInfo(rs);
    }

    private ResultSet createResultSetUsingSQLQuery() throws SQLException {
        String sqlStm = "SELECT * FROM RawReads";

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        return ps.executeQuery();
    }

    private ResultSet createResultSetUsingSQLQuery(int id) throws SQLException {
        String sqlStm = "SELECT * FROM RawReads WHERE ID=(?)";
        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    private List<TransferObject> populateAListWithProjectsInfo(ResultSet resultSet) throws SQLException, NoSuchElementException {
        List<TransferObject> listOfProjects = new ArrayList<>();

        while(resultSet.next()){
            listOfProjects.add(new Read(resultSet.getInt("ID"), "Location"));
        }

        if (listOfProjects.isEmpty()){
            throw new NoSuchElementException("Read Not Found");
        }
        return listOfProjects;
    }
}
