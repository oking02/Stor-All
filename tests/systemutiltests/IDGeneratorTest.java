package systemutiltests;

import main.java.dto.Experiment;
import main.java.dto.TransferObject;
import main.java.mysql.presenter.ExperimentPresenter;
import main.java.mysql.utils.IDGenerator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by oking on 18/11/14.
 */
public class IDGeneratorTest {

    @Test
    public void isIdUnique() throws Exception {
        int id = IDGenerator.getUniqueID("Experiment");
        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        List<TransferObject> listOfExperiments = experimentPresenter.createListOfAllExperiments();

        for (TransferObject transferObject : listOfExperiments){
            Experiment experiment = (Experiment) transferObject;
            assertTrue(id != experiment.id);
        }
    }
}
