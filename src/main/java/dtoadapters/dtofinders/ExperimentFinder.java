package main.java.dtoadapters.dtofinders;

import main.java.dto.Experiment;
import main.java.dto.TransferObject;
import main.java.mysql.presenter.ExperimentPresenter;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by oking on 22/11/14.
 */
public class ExperimentFinder extends DtoFinder {

    public List<TransferObject> findAll() throws SQLException {
        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        return experimentPresenter.createListOfAllExperiments();
    }

    public List<TransferObject> findOne(int id) throws SQLException {
        ExperimentPresenter experimentPresenter = new ExperimentPresenter();
        return experimentPresenter.getExperiment(id);
    }
}
