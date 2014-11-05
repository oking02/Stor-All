package main.java.server;

import main.java.server.resources.TestHTMLResource;
import main.java.server.resources.experiment.ExperimentResource;
import main.java.server.resources.experiment.SingleExperimentResource;
import main.java.server.resources.project.ProjectExperimentsResource;
import main.java.server.resources.project.ProjectResource;
import main.java.server.resources.project.SingleProjectResource;
import main.java.server.resources.read.ExperimentsUsingRead;
import main.java.server.resources.read.ReadResource;
import main.java.server.resources.read.SingleReadResource;
import main.java.server.resources.system.SystemResource;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.resource.Finder;
import org.restlet.routing.Router;


/**
 * Created by oking on 22/09/14.
 */
public class StorAllServer extends Application {
    private static final String serverName = "/storall";
    private static final int serverPort = 27777;

    public static void runServer() throws Exception {

        Component component = new Component();
        component.getServers().add(Protocol.HTTP, serverPort);
        component.getClients().add(Protocol.FILE);
        Application application = new StorAllServer();
        component.getDefaultHost().attach(serverName, application);
        component.start();

    }

    public static void main(String[] args) throws Exception {

        runServer();

    }

    @Override
    public Restlet createInboundRoot(){

        Router router = new Router(getContext());

        router.attach("/Experiment", ExperimentResource.class);
        router.attach("/Experiment/{id}", SingleExperimentResource.class);

        router.attach("/Project", ProjectResource.class);
        router.attach("/Project/{id}", SingleProjectResource.class);
        router.attach("/Project/{id}/Experiments", ProjectExperimentsResource.class);

        router.attach("/Read", ReadResource.class);
        router.attach("/Read/{id}", SingleReadResource.class);
        router.attach("/Read/{id}/Experiments", ExperimentsUsingRead.class);

        router.attach("/system", SystemResource.class);

        router.attach("/test", TestHTMLResource.class);

        return router;
    }
}
