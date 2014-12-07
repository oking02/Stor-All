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
public class UpdateAnalysisTool {
    private Connection dbConnection;
    private AnalysisTool analysisTool;

    public UpdateAnalysisTool(TransferObject transferObject) throws SQLException {
        this.analysisTool = (AnalysisTool) transferObject;
        this.dbConnection = ConnectionToDB.getInstance().getConnection();
    }

    public void update() throws SQLException {
        executeSQLUpdate();
    }

    private void executeSQLUpdate() throws SQLException {

        String sqlStm = "UPDATE AnalysisInfo SET OutputLocation=(?) WHERE Name=(?)";

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setString(1, analysisTool.outputLocation);
        ps.setString(2, analysisTool.name);
        ps.executeUpdate();
    }

    public void updateCount() throws SQLException {
        executeSQLUpdateCount();
    }

    private void executeSQLUpdateCount() throws SQLException {

        String sqlStm = "UPDATE AnalysisInfo SET UseCount= UseCount + 1 WHERE OutputLocation=(?)";

        PreparedStatement ps = dbConnection.prepareStatement(sqlStm);
        ps.setString(1, analysisTool.outputLocation);
        ps.executeUpdate();
    }
}
