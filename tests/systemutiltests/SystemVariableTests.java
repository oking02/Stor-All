package systemutiltests;

import main.java.mysql.utils.SystemVariables;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by oking on 18/11/14.
 */
public class SystemVariableTests {

    @Test
    public void testServerNameIsFound(){
        SystemVariables systemVariables = new SystemVariables();
        String serverName = systemVariables.getMysqlName();
        assertTrue(!serverName.isEmpty());
    }

    @Test
    public void testServerPortIsFound(){
        SystemVariables systemVariables = new SystemVariables();
        String serverPort = systemVariables.getMysqlPassword();
        assertTrue(!serverPort.isEmpty());
    }
}
