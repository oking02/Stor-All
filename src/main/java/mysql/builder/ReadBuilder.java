package main.java.mysql.builder;

import main.java.dto.Read;
import main.java.fileutils.DataValidation;
import main.java.fileutils.FileThreadController;
import main.java.fileutils.FileTranfers;
import main.java.mysql.utils.ConnectionToDB;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

import static main.java.fileutils.DataValidation.*;
import static main.java.mysql.errorhandling.SQLErrorHandling.sqlErrorHandling;

/**
 * Created by oking on 22/09/14.
 */
public class ReadBuilder {
    private Connection dbConnection;
    private Read read;

    public ReadBuilder(Read read) throws Exception {

        try {

            dbConnection = ConnectionToDB.getInstance().getConnection();
            this.read = read;

        } catch (SQLException e) {
            sqlErrorHandling(e);
        }
    }

    public void build() throws Exception {

        validateInputDataFolder(read.locationOfReadData);
        executeSqlInsert();
        createFolderForRead();
        moveReadDataFileToNewFolder();

    }

    private void executeSqlInsert() throws Exception {

        String sqlStm = "INSERT INTO RawReads"
                + "(ID) VALUES "
                + "(?)";

        PreparedStatement ps = null;
        try {

            ps = dbConnection.prepareStatement(sqlStm);
            ps.setInt(1, read.id);
            ps.executeUpdate();

        } catch (SQLException e) {
            sqlErrorHandling(e);
        }

    }

    private void createFolderForRead(){
        String newFolderLocation = new File("").getAbsolutePath() + "/Reads/Read-" + read.id + "";
        File newFolder = new File(newFolderLocation);
        newFolder.mkdir();
    }

    private void moveReadDataFileToNewFolder() throws IOException, InterruptedException {
        FileTranfers fileTranfers = new FileTranfers(read.locationOfReadData, new File("").getAbsolutePath() + "/Reads/Read-" + read.id + "");
        fileTranfers.transfer();
    }
}
