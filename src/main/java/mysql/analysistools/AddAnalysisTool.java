package main.java.mysql.analysistools;

import main.java.dto.AnalysisTool;
import main.java.dto.TransferObject;
import main.java.mysql.utils.ConnectionToDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by oking on 04/12/14.
 */
public class AddAnalysisTool {
    private Connection dbConnection;
    private AnalysisTool analysisTool;

    public AddAnalysisTool(TransferObject transferObject) throws SQLException {
        this.analysisTool = (AnalysisTool) transferObject;
        this.dbConnection = ConnectionToDB.getInstance().getConnection();
    }

    public void add() throws SQLException {
        executeSQLInsert();

    }

    private void executeSQLInsert() throws SQLException {

        String sqlStm = "INSERT INTO AnalysisInfo"
                + "(Name, OutputLocation) VALUES "
                + "(?, ?)";

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setString(1, analysisTool.name);
        ps.setString(2, analysisTool.outputLocation);
        ps.executeUpdate();
    }
}
