package main.java.server.util;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;

/**
 * Created by oking on 14/10/14.
 */
public class ResourceExceptionHandling {

    public static void exceptionHandling(Exception e, Resource resource){
        resource.getResponse().setEntity(e.getMessage(), MediaType.TEXT_PLAIN);
        Status status = new Status(Status.CLIENT_ERROR_BAD_REQUEST, e.getMessage());
        throw new ResourceException(status);
    }
}
