package main.java.server.util;

import main.java.mysql.errorhandling.SQLErrorHandling;
import org.restlet.data.Status;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * Created by oking on 22/11/14.
 */
public class ExceptionStatusMatcher {
    private Exception exception;

    public ExceptionStatusMatcher(Exception exception){
        this.exception = exception;
    }

    public Status getCorrectStatus(){
        if (exception instanceof SQLException){
            return new Status(Status.SERVER_ERROR_INTERNAL, buildSQLMessage());
        }
        else if(exception instanceof IOException){
            return new Status(Status.SERVER_ERROR_INTERNAL, exception.getMessage());
        }
        else if (exception instanceof FileNotFoundException){
            return new Status(Status.CLIENT_ERROR_GONE, "File Not Found");
        }
        else if (exception instanceof FileSystemException){
            return new Status(Status.CLIENT_ERROR_BAD_REQUEST, "Input Directory is empty");
        }
        else if(exception instanceof InterruptedException){
            return new Status(Status.SERVER_ERROR_INTERNAL, "File transfer thread interrupted!");
        }
        else if(exception instanceof ParserConfigurationException){
            return new Status(Status.SERVER_ERROR_INTERNAL, exception.getMessage());
        }
        else if (exception instanceof NoSuchElementException){
            return new Status(Status.CLIENT_ERROR_NOT_FOUND, exception.getMessage());
        }
        else {
            return new Status(Status.SERVER_ERROR_INTERNAL, "Error");
        }
    }

    private String buildSQLMessage(){
        SQLException e = (SQLException) exception;
        String message = "SQLException: " + e.getMessage()
                + "SQLState: " + e.getSQLState()
                + "VendorError: " + e.getErrorCode();
        return message;
    }

}
