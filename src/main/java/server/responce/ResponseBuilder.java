package main.java.server.responce;

import main.java.logging.LogMessageBuilder;
import main.java.logging.Logger;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.util.Series;

import java.io.IOException;

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

    public Status addErrorStatus(Exception exception) throws IOException {
        ExceptionStatusMatcher exceptionStatusMatcher = new ExceptionStatusMatcher(exception);
        response.setStatus(exceptionStatusMatcher.getCorrectStatus());
        logAction();
        return exceptionStatusMatcher.getCorrectStatus();
    }

    public void addSuccessStatus(String method) throws IOException {
        SuccessStatusMatcher successStatusMatcher = new SuccessStatusMatcher();
        response.setStatus(successStatusMatcher.addCorrectStatus(method));
        logAction();
    }

    private void logAction() throws IOException {
        LogMessageBuilder logMessageBuilder = new LogMessageBuilder(response.getRequest().getResourceRef().toString(), response.getStatus());
        Logger logger = new Logger();
        logger.log(logMessageBuilder.getLogMessage());
    }

}
