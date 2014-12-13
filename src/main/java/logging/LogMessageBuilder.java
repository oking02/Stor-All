package main.java.logging;

import org.restlet.data.Status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oking on 13/12/14.
 */
public class LogMessageBuilder {
    private String actionType;
    private Status status;

    public LogMessageBuilder(String type, Status status){
        this.actionType = type;
        this.status = status;
    }

    public String getLogMessage(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date) + " | " + status.getDescription() + " " + actionType;
    }


}
