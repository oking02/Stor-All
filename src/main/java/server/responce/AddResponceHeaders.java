package main.java.server.responce;

import org.restlet.Response;
import org.restlet.engine.header.Header;
import org.restlet.util.Series;

/**
 * Created by oking on 02/11/14.
 */
public class AddResponceHeaders {


    public static void addHeaders(Series<Header> responseHeaders, Response response){
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            response.getAttributes().put("org.restlet.http.headers", responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        responseHeaders.add(new Header("Access-Control-Allow-Methods", "POST,OPTIONS,DELETE"));
        responseHeaders.add(new Header("Access-Control-Allow-Headers", "Content-Type"));
        responseHeaders.add(new Header("Access-Control-Allow-Credentials", "false"));
        responseHeaders.add(new Header("Access-Control-Max-Age", "60"));
    }

}
