package main.java.server.responce;

import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.util.Series;

/**
 * Created by oking on 22/11/14.
 */
public class ResponseBuilder {
    private Response response;

    public ResponseBuilder(Response response){
        this.response = response;
        addAcceptHeaders();
    }

    private void addAcceptHeaders(){
        Series<Header> responseHeaders = (Series<Header>) response.getAttributes().get("org.restlet.http.headers");
        AddResponceHeaders.addHeaders(responseHeaders, response);
    }

    public Status addErrorStatus(Exception exception){
        ExceptionStatusMatcher exceptionStatusMatcher = new ExceptionStatusMatcher(exception);
        response.setStatus(exceptionStatusMatcher.getCorrectStatus());
        return exceptionStatusMatcher.getCorrectStatus();
    }

    public void addSuccessStatus(String method){
        SuccessStatusMatcher successStatusMatcher = new SuccessStatusMatcher();
        response.setStatus(successStatusMatcher.addCorrectStatus(method));
    }


}
