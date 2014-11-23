package main.java.dtoadapters.dtoadder;

import main.java.dto.TransferObject;
import main.java.mysql.builder.ExperimentBuilder;

import java.sql.SQLException;

/**
 * Created by oking on 22/11/14.
 */
public class ExperimentAdder extends DtoAdder {

    public boolean addDto(TransferObject transferObject) throws SQLException {
        ExperimentBuilder experimentBuilder = new ExperimentBuilder((main.java.dto.Experiment) transferObject);
        experimentBuilder.build();
        return true;
    }

}
