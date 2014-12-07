package main.java.mysql.analysistools;

import main.java.dto.AnalysisTool;
import main.java.dto.TransferObject;
import main.java.mysql.utils.ConnectionToDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by oking on 06/12/14.
 */
public class DeleteAnalysisTool {
    private Connection dbConnection;
    private AnalysisTool analysisTool;

    public DeleteAnalysisTool(TransferObject transferObject) throws SQLException {
        this.analysisTool = (AnalysisTool) transferObject;
        this.dbConnection = ConnectionToDB.getInstance().getConnection();
    }

    public void delete() throws SQLException {
        executeSQLDelete();
    }

    private void executeSQLDelete() throws SQLException {

        String sqlStm = "DELETE FROM AnalysisInfo WHERE Name=(?)";

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setString(1, analysisTool.name);
        ps.executeUpdate();
    }
}
