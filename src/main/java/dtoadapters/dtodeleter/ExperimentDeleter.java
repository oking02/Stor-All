package main.java.dtoadapters.dtodeleter;

import main.java.dto.TransferObject;
import main.java.mysql.remover.ExperimentRemover;

import java.sql.SQLException;

/**
 * Created by oking on 22/11/14.
 */
public class ExperimentDeleter extends DtoDeleter {

    @Override
    public void delete(TransferObject transferObject) throws SQLException {
        ExperimentRemover experimentRemover = new ExperimentRemover((main.java.dto.Experiment) transferObject);
        experimentRemover.remove();
    }
}
