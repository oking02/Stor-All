package main.java.server.responce;

import org.restlet.data.Status;

/**
 * Created by oking on 22/11/14.
 */
public class SuccessStatusMatcher {

    public Status addCorrectStatus(String method){
        if (method.equals("GET")){
            Status status = new Status(Status.SUCCESS_OK, "Successful GET");
            return status;
        }
        else if (method.equals("POST")){
            Status status = new Status(Status.SUCCESS_CREATED, "Successful resource creation");
            return status;
        }
        else if (method.equals("DELETE")){
            Status status = new Status(Status.SUCCESS_OK, "Successful DELETE");
            return status;
        }
        else {
            Status status = new Status(Status.SUCCESS_OK, "Successful Operation");
            return status;
        }
    }
}
